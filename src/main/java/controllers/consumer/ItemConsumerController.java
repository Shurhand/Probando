package controllers.consumer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ConsumerService;
import services.ItemService;

import controllers.AbstractController;
import domain.Category;
import domain.Item;

@Controller
@RequestMapping("/item/consumer")
public class ItemConsumerController extends AbstractController {
	// Services------------------------------------------
	@Autowired
	private ItemService itemService;
	@Autowired
	private ConsumerService consumerService;
	@Autowired
	private CategoryService categoryService;

	// Constructors----------------------------------------------
	public ItemConsumerController() {
		super();
	}

	// listing-----------------------------------------
	@RequestMapping(value = "/categorylist", method = RequestMethod.GET)
	public ModelAndView categorylist() {
		ModelAndView res;
		Collection<Category> categories;
		categories = categoryService.findAll();
		res = new ModelAndView("item/categorylist");
		res.addObject("categories", categories);
		res.addObject("requestURI", "item/categorylist.do");
		return res;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int categoryID) {
		ModelAndView res;
		Category category;
		Collection<Item> items;
		category = categoryService.findOne(categoryID);
		items = itemService.findItemByCategory(category);
		res = new ModelAndView("item/list");
		res.addObject("items", items);
		res.addObject("requestURI", "item/list.do");
		return res;
	}
	
	//Add---------------------------------------------------------
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int itemID) {
		ModelAndView res;
		Item item;
		item = itemService.findOne(itemID);
		consumerService.addItemToShoppingCart(item);
		res = new ModelAndView("redirect:/shoppingCart/consumer/list.do");

		return res;
	}

}
