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
public class Content extends DomainEntity {
	// ==============Constructor==========
	public Content() {
		super();
	}

	// ==============Atributos============
	private Integer quantity;

	@Min(0)
	@NotNull
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	//=============Relaciones=============
	private ShoppingCart shoppingCart;
	private Item item;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public ShoppingCart getShoppingCart(){
		return shoppingCart;
	}
	
	public void setShoppingCart(ShoppingCart shoppingCart){
		this.shoppingCart = shoppingCart;
	}
	
	@Valid
	@ManyToOne(optional=true)
	public Item getItem(){
		return item;
	}
	
	public void setItem(Item item){
		this.item = item;
	}
}
