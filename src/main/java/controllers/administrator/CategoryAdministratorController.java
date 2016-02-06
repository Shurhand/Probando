package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Category;
import domain.Tax;
import services.AdminService;
import services.CategoryService;
import services.TaxService;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {
	// Services------------------------------------------
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AdminService administratorService;
	@Autowired
	private TaxService taxService;

	// Constructors----------------------------------------------
	public CategoryAdministratorController() {
		super();
	}

	// listing-----------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Category> categories;
		categories = categoryService.findAll();
		res = new ModelAndView("category/list");
		res.addObject("categories", categories);
		res.addObject("requestURI", "category/administrator/list.do");
		return res;
	}

	// Creation--------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Category category;
		Collection<Tax> tax = new ArrayList<>();
		tax = taxService.findAll();
		
		category = categoryService.create();
		res = new ModelAndView("category/edit");
		res.addObject("category", category);
		res.addObject("taxes",tax);
		return res;
	}

	// Edition--------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int categoryID) {
		ModelAndView res;
		Category category;
		category = categoryService.findOne(categoryID);
		Assert.notNull(category);
		res = createEditModelAndView(category);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Category category, BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = createEditModelAndView(category);
		} else try {
			categoryService.save(category);
			res = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			res = createEditModelAndView(category, "category.commit.error");
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Category category, BindingResult binding) {
		ModelAndView res;
		try {
			categoryService.delete(category);
			res = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			res = createEditModelAndView(category, "category.commit.error");
		}
		return res;
	}

	// Ancillary methods-------------------------------------
	protected ModelAndView createEditModelAndView(Category category) {
		ModelAndView res;
		res = createEditModelAndView(category, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(Category category, String message) {
		ModelAndView res;
		Collection<Tax> tax = new ArrayList<>();
		tax = taxService.findAll();

		res = new ModelAndView("category/edit");
		res.addObject("category", category);
		res.addObject("message", message);
		res.addObject("taxes",tax);
		return res;
	}

}
