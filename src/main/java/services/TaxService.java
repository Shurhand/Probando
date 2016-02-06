package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Category;
import domain.Item;
import domain.Tax;
import repositories.TaxRepository;
import security.Authority;
import security.LoginService;

@Service
@Transactional
public class TaxService {

	// ================== Managed repository ==================
	@Autowired
	private TaxRepository taxRepository;

	// ================== Supporting services ==================
	@Autowired
	private CategoryService categoryService;

	// ================== Constructors ==================
	public TaxService() {
		super();
	}

	// ================== Simple CRUD methods ==================
	public Tax create() {
		Tax result;
		boolean admin = false;
		Collection<Authority> authority;
		Collection<Category> categories;

		categories = new ArrayList<>();
		authority = LoginService.getPrincipal().getAuthorities();
		for (Authority a : authority) {
			if (a.getAuthority().equals(Authority.ADMIN)) {
				admin = true;
			}
		}
		Assert.isTrue(admin);

		result = new Tax();
		result.setCategories(categories);

		return result;
	}

	public void save(Tax tax) {
		Assert.notNull(tax);
		boolean admin = false;
		Collection<Authority> authority;

		authority = LoginService.getPrincipal().getAuthorities();
		for (Authority a : authority) {
			if (a.getAuthority().equals(Authority.ADMIN)) {
				admin = true;
			}
		}
		Assert.isTrue(admin);

		Integer res = tax.getId();
		Collection<Category> cats = tax.getCategories();
		tax.setCategories(cats);

		taxRepository.save(tax);

		if (res == 0) {
			List<Tax> taxes = (List<Tax>) taxRepository.findAll();
			Tax x = taxes.get(taxes.size() - 1);

			for (Category cat : tax.getCategories()) {
				if (!(cat == null) & tax.getCategories().contains(cat)) {
					cat.setTax(x);
					if (!cat.getItems().isEmpty()) {
						cat.getTax().setUsed(true);
					}
				}
			}
		}

		if (!tax.getCategories().isEmpty() && res != 0) {
			
			for (Category a : tax.getCategories()) {
				
				if (!(a == null) & tax.getCategories().contains(a)) {
					a.setTax(tax);
					if (!a.getItems().isEmpty()) {
						a.getTax().setUsed(true);
					}
				}
			}
		}

		if (res != 0) {
			Collection<Category> catego = categoryService.findCategoriesByTax(tax);
			for (Category ca : tax.getCategories()) {
				if (ca == null && tax.getCategories().size() == 1) {
					for (Category bla : categoryService.findAll()) {
						if (catego.contains(bla)) {
							bla.setTax(null);
						}
					}
				}
			}
		}

	}

	// Solo un Tax que no se haya utilizado nunca puede borrarse
	public void delete(Tax tax) {
		
		Assert.notNull(tax);
		Collection<Category> categories;
		Collection<Item> items;
		boolean admin = false;
		Collection<Authority> authority;

		authority = LoginService.getPrincipal().getAuthorities();
		for (Authority a : authority) {
			if (a.getAuthority().equals(Authority.ADMIN)) {
				admin = true;
			}
		}
		Assert.isTrue(admin);

//		items = Collections.emptyList();
//		categories = categoryService.findCategoriesByTax(tax);
//		for (Category c : categories) {
//			items.addAll(c.getItems());
//		}
//		Assert.isTrue(items.isEmpty());
		for(Category c : tax.getCategories()){
			c.setTax(null);
		}
		Assert.isTrue(!tax.isUsed());
		
		taxRepository.delete(tax);
	}

	public Tax findOne(int taxID) {
		Assert.notNull(taxID);
		Assert.isTrue(taxID != 0);
		Tax result;

		result = taxRepository.findOne(taxID);
		Assert.notNull(result);

		return result;
	}

	public Collection<Tax> findAll() {
		Collection<Tax> result;

		result = taxRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// ================== Other business methods ==================
	public Tax setDefaultTax() {
		Tax result;

		result = taxRepository.SetDefaultTax();
		Assert.notNull(result);

		return result;
	}

}
