package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Consumer;
import domain.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

	@Query("select c.shoppingCart from Consumer c where c=?1")
	ShoppingCart getShoppingCartConsumer(Consumer c);
}
