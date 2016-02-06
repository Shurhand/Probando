package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CashOrder;

@Repository
public interface CashOrderRepository extends JpaRepository<CashOrder, Integer> {

	@Query("select o from CashOrder o")
	Collection<CashOrder> getRegisteredCashOrders();
	@Query("select c from CashOrder c where c.consumer.id = ?1")
	Collection<CashOrder> findByConsumerId(int consumerID);
}
