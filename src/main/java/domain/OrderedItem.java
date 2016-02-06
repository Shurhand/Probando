package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class OrderedItem extends DomainEntity {
	// ==========Constructor=========

	public OrderedItem() {
		super();
	}

	// ==========Atributos===========
	private String sku;

	private String name;

	private String description;

	private double price;
	
	private int quantity;

	@NotNull
	@Pattern(regexp = "^\\w{2}-(\\w{4}$)")
//	@Column(unique = true, updatable = false)
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@NotBlank
	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@NotNull
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Min(0)
	@Digits(integer=9,fraction=2)
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	@Min(1)
	@NotNull
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// ==========Relaciones==========
	private CashOrder cashOrder;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public CashOrder getCashOrder() {
		return cashOrder;
	}

	public void setCashOrder(CashOrder cashOrder) {
		this.cashOrder = cashOrder;
	}


}
