package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Store extends DomainEntity {
	// ==========Constructor==========
	public Store() {
		super();
	}

	// ==========Atributos==========
	private Integer quantity;

	@Min(0)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	// ==========Relaciones==========
	private Item item;
	private Warehouse warehouse;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

}
