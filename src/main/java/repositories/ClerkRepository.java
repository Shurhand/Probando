package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Clerk;


@Repository
public interface ClerkRepository extends JpaRepository<Clerk, Integer>{
	
	@Query("select c from Clerk c where c.userAccount = ?1")
	Clerk findClerk(UserAccount userAccount);

}
