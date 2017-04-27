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
		return new ModelAndView("picker");
	}
	
}
