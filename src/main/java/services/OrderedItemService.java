package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OrderedItemRepository;
import domain.CashOrder;
import domain.Item;
import domain.OrderedItem;

@Service
@Transactional
public class OrderedItemService {

	// ================== Managed repository ==================
	@Autowired
	private OrderedItemRepository orderedItemRepository;

	// ================== Supporting services ==================

	// ================== Constructors ==================
	public OrderedItemService() {
		super();
	}

	// ================== Simple CRUD methods ==================
	public OrderedItem create() {
		OrderedItem result;
		result = new OrderedItem();

		return result;
	}

	public void save(OrderedItem orderedItem) {
		Assert.notNull(orderedItem);

		orderedItemRepository.save(orderedItem);
	}

	public Collection<OrderedItem> findAll() {
		Collection<OrderedItem> result;

		result = orderedItemRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public OrderedItem findOne(int orderedItemID) {
		Assert.isTrue(orderedItemID != 0);
		Assert.notNull(orderedItemID);
		OrderedItem result;

		result = orderedItemRepository.findOne(orderedItemID);
		Assert.notNull(result);

		return result;
	}

	public OrderedItem createFromItem(Item it) {
		Assert.notNull(it);
		OrderedItem result;

		result = create();
		result.setSku(it.getSku());
		result.setName(it.getName());
		result.setDescription(it.getDescription());
		result.setPrice(it.getPrice());

		return result;
	}

	public Collection<OrderedItem> findOrderedItemByOrder(CashOrder order) {
		Assert.notNull(order);
		Collection<OrderedItem> res = new ArrayList<>();
		Collection<OrderedItem> orderedItems = orderedItemRepository
				.findItemByOrder(order);
		for (OrderedItem i : orderedItems) {
			res.add(i);
		}
		return res;
	}

}