package org.shaohuogun.picker.strategy.service;

import java.util.Date;
import java.util.List;

import org.shaohuogun.picker.strategy.dao.StrategyDao;
import org.shaohuogun.picker.strategy.model.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StrategyService {

	@Autowired
	private StrategyDao strategyDao;

	@Transactional
	public Strategy createStrategy(Strategy strategy) throws Exception {
		if (strategy == null) {
			throw new NullPointerException("Strategy cann't be null.");
		}
		
		strategyDao.insert(strategy);
		return strategyDao.selectById(strategy.getId());
	}
	
	public Strategy getStrategy(String id) throws Exception {
		if ((id == null) || id.isEmpty()) {
			throw new IllegalArgumentException("Strategy's id cann't be null or empty.");
		}

		return strategyDao.selectById(id);
	}
	
	public Strategy getStrategyByName(String name) throws Exception {
		if ((name == null) || name.isEmpty()) {
			throw new IllegalArgumentException("Strategy's name cann't be null or empty.");
		}
		
		return strategyDao.selectByName(name);
	}

	public List<Strategy> getAllStrategies() {
		return strategyDao.selectAll();
	}

	public List<Strategy> getFreshStrategies(Date refreshTime) throws Exception {
		if (refreshTime == null) {
			throw new NullPointerException("Refresh time cann't be null.");
		}

		return strategyDao.selectFresh(refreshTime);
	}

}
