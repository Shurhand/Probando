package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {
	
	// ================== Managed repository ================== 
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	// ================== Supporting services ================== 
	
	// ================== Constructors ================== 

	// ================== Simple CRUD methods ================== 
	public UserAccount createByConsumer(){
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority("CONSUMER");
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result = new UserAccount();
		result.setUsername("ConsumerTester");
		result.setPassword("consumer");
		result.setAuthorities(authorities);
		
		return result;
	}
	
	public UserAccount createByClerk(){
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		result = new UserAccount();
		result.setUsername("ClerkTester");
		result.setPassword("clerk");
		
		authority = new Authority();
		authority.setAuthority("CLERK");
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result.setAuthorities(authorities);
		
		return result;
	}
	
	public UserAccount createByAdministrator(){
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		result = new UserAccount();
		result.setUsername("AdminTester");
		result.setPassword("admin");
		
		authority = new Authority();
		authority.setAuthority("ADMIN");
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result.setAuthorities(authorities);
		
		return result;
	}
	
	public UserAccount findOne(int ID){
		Assert.notNull(ID);
		Assert.isTrue(ID != 0);
		UserAccount result;
		
		result = userAccountRepository.findOne(ID);
		
		return result;
	}
	
	public UserAccount findUserAccount(UserAccount userAccount){
		Assert.notNull(userAccount);
		UserAccount result;
		
		result = userAccountRepository.findByUsername(userAccount.getUsername());
		
		return result;
	}
	
	public void save(UserAccount userAccount){
		Assert.notNull(userAccount);
		
		userAccountRepository.save(userAccount);
	}
	
}
