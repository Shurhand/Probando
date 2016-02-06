	package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ShoppingCartRepository;
import domain.Consumer;
import domain.Content;
import domain.ShoppingCart;

@Service
@Transactional
public class ShoppingCartService {
	
	// ================== Managed repository ================== 
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	// ================== Supporting services ================== 
	
	@Autowired
	private ContentService contentService;

	// ================== Constructors ================== 
	public ShoppingCartService(){
		super();
	}
	
	// ================== Simple CRUD methods ================== 
	public ShoppingCart create(){
		ShoppingCart result;
		Collection<Content> contents;
		Collection<String> comments;
		comments = new ArrayList<>();
		contents = new ArrayList<>();
		result = new ShoppingCart();
		result.setComments(comments);
		result.setContents(contents);
		
		return result;
	}
	
	public void save(ShoppingCart shoppingCart){
		Assert.notNull(shoppingCart);
	
		
		if (shoppingCart.getContents() != null && !shoppingCart.getContents().isEmpty()) {
			for (Content c : shoppingCart.getContents()) {
				contentService.save(c);
			}
		}
		System.out.println(shoppingCart);
		System.out.println(shoppingCart.getConsumer());
		shoppingCartRepository.save(shoppingCart);
	
	}
	
	public void delete(ShoppingCart shoppingCart){
		Assert.notNull(shoppingCart);
		Assert.isTrue(shoppingCart.getId() != 0);
		
		shoppingCartRepository.delete(shoppingCart);
	}
	
	public ShoppingCart findOne(int shoppingCartID){
		Assert.isTrue(shoppingCartID != 0);
		
		ShoppingCart result;
		
		result = shoppingCartRepository.findOne(shoppingCartID);
		Assert.notNull(result);
		
		return result;		
	}
	
	public Collection<ShoppingCart> findAll(){
		Collection<ShoppingCart> result;
		
		result = shoppingCartRepository.findAll();
		Assert.notNull(result);
		
		return result;		
	}
		
	
	// ================== Other business methods ================== 
	public ShoppingCart getShoppingCartConsumer(Consumer cons){
		Assert.notNull(cons);
		
		ShoppingCart result;
		
		result = shoppingCartRepository.getShoppingCartConsumer(cons);
		Assert.notNull(result);
		
		return result;
	}
	
	public ShoppingCart EmptyShoppingCart(ShoppingCart shoppingCart){
		Collection<Content> contents = new ArrayList<>();
		
		
		shoppingCart.getComments().removeAll(shoppingCart.getComments());
		contents = contentService.emptyContent(shoppingCart.getContents());
		shoppingCart.setContents(contents);
		
		return shoppingCart;
	}

}
