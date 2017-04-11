package org.shaohuogun.picker.result.controller;

import org.shaohuogun.common.Controller;
import org.shaohuogun.picker.result.model.Result;
import org.shaohuogun.picker.result.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultController extends Controller {
	
	@Autowired
	private ResultService resultService;
	
	@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
	public Result getResult(@PathVariable String id) throws Exception {		
		if ((id == null) || id.isEmpty()) {
			throw new Exception("Invalid argument.");
		}
		
		return resultService.getResult(id);
	}
	
}
