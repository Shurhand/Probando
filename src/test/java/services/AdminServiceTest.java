package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import domain.Admin;
import domain.CashOrder;
import domain.Category;
import domain.Consumer;
import domain.Content;
import domain.Item;
import domain.OrderedItem;
import domain.Store;
import domain.Tax;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=false)
public class AdminServiceTest extends AbstractTest{

	// ============== Service under test ============== 
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private TaxService taxService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private StoreService storeService;

	
	@Test
	public void testCreateAdmin(){
		Admin admin;
		UserAccount userAccount;
		UserAccount testUserAccount;
		Collection<Admin> allAdmins;

		allAdmins = adminService.findAll();
		admin = adminService.create();
		
		System.out.println("Probando creación");
		System.out.println(admin);		
		System.out.println("Admins actuales:  ");
		for(Admin a: allAdmins){
			System.out.println(a);
		}
		
		admin.setName("TestAdmin");
		admin.setSurname("Surname");
		admin.setEmail("TestAdmin@hotmail.com");
		admin.setPhone("951346132");
		
		userAccount = userAccountService.createByAdministrator();
		userAccountService.save(userAccount);
		
		testUserAccount = userAccountService.findUserAccount(userAccount);
		admin.setUserAccount(testUserAccount);
		adminService.save(admin);
		
		allAdmins = adminService.findAll();

		System.out.println("============== Probando guardar Admin ==============");
		System.out.println("Admins despues de guardar: ");
		for(Admin a: allAdmins){
			System.out.println(a);
		}
	}
	
	@Test
	public void testFindPrincipal(){
		authenticate("admin");
		Admin admin;
		
		admin = adminService.findPrincipal();
		System.out.println("============== Probando FindByPrincipal ==============");
		System.out.println(admin);
		
		authenticate(null);		
	}
	
	@Test
	public void testListItemsCatalogue(){
		Collection<Item> items;
		
		items = adminService.findItemsOrderedByCategory();
		System.out.println("Probando listar Items");
		for(Item i: items){
			System.out.println(i);
		}
	}
	
	@Test
	public void testCreateItem(){
		authenticate("admin");
		Item item;
		Collection<Item> allItems;
		
		item = adminService.createItem("15-MH12");
		allItems = itemService.findAll();
		
		System.out.println("================ Test Crear Item ================");
		System.out.println(item);
		System.out.println("Todos los items actuales:");
		for(Item i: allItems){
			System.out.println(i);
		}

		item.setName("testItem");
		item.setDescription("Test");
		item.setPrice(1.00);
		item.setTags(Collections.<String> emptyList());
		item.setDeleted(false);
		item.setContents(Collections.<Content> emptyList());
		item.setStores(Collections.<Store> emptyList());

		
		
		itemService.save(item);
		allItems = itemService.findAll();
		
		System.out.println("================ Test Guardar Item ================");
		System.out.println("Todos los items actualizados:");
		for(Item i: allItems){
			System.out.println(i);
		}

		authenticate(null);
	}
	
	@Test
	public void testModifyItem(){
		authenticate("admin");
		Item item;
		Collection<String> tags = new ArrayList<>();
		tags.add("Vino");
		tags.add("Alcohol");
		Collection<Store> nuevasStores = new ArrayList<>();
		Store nuevaStore;
		Category nuevaCategoria;
		
		nuevaStore = storeService.findOne(43);
		nuevasStores.add(nuevaStore);
		
		nuevaCategoria = categoryService.findOne(22);		
		item = itemService.findOne(27);
		
		System.out.println("================ Test UpdateItem ================ ");
		System.out.println("Item antes de modificar: \n "
				+ "Sku: "+item.getSku()+"\n "
				+ "Name: "+item.getName()+"\n "
				+ "Description: "+item.getDescription()+"\n "
				+ "Price: "+item.getPrice()+"\n "
				+ "Picture: "+item.getPicture()+"\n "
				+ "Tags: "+item.getTags()+"\n "
				+ "Category: "+item.getCategory()+"\n "
				+ "Storages: "+item.getStores());

		adminService.modifyItem(item, "56-BA12", "Rivera del Duero", "Vino 12 años", 25.99, tags,
				null, nuevaCategoria, nuevasStores);
		
		System.out.println("Item Después de modificar: \n "
				+ "Sku: "+item.getSku()+"\n "
				+ "Name: "+item.getName()+"\n "
				+ "Description: "+item.getDescription()+"\n "
				+ "Price: "+item.getPrice()+"\n "
				+ "Picture: "+item.getPicture()+"\n "
				+ "Tags: "+item.getTags()+"\n "
				+ "Category: "+item.getCategory()+"\n "
				+ "Storages: "+item.getStores());
		
		authenticate(null);
	}
	
