package controllers.consumer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CashOrderService;
import services.OrderedItemService;

import controllers.AbstractController;
import domain.CashOrder;
import domain.OrderedItem;

@Controller
@RequestMapping("/order/consumer")
public class CashOrderConsumerController extends AbstractController {
	// Services------------------------------------------
	@Autowired
	private CashOrderService cashOrderService;
	@Autowired
	private OrderedItemService orderedItemService;

	// Constructors----------------------------------------------
	public CashOrderConsumerController() {
		super();
	}

	// listing-----------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<CashOrder> orders;
		orders = cashOrderService.findByPrincipal();
		res = new ModelAndView("order/list");
		res.addObject("orders", orders);
		res.addObject("requestURI", "order/consumer/list.do");
		return res;
	}

	@RequestMapping(value = "/listitems", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int orderID) {
		ModelAndView res;
		CashOrder order;
		Collection<OrderedItem> orderedItems;
		Double total = 0.0;
		order = cashOrderService.findOne(orderID);
		orderedItems = orderedItemService.findOrderedItemByOrder(order);
		total = calculaTotal(orderedItems);
		res = new ModelAndView("order/listitems");
		res.addObject("ordereditems", orderedItems);
		res.addObject("total", total);
		res.addObject("requestURI", "order/listitems.do");
		return res;
	}
	private Double calculaTotal(Collection<OrderedItem> orderedItems) {
		Double res = 0.0;
		for (OrderedItem o : orderedItems) {
			res += o.getPrice() * o.getQuantity();
		}
		return res;
	}
}
