package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tax;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Integer>{
	
	//----------------------------- Level C -----------------------------
	@Query("select t from Tax t where t.nameCategory = 'General'")
	Tax SetDefaultTax();
	@Query("select t from Tax t where t.nameCategory =?1")
	Tax getTaxFromName(String name);
	
}
