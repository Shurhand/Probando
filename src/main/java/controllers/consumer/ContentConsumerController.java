package controllers.consumer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Content;

import services.ContentService;

@Controller
@RequestMapping("/content/consumer")
public class ContentConsumerController {

	// -------------services-----------------------------
	@Autowired
	private ContentService contentService;

	// -----------constructors--------------------------
	public ContentConsumerController() {
		super();
	}

	// --------------Edition ----------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int contentID) {
		ModelAndView res;
		Content content;
		content = contentService.findOne(contentID);
		Assert.notNull(content);
		res = createEditModelAndView(content);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Content content, BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = createEditModelAndView(content);
		} else {
			try {
				contentService.save(content);
				res = new ModelAndView(
						"redirect:/shoppingCart/consumer/list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(content, "content.commit.error");
			}
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Content content, BindingResult binding) {
		ModelAndView res;

		try {
			contentService.delete(content);
			res = new ModelAndView("redirect:/shoppingCart/consumer/list.do");
		} catch (Throwable oops) {
			res = createEditModelAndView(content, "content.commit.error");
		}

		return res;
	}

	// ---------------Ancillary Methods---------------------
	public ModelAndView createEditModelAndView(Content content) {
		ModelAndView res;

		res = createEditModelAndView(content, null);

		return res;
	}

	public ModelAndView createEditModelAndView(Content content, String message) {
		ModelAndView res;

		res = new ModelAndView("content/edit");
		res.addObject("content", content);
		res.addObject("message", message);

		return res;
	}
}
