package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ContentRepository;
import domain.Content;
import domain.Item;
import domain.ShoppingCart;

@Service
@Transactional
public class ContentService {
	
	// ================== Managed repository ================== 
	@Autowired
	private ContentRepository contentRepository;
	
	// ================== Supporting services ================== 
//	@Autowired
//	private ShoppingCartService shoppingCartService;

	// ================== Constructors ================== 
	public ContentService(){
		super();
	}
	
	// ================== Simple CRUD methods ================== 
	public Content create(){

		Content result;
		result = new Content();
//		ShoppingCart shopCart;
//		shopCart = shoppingCartService.create();
//		shoppingCartService.save(shopCart);
//		result.setShoppingCart(shopCart);
		
		return result;
	}
	
	public void save(Content contents){
		Assert.notNull(contents);
		
		contentRepository.save(contents);
	}
	
	public void delete(Content contents){
		Assert.notNull(contents);
		Assert.isTrue(contents.getId() != 0);
		
		contentRepository.delete(contents);
	}
	
	
	public Content findOne(int contentsID){
		Assert.isTrue(contentsID != 0);
		
		Content result;
		
		result = contentRepository.findOne(contentsID);
		Assert.notNull(result);

		return result;
	}
	
	public Collection<Content> findAll(){
		
		Collection<Content> result;
		
		result = contentRepository.findAll();
		Assert.notNull(result);

		return result;
	}
	

	
	// ================== Other business methods ================== 
	public Content findContentByItemAndShoppingCart(Item item, ShoppingCart sC){
		Assert.notNull(item);
		Assert.notNull(sC);
		
		Content result;
		
		result = contentRepository.findContentByItemAndShoppingCart(item, sC);
		
		return result; 		
	}
	public Collection<Content> findContentInShoppingCart(ShoppingCart sC){
		Assert.notNull(sC);
		Collection<Content> result;
		
		result = contentRepository.findContentInShoppingCart(sC);
		return result;
	}
	
	public Collection<Content> emptyContent(Collection<Content> contents){
	
		for(Content c : contents){
			contentRepository.delete(c);
		}
		contents.removeAll(contents);

		return contents;
}
}
