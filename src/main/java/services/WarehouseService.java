package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarehouseRepository;
import security.Authority;
import security.LoginService;
import domain.Store;
import domain.Warehouse;

@Service
@Transactional
public class WarehouseService {
	
	// ================== Managed repository ================== 
	@Autowired
	private WarehouseRepository warehouseRepository;
	
	// ================== Supporting services ================== 
	
	// ================== Constructors ================== 
	public WarehouseService(){
		super();
	
	}
	
	// ================== Simple CRUD methods ================== 
	public Warehouse create(){
		Warehouse result;
		boolean admin = false;
		Collection<Authority> authority;
		Collection<Store> stores = new ArrayList<Store>();
		
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN)){
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		result = new Warehouse();
		result.setStores(stores);
		
		return result;
	}
	
	public void save(Warehouse warehouse){
		Assert.notNull(warehouse);
		boolean admin = false;
		Collection<Authority> authority;
		
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN)){
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		warehouseRepository.save(warehouse);
	}
	
	public void delete(Warehouse warehouse){
		Assert.notNull(warehouse);
		boolean admin = false;
		Collection<Authority> authority;
		
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN)){
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		warehouseRepository.delete(warehouse);
	}
	
	public Warehouse findOne(int warehouseID){
		Assert.notNull(warehouseID);
		Assert.isTrue(warehouseID != 0);
		Warehouse result;
		
		result = warehouseRepository.findOne(warehouseID);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Warehouse> findAll(){
		Collection<Warehouse> result;
		boolean res = false;
		Collection<Authority> authority;
		
		res = false;
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN) || a.getAuthority().equals(Authority.CLERK)){
				res = true;
			}
		}
		Assert.isTrue(res);
		
		result = warehouseRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	

		
	
}
