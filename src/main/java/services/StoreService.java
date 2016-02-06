package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StoreRepository;
import domain.Store;

@Service
@Transactional
public class StoreService {
	
	// ================== Managed repository ================== 
	@Autowired
	StoreRepository storageRepository;
	
	// ================== Supporting services ================== 
	
	
	// ================== Constructors ================== 
	public StoreService(){
		super();
	
	}
	
	// ================== Simple CRUD methods ================== 
	public Store create(){
		Store result;
		
		result = new Store();
		
		return result;
	}
	
	public void save(Store store){
		Assert.notNull(store);
		
		storageRepository.save(store);
	}
	
	public void delete(Store store){
		Assert.notNull(store);
		
		storageRepository.delete(store);
	}
	
	public Store findOne(int storeID){
		Assert.notNull(storeID);
		Assert.isTrue(storeID != 0);
		Store result;
		
		result = storageRepository.findOne(storeID);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Store> findAll(){
		Collection<Store> result;
		
		result = storageRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
			
}
