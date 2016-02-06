package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import security.LoginService;
import domain.Category;
import domain.Item;
import domain.Tax;

@Service
@Transactional
public class CategoryService {

	// ================== Managed repository ================== 
	@Autowired
	private CategoryRepository categoryRepository;
	
	// ================== Supporting services ================== 
	@Autowired
	private TaxService taxService;
	
	// ================== Constructors ================== 
	public CategoryService(){
		super();
	}
	
	// ================== Simple CRUD methods ================== 
	public Category create(){
		Category result;
		Tax tax = new Tax();
		boolean admin = false;
		Collection<Authority> authority;
		Collection<Item> items = new ArrayList<Item>();
		
			
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN)){
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		
		tax = taxService.setDefaultTax();
		result = new Category();
		result.setTax(tax);
		result.setItems(items);
		
		return result;
	}
	
	public void save(Category category){
		Assert.notNull(category);
		boolean admin = false;
		Collection<Authority> authority;
		
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN)){
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		if(category.getTax() == null){
			category.setTax(null);
		} else {
			category.setTax(category.getTax());
		}
		categoryRepository.save(category);

	}
	
	public void delete(Category category){
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);
		boolean admin = false;
		Collection<Authority> authority;
			
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN)){
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		categoryRepository.delete(category);
	}
	
	public Category findOne(int categoryID){
		Assert.notNull(categoryID);		
		Assert.isTrue(categoryID != 0);		
		Category result;
		
		result = categoryRepository.findOne(categoryID);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Category> findAll(){
//		Collection<Authority> authority;
		Collection<Category> res;
//		boolean admin = false;
		
//		authority = LoginService.getPrincipal().getAuthorities();
//		for(Authority a : authority){
//			if(a.getAuthority().equals(Authority.ADMIN)){
//				admin = true;
//			}
//		}
//		Assert.isTrue(admin);
		
		res = categoryRepository.findAll();
		Assert.notNull(res);
		
		return res;
	}
	

	
	// ================== Other business methods ================== 
	
	public Category setDefaultCategory(){
		Category result;
		
		result = categoryRepository.setDefaultCategory();
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Category> findCategoriesByTax(Tax t){
		Assert.notNull(t);
		Collection<Category> result;
		
		result = categoryRepository.findCategoriesByTax(t);
		Assert.notNull(result);
		
		return result;
	}
	
}