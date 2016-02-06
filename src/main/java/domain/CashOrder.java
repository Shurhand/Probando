package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class CashOrder extends DomainEntity {
	// ==============Constructor=========
	public CashOrder() {
		super();
	}

	// ==============Atributos==========
	private String ticker;
	private String consumerName;
	private String consumerAddress;
	private CreditCard creditCard;
	private Collection<String> comments;
	private Date placementMoment;
	private Date deliveryDate;
	private Date cancellationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(Date cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "\\d{6}-(\\w{4}$)")
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getPlacementMoment() {
		return placementMoment;
	}

	public void setPlacementMoment(Date placementMoment) {
		this.placementMoment = placementMoment;
	}

	@ElementCollection
	public Collection<String> getComments() {
		return comments;
	}

	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	@NotBlank
	@NotNull
	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	@NotBlank
	@NotNull
	public String getConsumerAddress() {
		return consumerAddress;
	}

	public void setConsumerAddress(String consumerAddress) {
		this.consumerAddress = consumerAddress;
	}

	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	// ==============Relaciones=========
	private Consumer consumer;
	private Clerk clerk;
	private Collection<OrderedItem> orderedItems;

	@Valid
	@NotNull
	@ManyToOne(optional = true)
	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	@Valid
	@ManyToOne(optional = true)
	public Clerk getClerk() {
		return clerk;
	}

	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}

	@NotEmpty
	@OneToMany(mappedBy = "cashOrder", cascade = CascadeType.ALL)
	public Collection<OrderedItem> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(Collection<OrderedItem> orderedItems) {
		this.orderedItems = orderedItems;
	}
}
