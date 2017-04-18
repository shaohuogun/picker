package org.shaohuogun.picker.strategy.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shaohuogun.picker.strategy.model.Strategy;

public class StrategyPool {
	
	private static StrategyPool instance = new StrategyPool();

	private Date refreshTime;

	private Map<String, Strategy> strategyMap = new HashMap<String, Strategy>();

	private StrategyPool() {
		super();
	}

	public static StrategyPool getInstance() {
		return instance;
	}
	
	public synchronized void refresh(List<Strategy> strategies) {
		refreshTime = new Date();
		
		if ((strategies != null) && !strategies.isEmpty()) {
			for (Strategy curStrategy : strategies) {
				strategyMap.put(curStrategy.getUrlRegex(), curStrategy);
			}
		}
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public String getSuitableStrategyId(String targetUrl) throws Exception {
		if (targetUrl == null) {
			throw new Exception("Invalid argument.");
		}

		for (String curKey : strategyMap.keySet()) {
			Strategy curStrategy = strategyMap.get(curKey);
			Pattern pattern = Pattern.compile(curStrategy.getUrlRegex());
			Matcher matcher = pattern.matcher(targetUrl);
			if (matcher.find()) {
				return curStrategy.getId();
			}
		}
		
		return null;
	}

}
