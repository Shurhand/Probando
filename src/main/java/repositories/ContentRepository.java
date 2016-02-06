package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Content;
import domain.Item;
import domain.ShoppingCart;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {

	@Query("select c from Content c where c.item = ?1 and c.shoppingCart = ?2")
	Content findContentByItemAndShoppingCart(Item i, ShoppingCart sC);
	
	@Query("select c from Content c where c.shoppingCart = ?1")
	Collection<Content> findContentInShoppingCart(ShoppingCart sC);
}
