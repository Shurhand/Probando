package controllers.consumer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.CashOrder;
import domain.Consumer;
import domain.CreditCard;
import domain.ShoppingCart;
import services.ConsumerService;

@Controller
@RequestMapping("/consumer")
public class RegisterConsumerController extends AbstractController{
	
	//===========  Services  ===========
	
	@Autowired
	private ConsumerService consumerService;
	
	//===========  Constructor  ===========
	
	public RegisterConsumerController(){
		super();
	}
	
	//===========  Creation  ===========
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView res;
		Consumer consumer;
		consumer = consumerService.create();
		Assert.notNull(consumer);
		res = createEditModelAndView(consumer);
		return res;
	}
	//=========== Salvar en la base de datos ===========
	
	@RequestMapping(value = "/create" , method = RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Consumer consumer, BindingResult binding){
		ModelAndView res;
		
		
		if (binding.hasErrors()) {
			for(ObjectError a : binding.getAllErrors()){
				System.out.println(a);
			}
			res = createEditModelAndView(consumer);
		}
		else {
			try {
				consumerService.save(consumer);
				res = new ModelAndView("redirect:/security/login.do");
			} catch (Throwable oops){
				res = new ModelAndView("redirect:/security/login.do");
			}
		}
		
	return res;
	}
	@RequestMapping(value = "/modifyProfile" , method = RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView res;
		Consumer consumer;
		
		consumer = consumerService.findPrincipal();
		Assert.notNull(consumer);
	
		res = new ModelAndView("consumer/modifyProfile");
		res.addObject("consumer", consumer);
		res.addObject("message", null);
			
		return res;
	
	}
	
	@RequestMapping(value = "/modifyProfile" , method = RequestMethod.POST, params = "save")
	public ModelAndView modifyProfile(@Valid Consumer consumer, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView2(consumer,null);
		}else{
			try{
				consumerService.modifyProfile(consumer);
				result = new ModelAndView("redirect:/"); 
			} catch (Throwable oops){
				result = createEditModelAndView2(consumer, "consumer.commit.error");
			}
		}
		return result;
	}

			
	//===========  Ancillary Methods  ===========
	protected ModelAndView createEditModelAndView(Consumer consumer){
		ModelAndView res;
		
		res = createEditModelAndView(consumer, null);
		
		return res;
	}
	
	protected ModelAndView createEditModelAndView(Consumer consumer, String message){
		ModelAndView res;
		CreditCard creditCard;
		CashOrder cashOrder;
		ShoppingCart shoppingCart;
		
		creditCard = new CreditCard();
		cashOrder = new CashOrder();
		shoppingCart = new ShoppingCart();
		
		res = new ModelAndView("consumer/create");
		res.addObject("consumer", consumer);
		res.addObject("message", message);
		res.addObject("creditCard", creditCard);
		res.addObject("cashOrder", cashOrder);
		res.addObject("shoppingCart", shoppingCart);
		
		return res;
	}
	
	protected ModelAndView createEditModelAndView2(Consumer consumer, String message){
		ModelAndView res;
		
		res = new ModelAndView("consumer/modifyProfile");
		res.addObject("consumer", consumer);
		res.addObject("message", message);
		
		return res;
	}
	
	

}
