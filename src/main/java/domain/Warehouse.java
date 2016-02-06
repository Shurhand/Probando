package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warehouse extends DomainEntity {
	//===============Constructor================
	public Warehouse(){
		super();
	}
	//===============Atributos================
	private String name;

	private String address;

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
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	//===============Relaciones================
	private Collection<Store> stores;

	@NotNull
	@OneToMany(mappedBy="warehouse", cascade = CascadeType.ALL)
	public Collection<Store> getStores() {
		return stores;
	}

	public void setStores(Collection<Store> stores) {
		this.stores = stores;
	}
	
}
