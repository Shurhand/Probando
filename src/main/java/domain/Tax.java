package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Tax extends DomainEntity {
	// ================Constructor================
	public Tax() {
		super();
	}

	// ================Atributos================
	private String nameCategory;

	private double percent;
	
	private boolean used;

	@NotBlank
	@NotNull
	public String getNameCategory() {
		return nameCategory;
	}

	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}

	@Range(min = 0, max = 100)
	@Digits(integer = 2, fraction = 2)
	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	
	// ================Relaciones================



	private Collection<Category> categories;

	@NotNull
	@OneToMany(mappedBy = "tax")
	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

}
