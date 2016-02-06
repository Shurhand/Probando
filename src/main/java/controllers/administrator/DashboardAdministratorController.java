package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Consumer;
import domain.Item;
import services.AdminService;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services -------------------------------------------------------
	@Autowired
	private AdminService administratorService;

	// Constructors ---------------------------------------------------
	public DashboardAdministratorController() {
		super();
	}

	// Listing ---------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Consumer> consumersMoreOrders;
		Collection<Consumer> consumersMoreMoneyOnOrders;
		Collection<Item> itemsBestSeller;
		Collection<Item> itemsWorstSeller;

		consumersMoreOrders = administratorService
				.findConsumersMoreCashOrders();
		consumersMoreMoneyOnOrders = administratorService
				.findConsumersSpentMoreMoney();
		itemsBestSeller = administratorService.findAllBestSellingItems();
		itemsWorstSeller = administratorService.findAllWorstSellingItems();

		res = new ModelAndView("dashboard/list");
		res.addObject("consumersMoreOrders", consumersMoreOrders);
		res.addObject("consumersMoreMoneyOnOrders", consumersMoreMoneyOnOrders);
		res.addObject("itemsBestSeller", itemsBestSeller);
		res.addObject("itemsWorstSeller", itemsWorstSeller);

		return res;
	}

}
