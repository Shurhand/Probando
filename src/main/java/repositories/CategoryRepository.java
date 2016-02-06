package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Tax;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	@Query("select c from Category c where c.name = 'Indefinido'")
	Category setDefaultCategory();
	
	@Query("select c from Category c where c.tax = ?1")
	Collection<Category> findCategoriesByTax(Tax t);
}