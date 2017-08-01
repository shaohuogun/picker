package org.shaohuogun.picker;

import java.util.List;

import org.shaohuogun.picker.strategy.model.Strategy;
import org.shaohuogun.picker.strategy.service.StrategyPool;
import org.shaohuogun.picker.strategy.service.StrategyService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class PickerStartup implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		StrategyService strategyService = (StrategyService) event.getApplicationContext().getBean("strategyService");
		List<Strategy> strategies = strategyService.getAllStrategies();
		StrategyPool.getInstance().refresh(strategies);
	}

}
