package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Item extends DomainEntity {
	// ==========Constructor==========

	public Item() {
		super();
	}

	// ==========Atributos==========
	
	@Column(unique = true)
	private String sku;

	private String name;

	private String description;

	private double price;

	private String picture;

	private Collection<String> tags;
	
	private boolean deleted;

	@Pattern(regexp = "\\w{2}-(\\w{4}$)")
	@NotNull
	@Column(unique = true, updatable = false)
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

	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@ElementCollection
	public Collection<String> getTags() {
		return tags;
	}

	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	// ==========Relaciones==========
	private Category category;
	private Collection<Content> contents;
	private Collection<Store> stores;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@NotNull
	@OneToMany(mappedBy = "item", cascade = { CascadeType.ALL })
	public Collection<Content> getContents() {
		return contents;
	}

	public void setContents(Collection<Content> contents) {
		this.contents = contents;
	}

	@NotNull
	@OneToMany(mappedBy = "item")
	public Collection<Store> getStores() {
		return stores;
	}

	public void setStores(Collection<Store> stores) {
		this.stores = stores;
	}

}
