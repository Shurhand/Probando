package controllers;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import services.CategoryService;
import services.ItemService;

import controllers.AbstractController;
import domain.Category;
import domain.Item;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {
	// Services------------------------------------------
	@Autowired
	private ItemService itemService;
	@Autowired
	private AdminService administratorService;
	@Autowired
	private CategoryService categoryService;

	// Constructors----------------------------------------------
	public ItemController() {
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
	
	// Items by keyword
	@RequestMapping(value = "/listsearch", method = RequestMethod.POST, params = "search")
	public ModelAndView search(String keyword) {
		ModelAndView result;

		result = listingItemsByKeyword(keyword);
	
		return result;
}

	@RequestMapping(value = "/listsearch", method = RequestMethod.GET)
	public ModelAndView listByKeyword(@RequestParam String keyword) {
		ModelAndView result;

		result = listingItemsByKeyword(keyword);
	
		return result;
}

//Ancillary Methods ----------------------------------------------
	public ModelAndView listingItemsByKeyword(String keyword){
	ModelAndView result;
	Collection<Item> items;
	
		items = Collections.emptyList();
	
		if(keyword == ""){
			items = itemService.findItemsNotDeleted();
		}else{
			items = itemService.findItemsContainKeyword(keyword);
		}			
	
		result = new ModelAndView("item/listsearch");
		result.addObject("items", items);
		result.addObject("requestedURI", "item/listsearch.do");
		
		return result;
	}
}