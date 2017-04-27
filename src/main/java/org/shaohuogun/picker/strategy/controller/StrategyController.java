package org.shaohuogun.picker.strategy.controller;

import java.util.List;

import org.shaohuogun.common.Controller;
import org.shaohuogun.common.Utility;
import org.shaohuogun.picker.strategy.model.Strategy;
import org.shaohuogun.picker.strategy.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StrategyController extends Controller {
	
	@Autowired
	private StrategyService strategyService;
	
	@RequestMapping(value = "/api/strategy", method = RequestMethod.POST)
	public Strategy createStrategy(@RequestBody Strategy strategy) throws Exception {
		strategy.setId(Utility.getUUID());
		strategy.setCreator("a11039eb-4ba1-441a-bfdb-0d40f61a53dd");
		return strategyService.createStrategy(strategy);
	}
	
	@RequestMapping(value = "/api/strategy/{id}", method = RequestMethod.GET)
	public Strategy getStrategy(@PathVariable String id) throws Exception {
		return strategyService.getStrategy(id);
	}
	
	@RequestMapping(value = "/api/strategy", method = RequestMethod.GET)
	public List<Strategy> getAllStrategies() {
		return strategyService.getAllStrategies();
	}
	
}
