package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Category;
import domain.Item;
import domain.Tax;
import services.AdminService;
import services.CategoryService;
import services.ItemService;
import services.TaxService;


@Controller
@RequestMapping("/item/administrator")
public class ItemAdministratorController extends AbstractController {
	// Services------------------------------------------
	@Autowired
	private ItemService itemService;
	@Autowired
	private AdminService administratorService;
	@Autowired
	private TaxService taxService;
	@Autowired
	private CategoryService categoryService;

	// Constructors----------------------------------------------
	public ItemAdministratorController() {
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
	public ModelAndView ist(@RequestParam int categoryID) {
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
	
	// List Items Deleted
	@RequestMapping(value = "/list-itemsDeleted", method = RequestMethod.GET)
	public ModelAndView listItemsDeleted() {
		ModelAndView result;
		Collection<Item> items;
			
		items = itemService.findItemsDeleted();

		result = new ModelAndView("item/list-itemsDeleted");
		result.addObject("items", items);

		return result;
	}

	// Creation--------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Item item;
		Collection<Category> categories = new ArrayList<>();
		categories = categoryService.findAll();
		item = itemService.create();
		
		res = new ModelAndView("item/create");
		res.addObject("item", item);
		res.addObject("categories", categories);
		return res;
	}

	// Edition--------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int itemID) {
		ModelAndView res;
		Item item;
		item = itemService.findOne(itemID);
		Assert.notNull(item);
		res = createEditModelAndView(item);
		return res;
	}

	@RequestMapping(value = {"/create","/edit","/delete"}, method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Item item, BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			for(ObjectError e : binding.getAllErrors()){
				System.out.println(e);
			}
			res = createEditModelAndView(item);
		} else {
			try{
				itemService.save(item);
				res = new ModelAndView("redirect:categorylist.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(item, "category.commit.error");
			}
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Item item, BindingResult binding) {
		ModelAndView res;
		try {
			itemService.delete(item);
			res = new ModelAndView("redirect:categorylist.do");
		} catch (Throwable oops) {
			res = createEditModelAndView(item, "item.commit.error");
		}
		return res;
	}
	
	//Undelete - Restaurar
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int itemID) {
		ModelAndView res;
		Item item;
		item = itemService.findOne(itemID);
		itemService.setUndelete(item);
		res = new ModelAndView("redirect:/item/administrator/list-itemsDeleted.do");

		return res;
	}


	// Ancillary methods-------------------------------------
	protected ModelAndView createEditModelAndView(Item item) {
		ModelAndView res;
		res = createEditModelAndView(item, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(Item item, String message) {
		ModelAndView res;
		Collection<Category> categories = new ArrayList<>();
		categories = categoryService.findAll();
			
		res = new ModelAndView("item/edit");
		res.addObject("item", item);
		res.addObject("message", message);
		res.addObject("categories", categories);
		return res;
	}

}
