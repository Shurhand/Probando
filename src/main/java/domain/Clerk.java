package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Clerk extends Actor {
	// =============Constructor============
	public Clerk() {
		super();
	}

	// ==============Relaciones=============
	private Collection<CashOrder> cashOrders;

	@NotNull
	@OneToMany(mappedBy = "clerk")
	public Collection<CashOrder> getCashOrders() {
		return cashOrders;
	}

	public void setCashOrders(Collection<CashOrder> cashOrders) {
		this.cashOrders = cashOrders;
	}
}
