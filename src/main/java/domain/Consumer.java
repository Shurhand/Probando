package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Consumer extends Actor {
	// ==============Constructor=============
	public Consumer() {
		super();
	}

	//==============Atributos==============
	private CreditCard creditCard;
	private String address;

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	@NotNull
	@NotBlank
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	// ===============Relaciones==============

	private Collection<CashOrder> cashOrders;
	private ShoppingCart shoppingCart;

	
	@Valid
	@OneToOne(optional = false, mappedBy = "consumer", cascade = {CascadeType.ALL})
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	@NotNull
	@OneToMany(mappedBy = "consumer")
	public Collection<CashOrder> getCashOrders() {
		return cashOrders;
	}

	public void setCashOrders(Collection<CashOrder> cashOrders) {
		this.cashOrders = cashOrders;
	}
}