	@Test
	public void testDeleteItem(){
		authenticate("admin");
		Item item;
		Collection<Item> allItems;
		
		item = itemService.findOne(27);
		allItems = adminService.findItemsOrderedByCategory();
		
		System.out.println("================ Test DeleteItem ================ ");
		System.out.println("Item elegido para borrarse:\n"+ item);
		System.out.println("Todos los items antes de borrar:");
		for(Item i: allItems){
			System.out.println(i);
		}
		
		adminService.deleteItem(item);
		allItems = itemService.findAll();

		System.out.println("Todos los items después de borrar:");
		for(Item i: allItems){
			if(!i.isDeleted())
			System.out.println(i);
		}

		authenticate(null);
	}
	
//	@Test
//	public void testFindItemByKeyword(){
//		Item itemByName;
//		Item itemBySku;
//		Item itemByDescription;
//		Item itemNoEncontrado;
//		
//		itemByName = adminService.findItemContainsKeyword("Malla Mandarinas 3 Kg");
//		itemBySku = adminService.findItemContainsKeyword("AA-0001");
//		itemByDescription = adminService.findItemContainsKeyword("Fragancia Invictus de Paco Rabanne, 100 mL");
//		itemNoEncontrado = adminService.findItemContainsKeyword("Vino Rivera del Duero");
//		
//		System.out.println("================ Test FindItemsByKeyword ================ ");
//		System.out.println(itemByName);
//		System.out.println(itemBySku);
//		System.out.println(itemByDescription);	
//		System.out.println(itemNoEncontrado);	
//	}
	
	

	@Test
	public void testCreateTax(){
		authenticate("admin");
		Tax tax;
		Collection<Tax> allTax;
		
		tax = adminService.createTax();
		allTax = taxService.findAll();
		
		System.out.println("============== Probando creación ==============");
		System.out.println(tax);
		System.out.println("Taxes actuales: ");
		for(Tax t: allTax){
			System.out.println(t);
		}
		
		tax.setNameCategory("testTax");
		tax.setPercent(42.22);
		tax.setCategories(Collections.<Category> emptyList());
		taxService.save(tax);
		allTax = taxService.findAll();

		System.out.println("============== Probando guardar Tax ==============");
		System.out.println("Taxes después de guardar: ");
		for(Tax t: allTax){
			System.out.println(t);
		}
		
		authenticate(null);
	}
	
	@Test
	public void testModifyTax(){
		authenticate("admin");
		Tax tax;

		tax = taxService.findOne(16); 
		
		System.out.println("============== Probando actualizar Tax ==============");
		System.out.println("Tax antes de actualizar:\n "
				+ "Nombre: "+tax.getNameCategory()+"\n "
				+ "Porcentaje: "+tax.getPercent()+"\n"+tax.getCategories());
		
		adminService.modifyTax(tax, "Reducido", 10.00, tax.getCategories());
		
		System.out.println("Tax despues de actualizar:\n "
				+ "Nombre: "+tax.getNameCategory()+"\n "
						+ "Portentaje: "+tax.getPercent()+"\n"+tax.getCategories());

		authenticate(null);
	}
	
	@Test
	public void testDeleteTax(){
		authenticate("admin");
		Tax tax;
		Collection<Tax> allTax;

		tax = taxService.findOne(17); 
		allTax = taxService.findAll();
		
		System.out.println("============== Test borrar Tax ==============");
		System.out.println("Tax seleccionado:\n"+tax);
		System.out.println("Todas las Taxes antes de borrar: ");
		for(Tax t: allTax){
			System.out.println(t);
		}
		
		adminService.deleteTax(tax);
		allTax = taxService.findAll();

		System.out.println("Taxes actuales después de borrar: ");
		for(Tax t: allTax){
			System.out.println(t);
		}

		authenticate(null);
	}
	
	@Test
	public void testCreateCategory(){
		authenticate("admin");
		Category category;
		Collection<Category> allCategories;
		
		category = adminService.createCategory();
		allCategories = adminService.listingCategory();
		
		System.out.println("============== Test CreateCategory ==============");
		System.out.println(category);
		System.out.println("Categorías actuales:");
		for(Category c: allCategories){
			System.out.println(c);
		}
		
		category.setName("Limpiza");
		category.setDescription("Productos de limpieza");
		category.setPicture(null);
		category.setItems(Collections.<Item> emptyList());
		categoryService.save(category);
		allCategories = adminService.listingCategory();

		System.out.println("Categorías después:");
		for(Category c: allCategories){
			System.out.println(c);
		}
		
		authenticate(null);
	}
	
