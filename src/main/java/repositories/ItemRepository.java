package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Item;
import domain.OrderedItem;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer>{

	@Query("select i from Item i group by i.category")
	Collection<Item> findItemsOrderedByCategory();
	
	@Query("select i from Item i where ((i.sku like %?1% or i.name like %?1% or i.description like %?1%) and i.deleted = false))")
	Collection<Item> findItemsContainKeyword(String k);
	
	@Query("select item from CashOrder c join c.orderedItems item group by item.sku having sum(item.quantity) >= all(select sum(item.quantity) from CashOrder c join c.orderedItems item group by item.sku)")
	Collection<OrderedItem> findAllBestSellingItems();
	
	@Query("select item from CashOrder c join c.orderedItems item group by item.sku having sum(item.quantity) <= all(select sum(item.quantity) from CashOrder c join c.orderedItems item group by item.sku)")
	Collection<OrderedItem> findAllWorstSellingItems();
	
	@Query("select i from Item i where i.category = ?1")
	Collection<Item> findItemByCategory(Category category);
}
