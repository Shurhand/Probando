package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Category;
import domain.Content;
import domain.Item;
import domain.OrderedItem;
import domain.Store;
import domain.Tax;
import repositories.ItemRepository;
import security.Authority;
import security.LoginService;

@Service
@Transactional
public class ItemService {

	// ================== Managed repository ==================
	@Autowired
	private ItemRepository itemRepository;

	// ================== Supporting services ==================
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private TaxService taxService;

	// ================== Constructors ==================
	public ItemService() {
		super();
	}

	// ================== Simple CRUD methods ==================
	public Item create() {
		Item result;
		Category category = new Category();
		Collection<Content> contents = new ArrayList<>();
		Collection<Store> stores = new ArrayList<>();
		result = new Item();
		Tax tax;
		
		boolean admin = false;
		Collection<Authority> authority;

		authority = LoginService.getPrincipal().getAuthorities();
		for (Authority a : authority) {
			if (a.getAuthority().equals(Authority.ADMIN)) {
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		category = categoryService.setDefaultCategory();
		tax = taxService.setDefaultTax();


		result.setCategory(category);
		result.getCategory().setTax(tax);
		result.setContents(contents);
		result.setStores(stores);
		result.setSku(RandomStringUtils.randomAlphanumeric(2) + "-"+RandomStringUtils.randomAlphanumeric(4));
		

		return result;
	}

	public Item create(String sku) {
		Assert.notNull(sku);
		Item result;
		boolean admin = false;
		Collection<Authority> authority;

		authority = LoginService.getPrincipal().getAuthorities();
		for (Authority a : authority) {
			if (a.getAuthority().equals(Authority.ADMIN)) {
				admin = true;
			}
		}
		Assert.isTrue(admin);

		result = create();

		result.setSku(sku);
		result.getCategory().getTax().setUsed(true);
		result.setDeleted(false);

		return result;
	}

	public void save(Item item) {
		Assert.notNull(item);

		boolean admin = false;
		Collection<Authority> authority;

		authority = LoginService.getPrincipal().getAuthorities();
		for (Authority a : authority) {
			if (a.getAuthority().equals(Authority.ADMIN)) {
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		item.getCategory().getTax().setUsed(true);
		itemRepository.save(item);
	}

	public void delete(Item item) {
		Assert.notNull(item);
		Assert.isTrue(!item.isDeleted());
		boolean admin = false;
		Collection<Authority> authority;

		authority = LoginService.getPrincipal().getAuthorities();
		for (Authority a : authority) {
			if (a.getAuthority().equals(Authority.ADMIN)) {
				admin = true;
			}
		}
		Assert.isTrue(admin);

		item.setDeleted(true);
		itemRepository.save(item);
	}

	public Item findOne(int itemID) {
		Assert.isTrue(itemID != 0);

		Item result;

		result = itemRepository.findOne(itemID);

		return result;
	}

	public Collection<Item> findAll() {
		Collection<Item> result;

		result = itemRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// ================== Other business methods ==================
	public Collection<Item> findItemsOrderedByCategory() {

		Collection<Item> result;

		result = itemRepository.findItemsOrderedByCategory();
		Assert.notNull(result);
		for (Item it : result) {
			if (it.isDeleted()) {
				result.remove(it);
			}
		}

		return result;
	}

	public Collection<Item> findItemByCategory(Category c) {
		Assert.notNull(c);
		Collection<Item> res = new ArrayList<>();
		Collection<Item> items = itemRepository.findItemByCategory(c);
		for (Item i : items) {
			if (i.isDeleted() == false) {
				res.add(i);
			}
		}
		return res;
	}

	public Collection<Item> findItemsContainKeyword(String k) {
		Assert.notNull(k);

		Collection<Item> result;

		result = itemRepository.findItemsContainKeyword(k);

		return result;
	}

	public Collection<Item> findAllBestSellingItems() {
		Collection<Item> items = new ArrayList<>();
		Collection<OrderedItem> ordereditems;
		
		
		ordereditems = itemRepository.findAllBestSellingItems();
		
		for(OrderedItem oi : ordereditems){
			for(Item i : findAll()){
				if(i.getSku().equals(oi.getSku())){
					items.add(i);
				}
			}
		}
		return items;
	}
	
	public Collection<Item> findAllWorstSellingItems() {
		Collection<Item> items = new ArrayList<>();
		Collection<OrderedItem> ordereditems;
		
		
		ordereditems = itemRepository.findAllWorstSellingItems();
		
		for(OrderedItem oi : ordereditems){
			for(Item i : findAll()){
				if(i.getSku().equals(oi.getSku())){
					items.add(i);
				}
			}
		}
		Assert.notNull(items);

		return items;
	}
	
	
	public Collection<Item> findItemsDeleted(){		
		Collection<Item> result;
		Collection<Item> allItems;
		
		result = new ArrayList<>();
		allItems = itemRepository.findAll();
		for(Item item:allItems){
			if(item.isDeleted() == true){
				result.add(item);
			}
		}
		
		Assert.notNull(result);
		
		return result;
	}

	public void setUndelete(Item item){
		item.setDeleted(false);
	
	}

	public Collection<Item> findItemsNotDeleted() {
		Collection<Item> result;
		Collection<Item> allItems;
		
		result = new ArrayList<>();
		allItems = itemRepository.findAll();
		for(Item item:allItems){
			if(item.isDeleted() == false){
				result.add(item);
			}
		}
		
		Assert.notNull(result);
		
		return result;
	}
	
	
}
