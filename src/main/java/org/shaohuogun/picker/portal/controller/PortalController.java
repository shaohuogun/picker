package org.shaohuogun.picker.portal.controller;

import org.shaohuogun.common.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PortalController extends Controller {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView portal() {
		return new ModelAndView("portal");
	}
	
	@RequestMapping(value = "/strategy", method = RequestMethod.GET)
	public ModelAndView strategy() {
		return new ModelAndView("strategy");
	}
	
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public ModelAndView request() {
		return new ModelAndView("request");
	}
	
	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public ModelAndView result() {
		return new ModelAndView("result");
	}
	
}
