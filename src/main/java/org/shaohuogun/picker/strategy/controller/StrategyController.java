package org.shaohuogun.picker.strategy.controller;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.shaohuogun.common.Controller;
import org.shaohuogun.common.Utility;
import org.shaohuogun.picker.strategy.model.Strategy;
import org.shaohuogun.picker.strategy.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StrategyController extends Controller {
	
	@Autowired
	private StrategyService strategyService;

	@RequestMapping(value = "/api/strategy", method = RequestMethod.GET)
	public Strategy createStrategy(HttpServletRequest req, @RequestParam(required = true) String file) throws Exception {		
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("strategy/"+ file + ".xml");
		String xml = Utility.readFile(url.getPath());
		
		Strategy strategy = new Strategy();
		strategy.setId(Utility.getUUID());
		strategy.setCreator("a11039eb-4ba1-441a-bfdb-0d40f61a53dd");
		
		strategy.setName(file);
		strategy.setXml(xml);
		return strategyService.createStrategy(strategy);
	}
	
	@RequestMapping(value = "/api/strategy/{id}", method = RequestMethod.GET)
	public Strategy getStrategy(@PathVariable String id) throws Exception {		
		if ((id == null) || id.isEmpty()) {
			throw new Exception("Invalid argument.");
		}
		
		return strategyService.getStrategy(id);
	}
	
}
