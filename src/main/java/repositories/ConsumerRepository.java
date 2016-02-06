package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Consumer;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Integer> {

	@Query("select cs from Consumer cs where cs.userAccount is not null")
	Collection<Consumer> getRegisteredConsumers();
	
	@Query("select c from Consumer c where c.cashOrders.size = (select max(c1.cashOrders.size) from Consumer c1)")
	Collection<Consumer> findConsumersMoreCashOrders();
	
	@Query("select o.consumer from CashOrder o join o.orderedItems item group by o.ticker having sum(item.price * item.quantity) >= all(select sum(item.price * item.quantity) from CashOrder o join o.orderedItems item group by o.ticker)")
	Collection<Consumer> findConsumersSpentMoreMoney();
	
	@Query("select c from Consumer c where c.userAccount = ?1")
	Consumer findConsumer(UserAccount user);
}
