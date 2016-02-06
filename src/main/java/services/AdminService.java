package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Admin;
import domain.CashOrder;
import domain.Category;
import domain.Clerk;
import domain.Consumer;
import domain.Item;
import domain.OrderedItem;
import domain.Store;
import domain.Tax;
import repositories.AdminRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdminService {
	
	// ================== Managed repository ================== 
	@Autowired
	private AdminRepository adminRepository;
	
	// ================== Supporting services ================== 
	@Autowired
	private ConsumerService consumerService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TaxService taxService;
	
	@Autowired
	private ClerkService clerkService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CashOrderService cashOrderService;
	

	
	// ================== Constructors ================== 
	public AdminService(){
		super();
	}
	
	// ================== Simple CRUD methods ================== 
	public Admin create(){
		Admin result;
		
		result = new Admin();
		
		return result;
	}
	
	public void save(Admin admin){
		Assert.notNull(admin);
		
		adminRepository.save(admin);
	}
	
	public void delete(Admin admin){
		Assert.notNull(admin);
		Assert.isTrue(admin.getId() != 0);
		
		adminRepository.delete(admin);
	}
	
	public Admin findOne(int adminID){
		Assert.isTrue(adminID != 0);
		
		Admin result;
		
		result = adminRepository.findOne(adminID);
		Assert.notNull(result);
		
		return result;		
	}
	
	public Collection<Admin> findAll(){
		Collection<Admin> result;
		
		result = adminRepository.findAll();
		Assert.notNull(result);
		
		return result;		
	}
	

	
	// ================== Other business methods ================== 
	public Admin findPrincipal() {
		Admin result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = adminRepository.findAdmin(userAccount);
		Assert.notNull(result);
		
		return result;
	}
	
	//Display consumer/s with more orders
	public Collection<Consumer> findConsumersMoreCashOrders(){
		Collection<Consumer> result;
		
		result = consumerService.findConsumersMoreCashOrders();
		Assert.notNull(result);
		
		return result;
	}
	// List the catalogue of items grouped by their categories
	public Collection<Item> findItemsOrderedByCategory(){
		Collection<Item> result;
			
		result = itemService.findItemsOrderedByCategory();

		return result;
	}
	
	//Search an item using a keyword
	public Collection<Item> findItemsContainKeyword(String k){
		Assert.notNull(k);
		
		Collection<Item> result;
		
		result = itemService.findItemsContainKeyword(k);
			
		return result;
	}
	
	
	//Display consumer/s who have spent more money on their orders
	public Collection<Consumer> findConsumersSpentMoreMoney(){
		Collection<Consumer> result;
		
		result = consumerService.findConsumersSpentMoreMoney();
		Assert.notNull(result);	
		
		return result;
	}
	
	//Display the best selling item/s
	public Collection<Item> findAllBestSellingItems(){
		Collection<Item> result;
		
		result = itemService.findAllBestSellingItems();
		Assert.notNull(result);
		
		return result;
	}
	
	//Display the worst selling item/s
	public Collection<Item> findAllWorstSellingItems(){
		Collection<Item> result;
		
		result = itemService.findAllWorstSellingItems();
		Assert.notNull(result);
		
		return result;
	}
	
	// List the consumers that are registered in the system
	public Collection<Consumer> getRegisteredConsumers() {
		Collection<Consumer> result;

		result = consumerService.findAll();
		Assert.notNull(result);

		return result;
	}
	
	// List the orders that are registered in the system
	public Collection<CashOrder> getRegisteredOrders() {
		Collection<CashOrder> result;

		result = cashOrderService.findAll();
		Assert.notNull(result);

		return result;
	}
	
	//Create category
	public Category createCategory(){
		Category result;
		Collection<Category> allCategories;
		
		allCategories = categoryService.findAll();
		Assert.notNull(allCategories);
		result = categoryService.create();
		Assert.notNull(result);
		Assert.isTrue(!allCategories.contains(result), "This category already exist");
		
		return result;		
	}
	
	//Listing category
	public Collection<Category> listingCategory(){
		Collection<Category> result;
		
		result = categoryService.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	//Modify category
	public void modifyCategory(Category c, String n, String d, 
			String p, Collection<Item> items, Tax t){
		Assert.notNull(c);
		
		c.setName(n);
		c.setDescription(d);
		c.setPicture(p);
		c.setItems(items);
		c.setTax(t);
		
		categoryService.save(c);	
	
	}
	
	//Delete category
	public void deleteCategory(Category category){
		Assert.notNull(category);
		
		categoryService.delete(category);
		
	}
	//Create Item
	public Item createItem(String sku){
		Item result;
		
		result = itemService.create(sku);
		
		return result;	
	}
	
	//Update Item
	public void modifyItem(Item item, String sku, String name, String description, double price, 
			Collection<String> tags, String picture, Category category, Collection<Store> stores){
		Assert.notNull(item);
		
		item.setSku(sku);
		item.setName(name);
		item.setDescription(description);
		item.setPrice(price);
		item.setTags(tags);
		item.setPicture(picture);
		item.setCategory(category);
		item.setStores(stores);
	}
	
	//Delete Item
	public void deleteItem(Item item){
		Assert.notNull(item);
		Assert.isTrue(!item.isDeleted());
		
		item.setDeleted(true);
	}

	//Create  Tax
	public Tax createTax(){
		Tax result;
		
		result = taxService.create();
		
		return result;
	}
	
	//Update Tax
	public void modifyTax(Tax tax, String name, double percent, Collection<Category> categories){
		Assert.notNull(tax);
		
		tax.setNameCategory(name);
		tax.setPercent(percent);
		tax.setCategories(categories);
		
		taxService.save(tax);
	}
	
	public void deleteTax(Tax tax){
		Assert.notNull(tax);
		Collection<Category> categories;
		
		categories = tax.getCategories();
		Assert.isTrue(categories.isEmpty());
				
		taxService.delete(tax);
	}
	
	public Clerk registerNewClerk(){
		Clerk result;
		
		result = clerkService.create();
		
		return result;
	}
}
