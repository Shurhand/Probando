package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import domain.CashOrder;
import domain.Consumer;
import domain.Content;
import domain.CreditCard;
import domain.Item;
import domain.ShoppingCart;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=false)
public class ConsumerServiceTest extends AbstractTest {
	
	//============== Service under test ============== 
	@Autowired
	private ConsumerService consumerService;

	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserAccountService userAccountService;
	

	
	//======================== Tests ============================
	
	@Test 
	public void testDisplayShoppingCart(){
		authenticate("consumer1");
		ShoppingCart result;
		
		result = consumerService.getShoppingCart();
		System.out.println("=============== Probando Display de ShoppingCart ===============");
		System.out.println(result + "\n");
			
		authenticate(null);
				
	}
	
	@Test  
	public void testCreateConsumer(){
		authenticate("admin");
		Consumer consumer;
		UserAccount userAccount;
		UserAccount testUserAccount;
		Collection<Consumer> allConsumer;
		CreditCard creditCard;
		Collection<CashOrder> orders;

		orders = new ArrayList<>();
		creditCard = new CreditCard();
		consumer = consumerService.create();
		allConsumer = consumerService.findAll();
		
		System.out.println("Test Creación de Consumer");
		System.out.println(consumer);
		System.out.println("Listado de todos los Consumers: ");
		for (Consumer c : allConsumer) {
			System.out.println(c);
		}
		
		creditCard.setHolderName("TestConsumer");
		creditCard.setBrandName("Visa");
		creditCard.setNumber("4160818214885017");
		creditCard.setExpirationMonth(1);
		creditCard.setExpirationYear(2017);
		creditCard.setCvvCode(123);
		
		
		consumer.setName("ConsumerTester");
		consumer.setSurname("Test_Surnames");
		consumer.setEmail("consumerTest@gmail.com");
		consumer.setPhone("954287944");
		consumer.setCreditCard(creditCard);
		consumer.setAddress("Calle Molino");
		consumer.setCashOrders(orders);

		
		userAccount = userAccountService.createByConsumer();
		userAccountService.save(userAccount);
	//	System.out.println(userAccount);
							
		testUserAccount = userAccountService.findUserAccount(userAccount);
	//	System.out.println(testUserAccount);
		consumer.setUserAccount(testUserAccount);
	//	System.out.println(consumer);
	
		consumer.getShoppingCart().setConsumer(consumer);
	//	System.out.println(consumer.getShoppingCart().getConsumer());
		
		consumerService.save(consumer);
		
		List<Consumer> cons = (List<Consumer>) consumerService.findAll();
		shoppingCartService.save(cons.get(cons.size()-1).getShoppingCart());
		
//		Integer res = consumer.getUserAccount().getId();
//		String aux = Integer.toHexString(res);
//		System.out.println(aux);
//		Integer res2 = Integer.parseInt(aux)+1;
//		System.out.println(res2);
//		Consumer a = consumerService.findOne(res2);
//		System.out.println(a);
//		shoppingCartService.save(a.getShoppingCart());
	
		allConsumer = consumerService.findAll();
		System.out.println(allConsumer);

		System.out.println("========== Test de guardado ==============");		
		System.out.println("Listado de Consumers después de guardar: ");
		for (Consumer c : allConsumer) {
			System.out.println(c);
		}
		
		authenticate(null);
	}
	
	@Test 
	public void testAddItemShoppingCart(){
		authenticate("consumer1");
		Collection<Content> contents;
		ShoppingCart shoppingCart;
		Collection<Item> items;
		
		shoppingCart = consumerService.getShoppingCart();
		contents = shoppingCart.getContents();
		System.out.println("Contenido previo al cambio: "+contents);
		items = consumerService.findItemsOrderedByCategory();
		System.out.println("Añadiendo contenido: "+items.iterator().next().getName());
		consumerService.addItemToShoppingCart(items.iterator().next());
		System.out.println("Nuevo contenido del ShoppingCart: "+contents);
		
		authenticate(null);		
		
	}
	
	@Test 
	public void testDeleteItemShoppingCart(){
		authenticate("Consumer3");
		Item item;
		
		item = itemService.findOne(27);
		consumerService.deleteItemFromShoppingCart(item);
		
		System.out.println("=========== Test Borrar Item del carrito ==========");
		System.out.println("Item borrado: " + item.getName());
		System.out.println("\n");	
		
		authenticate(null);
	}
		
	@Test 
	public void testAddComment(){
		authenticate("consumer1");
		String comment;
		
		comment = "Añadiendo un comentario";
		consumerService.addCommentToShoppingCart(comment);
		
		System.out.println("=============== Probando añadir comentarios ===============");
		System.out.println("Comentario añadido: " + comment);
		System.out.println("\n");	
		
		authenticate(null);
		
	}
	
	@Test 
	public void testModifyComment(){
		authenticate("Consumer1");
		ShoppingCart shoppingCart;
		Collection<String> comments;
		String viejoComment,nuevoComment;
		
		
		nuevoComment = "Comentario sin modificar'";
		shoppingCart = shoppingCartService.findOne(10);
		comments = shoppingCart.getComments();
		viejoComment = comments.iterator().next();
		consumerService.modifyCommentInShoppingCart(viejoComment, nuevoComment);
		
		System.out.println("=============== Probando modificar comentarios ===============");
		System.out.println("Viejo comentario: " +viejoComment+ "\n"+"Nuevo comentario: "+nuevoComment);
		System.out.println("\n");	
		
		authenticate(null);
		
	}
	
	@Test 
	public void testDeleteComment(){
		authenticate("Consumer1");
		Collection<String> comments;
		ShoppingCart shoppingCart;
		String commentDeleted;
		
		shoppingCart = shoppingCartService.findOne(10);
		comments = shoppingCart.getComments();
		commentDeleted = comments.iterator().next();
		consumerService.deleteCommentInShoppingCart(commentDeleted);
		
		System.out.println("=============== Probando borrar comentarios ===============");
		System.out.println("Comentario borrado: "+commentDeleted);
		System.out.println("Comprobando que no haya comentarios:" + shoppingCart.getComments());
		System.out.println("\n");
		
		authenticate(null);
	
	}
	
	@Test 
	public void testPlaceOrderFromShoppingCart(){
		authenticate("Consumer1");
		Consumer consumer = consumerService.findPrincipal();
		CashOrder order;
		ShoppingCart shoppingCart;
		Collection<Content> contentidoAntes;
		Collection<Content> contentidoDespues;
		
		shoppingCart = shoppingCartService.getShoppingCartConsumer(consumer);
		contentidoAntes = shoppingCart.getContents();
		order = consumerService.placeOrderFromShoppingCart();
		contentidoDespues = shoppingCart.getContents();
		
		System.out.println("================== Test PlaceOrderFromShoppingCart ====================");
		System.out.println("Carrito de compra y su contenido:\n"+shoppingCart+"\n"+contentidoAntes);
		System.out.println("Pedido y sus objetos:\n"+order+"\n"+order.getOrderedItems());
		System.out.println("Contenido del carrito después de hacer el pedido\n"+contentidoDespues);
	
		authenticate(null);
	}
	
}