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

import services.AdminService;
import services.CategoryService;
import services.TaxService;

import controllers.AbstractController;
import domain.CashOrder;
import domain.Category;
import domain.OrderedItem;
import domain.Tax;

@Controller
@RequestMapping("/tax/administrator")
public class TaxAdministratorController extends AbstractController {
	// Services------------------------------------------
	@Autowired
	private TaxService taxService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AdminService adminService;

	// Constructors----------------------------------------------
	public TaxAdministratorController() {
		super();
	}

	// listing-----------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Tax> taxs;
		taxs = taxService.findAll();
		res = new ModelAndView("tax/list");
		res.addObject("taxes", taxs);
		res.addObject("requestURI", "tax/administrator/list.do");
		return res;
	}

	// Creation--------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Tax tax;
		Collection<Category> categories = new ArrayList<>();
		categories = categoryService.findAll();
		tax = taxService.create();
		res = new ModelAndView("tax/edit");
		res.addObject("tax", tax);
		res.addObject("categories", categories);
		return res;
	}

	// Edition--------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int taxID) {
		ModelAndView res;
		Tax tax;
		tax = taxService.findOne(taxID);
		Assert.notNull(tax);
		res = createEditModelAndView(tax);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Tax tax, BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = createEditModelAndView(tax);
		} else {
			try {
				taxService.save(tax);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(tax, "tax.commit.error");
			}
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Tax tax, BindingResult binding) {
		ModelAndView res;
		try {
			taxService.delete(tax);
			res = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			res = createEditModelAndView(tax, "tax.commit.error");
		}
		return res;
	}
	
	@RequestMapping(value = "/listcategories", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int taxID) {
		ModelAndView res;
		Tax tax;
		Collection<Category> categories = new ArrayList<>();
		tax = taxService.findOne(taxID);
		categories = categoryService.findCategoriesByTax(tax);
		
		res = new ModelAndView("category/list");
		res.addObject("categories", categories);
		res.addObject("requestURI", "tax/listcategories.do");
		return res;
	}

	// Ancillary methods-------------------------------------
	protected ModelAndView createEditModelAndView(Tax tax) {
		ModelAndView res;
		res = createEditModelAndView(tax, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(Tax tax, String message) {
		ModelAndView res;
		Collection<Category> categories = new ArrayList<>();
		categories = categoryService.findAll();

		res = new ModelAndView("tax/edit");
		res.addObject("tax", tax);
		res.addObject("message", message);
		res.addObject("categories", categories);

		return res;
	}

}
