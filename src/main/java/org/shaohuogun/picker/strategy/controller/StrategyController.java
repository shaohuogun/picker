package org.shaohuogun.picker.strategy.controller;

import org.shaohuogun.common.Controller;
import org.shaohuogun.common.Pagination;
import org.shaohuogun.common.Utility;
import org.shaohuogun.picker.strategy.model.Strategy;
import org.shaohuogun.picker.strategy.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value = "/api/strategy/{id}", method = RequestMethod.DELETE)
	public Strategy deleteStrategy(@PathVariable String id) throws Exception {
		return strategyService.deleteStrategy(id);
	}
	
	@RequestMapping(value = "/api/strategies", method = RequestMethod.GET)
	public Pagination getStrategies(@RequestParam(defaultValue = "1", required = false) int page) throws Exception {
		String creator = "a11039eb-4ba1-441a-bfdb-0d40f61a53dd";

		int total = strategyService.getStrategyCountOfCreator(creator);
		Pagination pagination = new Pagination();
		pagination.setTotal(total);
		pagination.setPageIndex(page);
		return strategyService.getStrategiesOfCreator(creator, pagination);
	}
	
}
