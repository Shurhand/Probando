package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CashOrder;
import domain.OrderedItem;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Integer>{
	@Query("select o from OrderedItem o where o.cashOrder = ?1")
	Collection<OrderedItem> findItemByOrder(CashOrder cashOrder);
}
