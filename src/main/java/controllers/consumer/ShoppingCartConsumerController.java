package controllers.consumer;

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
import domain.Consumer;
import domain.Content;
import domain.ShoppingCart;
import services.ConsumerService;
import services.ShoppingCartService;

@Controller
@RequestMapping("/shoppingCart/consumer")
public class ShoppingCartConsumerController extends AbstractController {
	// Services------------------------------------------
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ConsumerService consumerService;

	// Constructors----------------------------------------------
	public ShoppingCartConsumerController() {
		super();
	}

	// listing-----------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		ShoppingCart shoppingCart;
		Consumer consumer;
		Collection<Content> contents;
		Collection<String> comments = new ArrayList<>();
		Double total = 0.0;
		shoppingCart = null;
		consumer = consumerService.findPrincipal();
		if(consumer!=null){
			shoppingCart = shoppingCartService.getShoppingCartConsumer(consumer);
		}
		
		contents = shoppingCart.getContents();
		comments = shoppingCart.getComments();
		total = calculaTotal(contents);
		res = new ModelAndView("shoppingCart/list");
		res.addObject("shoppingCart", shoppingCart);
		res.addObject("comments", comments);
		res.addObject("contentsShoppingCart",contents);
		res.addObject("total", total);
		res.addObject("requestURI", "shoppingCart/consumer/list.do");
		return res;
	}
	private Double calculaTotal(Collection<Content> contents){
		Double price;
		int quantity;
		Double res = 0.0;
		for (Content c: contents){
			if(c != null){
			
			price = c.getItem().getPrice();
			quantity = c.getQuantity();
			res += (price*quantity);
		}
		}
		return res;
	}



	// Edition--------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int shoppingCartID) {
		ModelAndView res;
		ShoppingCart shoppingCart;
		shoppingCart = shoppingCartService.findOne(shoppingCartID);
		Assert.notNull(shoppingCart);
		res = createEditModelAndView(shoppingCart);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ShoppingCart shoppingCart, BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = createEditModelAndView(shoppingCart);
		} else {
			try {
				shoppingCartService.save(shoppingCart);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops){
				res = new ModelAndView("shoppingCart/edit");
			}
		}
		return res;
	}
	
	@RequestMapping(value="/placeOrder", method = RequestMethod.GET)
	public ModelAndView checkOut(Integer shoppingCartID){
		ModelAndView result;
		ShoppingCart shoppingCart;
		Consumer consumer;
		
		
		consumer = consumerService.findPrincipal();
		
		try {
			
			shoppingCart = shoppingCartService.findOne(shoppingCartID);
			consumerService.validateCreditCard(consumer);
			consumerService.placeOrderFromShoppingCart(shoppingCart);
			result = new ModelAndView("redirect:/order/consumer/list.do");
		} catch (Throwable oops) {
			try{
				consumerService.validateCreditCard(consumer);
				result = list();
			}catch (Throwable ooops){
				result = new ModelAndView("redirect:/consumer/shoppingCart/list.do");
			}	
		}
		
		
		return result;
		
	}


	// Ancillary methods-------------------------------------
	protected ModelAndView createEditModelAndView(ShoppingCart shoppingCart) {
		ModelAndView res;
		res = createEditModelAndView(shoppingCart, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(ShoppingCart shoppingCart, 
			String message) {
		ModelAndView res;
		Collection<String> comments = shoppingCart.getComments();
		Collection<Content> contents = shoppingCart.getContents();
		Consumer consumer = consumerService.findPrincipal();
		shoppingCart.setConsumer(consumer);
		
		res = new ModelAndView("shoppingCart/edit");
		res.addObject("shoppingCart", shoppingCart);
		res.addObject("consumer", consumer);
		res.addObject("comments", comments);
		res.addObject("contents",contents);
		res.addObject("message", message);

		return res;
	}

}