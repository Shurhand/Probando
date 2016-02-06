package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class ShoppingCart extends DomainEntity {
	// =============Constructor===============
	public ShoppingCart() {
		super();
	}

	// =============Atributos===============
	private Collection<String> comments;

	@ElementCollection
	public Collection<String> getComments() {
		return comments;
	}

	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	
	// =============Relaciones===============
	
	private Consumer consumer;
	private Collection<Content> contents;

	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	@NotNull
	@OneToMany(mappedBy = "shoppingCart")
	public Collection<Content> getContents() {
		return contents;
	}

	public void setContents(Collection<Content> contents) {
		this.contents = contents;
	}

}
