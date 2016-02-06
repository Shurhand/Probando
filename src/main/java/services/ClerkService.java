package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ClerkRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CashOrder;
import domain.Clerk;

@Service
@Transactional
public class ClerkService {
	
	// ================== Managed repository ================== 
	@Autowired
	private ClerkRepository clerkRepository;
	
	// ================== Supporting services ================== 
	
	// ================== Constructors ================== 
	public ClerkService(){
		super();
	}
	
	// ================== Simple CRUD methods ================== 
	public Clerk create(){
		Clerk result;
		boolean admin = false;
		Collection<Authority> authority;
		Collection<CashOrder> cashOrders;
		
		cashOrders = new ArrayList<>();
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN)){
				admin = true;
			}
		}
		Assert.isTrue(admin);
		
		result = new Clerk();
		result.setCashOrders(cashOrders);
		
		return result;
	}
	
	public void save(Clerk clerk) {
		Assert.notNull(clerk);
		boolean admin;
		Collection<Authority> authority;
		
		admin = false;
		authority = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authority){
			if(a.getAuthority().equals(Authority.ADMIN)){
				admin = true;
			}
		}
		Assert.isTrue(admin);

		clerkRepository.save(clerk);
	}
	
	public Clerk findOne(int clerkId){
		Assert.isTrue(clerkId != 0);
		
		Clerk result;
		
		result = clerkRepository.findOne(clerkId);
		Assert.notNull(result);
		
		return result;		
	}
	
	public Collection<Clerk> findAll(){
		Collection<Clerk> result;
		
		result = clerkRepository.findAll();
		Assert.notNull(result);
		
		return result;		
	}
	

	
	
	// ================== Other business methods ================== 
	public Clerk findPrincipal() {
		Clerk result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = clerkRepository.findClerk(userAccount);
		Assert.notNull(result);
		
		return result;
	}
}