	@Test
	public void testModifyCategory(){
		authenticate("admin");
		Category category;
		Tax tax;
		Item item;
		Collection<Item> items;
		
		items = new ArrayList<>();
		tax = taxService.findOne(16);
		item = itemService.findOne(25);
		items.add(item);

		category = categoryService.findOne(22);
		
		System.out.println("============== Test UpdateCategory ==============");
		System.out.println("Category antes de actualizar:\n "
				+ "Name: "+category.getName()+"\n "
				+ "Description: "+category.getDescription()+"\n "
				+ "Picture: "+category.getPicture()+"\n "
				+ "Items: "+category.getItems()+"\n "
				+ "Tax: "+category.getTax().getNameCategory());
		
		adminService.modifyCategory(category, "Pescadería", "Pescados y mariscos", null, items, tax);
		
		System.out.println("Category después de actualizar:\n "
				+ "Name: "+category.getName()+"\n "
				+ "Description: "+category.getDescription()+"\n "
				+ "Picture: "+category.getPicture()+"\n "
				+ "Items: "+category.getItems()+"\n "
				+ "Tax: "+category.getTax().getNameCategory());
		
		authenticate(null);		
	}
	
	@Test
	public void testDeleteCategory(){
		authenticate("admin");
		Category category;
		Collection<Category> allCategories;

		category = categoryService.findOne(22);
		allCategories = adminService.listingCategory();
		
		System.out.println("============== Test borrar Categoría ============== ");
		System.out.println("Categoría a borrar:\n"+category);
		System.out.println("Todas las categorías antes de borrar: ");
		for(Category c: allCategories){
			System.out.println(c);
		}
		
		adminService.deleteCategory(category);
		allCategories = adminService.listingCategory();

		System.out.println("Todas las categorías después de borrar: ");
		for(Category c: allCategories){
			System.out.println(c);
		}
		
		authenticate(null);
	}

	
	@Test
	public void testListingCategory(){
		authenticate("admin");
		Collection<Category> allCategories;
		
		allCategories = adminService.listingCategory();
		
		System.out.println("============== Probando listar categorías ============== ");
		System.out.println("Categorías actuales: ");
		for(Category c: allCategories){
			System.out.println(c);
		}
		
		authenticate(null);
	}
	

	@Test
	public void testgetRegisteredConsumers(){
		authenticate("admin");
		Collection<Consumer> allConsumers;
		
		allConsumers = adminService.getRegisteredConsumers();
		
		System.out.println("============== Consumers registrados ============== ");
		for(Consumer c: allConsumers){
			System.out.println(c);
		}
		
		authenticate(null);
	}
	
	@Test
	public void testRegisteredOrders(){
		authenticate("admin");
		Collection<CashOrder> allCashOrders;
		
		allCashOrders = adminService.getRegisteredOrders();
		
		System.out.println("============== CashOrders registrados ============== ");
		for(CashOrder o: allCashOrders){
			System.out.println(o);
		}

		authenticate(null);
	}
	
	@Test
	public void testfindConsumersSpentMoreMoney(){
		authenticate("admin");
		Collection<Consumer> consumers;
		
		consumers = adminService.findConsumersSpentMoreMoney();
		
		System.out.println("============== Consumers que gastan más ============== ");
		for(Consumer c: consumers){
			System.out.println(c);
		}
		
		authenticate(null);
	}
	
	@Test
	public void testConsumersWithMoreOrders(){
		authenticate("admin");
		Collection<Consumer> consumers;
		
		consumers = adminService.findConsumersMoreCashOrders();
		
		System.out.println("============== Consumer con mas CashOrders ============== ");
		for(Consumer c: consumers){
			System.out.println(c);
		}
		
		authenticate(null);
	}
	
	@Test
	public void testfindAllBestSellingItems(){
		authenticate("admin");
		Collection<Item> items;
		
		items = adminService.findAllBestSellingItems();
		
		System.out.println("============== Test Items que más venden ============== ");
		for(Item i: items){
			System.out.println(i);
		}
		
		authenticate(null);
	}
	
	
	@Test
	public void testfindAllWorstSellingItems(){
		authenticate("admin");
		Collection<Item> items;
		
		items = adminService.findAllWorstSellingItems();
		
		System.out.println("============== Test Items que menos venden ============== ");
		for(Item i: items){
			System.out.println(i);
		}
		
		authenticate(null);
	}

	

}
