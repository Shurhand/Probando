package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.CashOrder;
import domain.Clerk;
import domain.Consumer;
import domain.Content;
import domain.CreditCard;
import domain.Item;
import domain.OrderedItem;
import domain.ShoppingCart;
import repositories.ConsumerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ConsumerService {
	
	// ================== Managed repository ================== 
	@Autowired
	private ConsumerRepository consumerRepository;
	
	// ================== Supporting services ================== 
	@Autowired
	private OrderedItemService orderedItemService;

	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private CashOrderService cashOrderService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ClerkService clerkService;
	
	@Autowired
	private TaxService taxService;
	
	@Autowired
	private Md5PasswordEncoder md5PassWordEncoder;
	

	

	
	// ================== Constructors ================== 
	public ConsumerService(){
		super();
	}
	
	// ================== Simple CRUD methods ================== 
	public Consumer create(){
		
		Consumer result = new Consumer();
		ShoppingCart shoppingCart = shoppingCartService.create();
		Collection<CashOrder> cashOrders = new ArrayList<CashOrder>();
		CreditCard creditcard;
		UserAccount userAccount;
		creditcard = new CreditCard();
	

		userAccount = new UserAccount();
	
		

		Collection<Authority> auths = new ArrayList<>();
		
		Authority auth = new Authority();
		auth.setAuthority("CONSUMER");
		auths.add(auth);
		
		userAccount.setAuthorities(auths);
		
		
		result.setShoppingCart(shoppingCart);
		result.setCashOrders(cashOrders);
		result.setCreditCard(creditcard);
		result.setUserAccount(userAccount);
		
		
		
		return result;
	}
	
	public void save(Consumer consumer) {
	
		Assert.notNull(consumer);
		String password;
		ShoppingCart shoppingCart = shoppingCartService.create();
		

		Collection<Authority> auths = new ArrayList<>();
		Authority auth = new Authority();
		auth.setAuthority("CONSUMER");
		auths.add(auth);
	

		validateCreditCard(consumer);
		password = consumer.getUserAccount().getPassword();
		password = md5PassWordEncoder.encodePassword(password, null);
		consumer.getUserAccount().setPassword(password);
		
				
		consumer.getUserAccount().setAuthorities(auths);

		
		Consumer cons = this.consumerRepository.saveAndFlush(consumer);
		
		//System.out.println(consumer);
		cons.setShoppingCart(shoppingCart);	
		cons.getShoppingCart().setConsumer(cons);
/*
	//	System.out.println(findAll());	
	//	for(Consumer c : findAll()){
		//	System.out.println(c.getShoppingCart());
		//	System.out.println(c.getShoppingCart().getConsumer());
		//	System.out.println(c.getShoppingCart().getConsumer().getShoppingCart());
			
//	}
		
	//	System.out.println(cons.getShoppingCart());	
	//	shoppingCartService.save(cons.getShoppingCart());
		//ShoppingCart cons = shoppingCartService.getShoppingCartConsumer(consumer);
		//System.out.println(cons);
		//List<Consumer> cons = (List<Consumer>) consumerRepository.findAll();
		//shoppingCartService.save(cons.get(cons.size()-1).getShoppingCart());
*/	
	}
	
	public void delete(Consumer consumer) {
		Assert.notNull(consumer);
		Assert.isTrue(consumer.getId() != 0);

		consumerRepository.save(consumer);
	}	
	
	public Consumer findOne(int consumerID){
		Assert.isTrue(consumerID != 0);
		
		Consumer result;
		
		result = consumerRepository.findOne(consumerID);
		Assert.notNull(result);
		
		return result;		
	}
	
	public Collection<Consumer> findAll(){
		Collection<Consumer> result;
		boolean admin = false;
		Collection<Authority> authority;
		
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN)){
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		result = consumerRepository.findAll();
		Assert.notNull(result);
		
		return result;		
	}
	

	
	// Other business methods 
	public Consumer findPrincipal() {
		Consumer result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = consumerRepository.findConsumer(userAccount);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Item> findItemsOrderedByCategory(){
		Collection<Item> result;
		
		result = itemService.findItemsOrderedByCategory();

		return result;
	}
	
	public Collection<Item> findItemsContainKeyword(String k){
		Assert.notNull(k);
		
		Collection<Item> result;
		
		result = itemService.findItemsContainKeyword(k);
			
		return result;
	}
	
	//Metodos para Admin
	
	public Collection<Consumer> findConsumersMoreCashOrders() {
		Collection<Consumer> result;
		boolean admin;
		Collection<Authority> authority;
		
		admin = false;
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN)){
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		result = consumerRepository.findConsumersMoreCashOrders();
		Assert.notNull(result);

		return result;
	}

		public Collection<Consumer> findConsumersSpentMoreMoney() {
		Collection<Consumer> result;
		boolean admin = false;
		Collection<Authority> authority;
		
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN)){
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		result = consumerRepository.findConsumersSpentMoreMoney();
		Assert.notNull(result);

		return result;
	}

	
	public Collection<Consumer> getRegisteredConsumers(){
		Collection<Consumer> result;
		
		result = consumerRepository.getRegisteredConsumers();
		Assert.notNull(result);
		
		return result;
	}
	
	public ShoppingCart getShoppingCart(){
		ShoppingCart result;
		Consumer consumer;
		
		consumer = findPrincipal();
		result = shoppingCartService.getShoppingCartConsumer(consumer);
		Assert.notNull(result);
		
		return result;
	}
	
	//Añadir objetos a la ShoppingCart y actualizar la cantidad si el objeto ya existia
	public void addItemToShoppingCart(Item item){
		Assert.notNull(item);
		
		ShoppingCart shoppingCart;
		Content content;
		
		shoppingCart = getShoppingCart(); 		
		//Buscamos el content primero
		content = contentService.findContentByItemAndShoppingCart(item, shoppingCart);
		
		//Si no existe lo creamos y añadimos el item y la cantidad
		if(content == null){
			content = contentService.create();
			content.setQuantity(1);
			content.setShoppingCart(shoppingCart);
			content.setItem(item);
			shoppingCart.getContents().add(content);
		}else{
			//Si existe se actualiza la cantidad
			content.setQuantity(content.getQuantity()+1);
		}
		contentService.save(content);
	}
	
	//Change the quantity of an item in his or her shopping cart
	public void changeItemQuantityInShoppingCart(Item item, Integer quantity){
		Assert.notNull(item);
		Assert.isTrue(quantity > 0);
		
		ShoppingCart shoppingCart;
		Content content;
		
		shoppingCart = getShoppingCart(); 		
		content = contentService.findContentByItemAndShoppingCart(item, shoppingCart);
		Assert.notNull(content, "This item does not exist in your shopping cart.");

		content.setQuantity(quantity);
	}
	
	//Delete an item from his or her shopping cart
	public void deleteItemFromShoppingCart(Item item){
		Assert.notNull(item);
		
		ShoppingCart shoppingCart;
		Content content;
		
		shoppingCart = getShoppingCart(); 	
		content = contentService.findContentByItemAndShoppingCart(item, shoppingCart);
		Assert.notNull(content, "This item does not exist in your shopping cart.");
		
		contentService.delete(content);
	}
	
	//Add a comment to the shopping cart
	public void addCommentToShoppingCart(String comment){
		Assert.notNull(comment);
		
		ShoppingCart shoppingCart;

		shoppingCart = getShoppingCart(); 		
		shoppingCart.getComments().add(comment);
	}
	
	//Modify a comment in the shopping cart
	public void modifyCommentInShoppingCart(String viejoComment, String nuevoComment){
		Assert.notNull(viejoComment);
		Assert.notNull(nuevoComment);
		
		ShoppingCart shoppingCart;

		shoppingCart = getShoppingCart(); 		
		shoppingCart.getComments().remove(viejoComment);
		shoppingCart.getComments().add(nuevoComment);		
	}

	//Delete a comment in the shopping cart
	public void deleteCommentInShoppingCart(String comment){
		Assert.notNull(comment);
		
		ShoppingCart shoppingCart;

		shoppingCart = getShoppingCart(); 		
		shoppingCart.getComments().remove(comment);
	}
	//Check his or her shopping cart out and place the corresponding order.
	public CashOrder placeOrderFromShoppingCart(){
		Consumer consumer;
		Date moment;
		ShoppingCart shoppingCart;
		CashOrder cashOrder,result;
		Collection<Content> contents;
		OrderedItem orderedItem;
		Collection<OrderedItem> orderedItems = new ArrayList<OrderedItem>();
		Collection<String> comments = new ArrayList<String>();
		Clerk clerk;
		
		
		clerk = clerkService.findOne(13);

		shoppingCart = getShoppingCart();
		consumer = findPrincipal();
		contents = contentService.findContentInShoppingCart(shoppingCart);
		
		moment = new Date(System.currentTimeMillis()-1);
		cashOrder = cashOrderService.create();
		
		
		cashOrder.setConsumerName(consumer.getName());
		cashOrder.setConsumerAddress(consumer.getAddress());
		cashOrder.setCreditCard(consumer.getCreditCard());
		cashOrder.setPlacementMoment(moment);
		cashOrder.setDeliveryDate(null);
		cashOrder.setCancellationDate(null);
		cashOrder.setComments(comments);
		cashOrder.setConsumer(consumer);
		cashOrder.setOrderedItems(orderedItems);
		cashOrder.setClerk(clerk);
		
		for(Content ct: contents){
			orderedItem = orderedItemService.createFromItem(ct.getItem());
			orderedItem.setQuantity(ct.getQuantity());
			orderedItem.setCashOrder(cashOrder);
			cashOrder.getOrderedItems().add(orderedItem);
		}
	
		result = cashOrderService.save(cashOrder);
		for(OrderedItem o: result.getOrderedItems()){
			orderedItemService.save(o);
		}
		
		shoppingCart = shoppingCartService.EmptyShoppingCart(shoppingCart);
		shoppingCartService.save(shoppingCart);
		
		return result;		
	}
	
	public void placeOrderFromShoppingCart(ShoppingCart shoppingCart){
		Consumer consumer;
		Collection<Content> contents;
		CashOrder cashOrder;
		OrderedItem orderedItem;
		Collection<OrderedItem> orderedItems;
		Date moment;
		Collection<String> comments;
		CashOrder result;
		
		moment = new Date(System.currentTimeMillis() - 1);
		cashOrder = cashOrderService.create();
	
		comments = new ArrayList<>();
		orderedItems = new ArrayList<>();
		consumer = findPrincipal();
		contents = contentService.findContentInShoppingCart(shoppingCart);
		
		
		
		cashOrder.setConsumerName(consumer.getName());
		cashOrder.setConsumerAddress(consumer.getAddress());
		cashOrder.setCreditCard(consumer.getCreditCard());
		cashOrder.setPlacementMoment(moment);
		cashOrder.setCancellationDate(null);
		cashOrder.setDeliveryDate(null);
		cashOrder.setConsumer(consumer);
		cashOrder.setOrderedItems(orderedItems);
			
		for(String comment : shoppingCart.getComments()){
			comments.add(comment);
		}
		cashOrder.setComments(comments);
		
		
		
		for(Content c: contents){
			orderedItem = orderedItemService.createFromItem(c.getItem());
			orderedItem.setQuantity(c.getQuantity());
			orderedItem.setCashOrder(cashOrder);
			cashOrder.getOrderedItems().add(orderedItem);
		}
		
		
		result = cashOrderService.save(cashOrder);
		for(OrderedItem oi: result.getOrderedItems()){
			orderedItemService.save(oi);
		}
		
		
		shoppingCart = shoppingCartService.EmptyShoppingCart(shoppingCart);
		shoppingCartService.save(shoppingCart);
	}
	
	public void validateCreditCard(Consumer consumer){
		Integer anyo, mes;
		Calendar fecha;
				
		fecha = Calendar.getInstance();
		anyo = fecha.get(Calendar.YEAR);
		mes = fecha.get(Calendar.MONTH) + 1; 
				
		Assert.isTrue(consumer.getCreditCard().getExpirationYear() >= anyo);
		
		if(consumer.getCreditCard().getExpirationYear() == anyo) {
			Assert.isTrue(consumer.getCreditCard().getExpirationMonth() >= mes);
		}
	}
	
	public void modifyProfile(Consumer cons) {
		
		Assert.notNull(cons);
		Consumer consumer;
		String address;
		String phone;
		CreditCard creditCard;
		
		creditCard = cons.getCreditCard();
		address = cons.getAddress();
		phone = cons.getPhone();
		
		
		consumer = findPrincipal();
		consumer.setCreditCard(creditCard);
		consumer.setAddress(address);
		consumer.setPhone(phone);
		
		validateCreditCard(consumer);
		
	
	}
	
	
}
