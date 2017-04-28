package org.shaohuogun.picker.strategy.service;

import java.util.Date;
import java.util.List;

import org.shaohuogun.common.Model;
import org.shaohuogun.common.Pagination;
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
	
	public Strategy deleteStrategy(String id) throws Exception {
		if ((id == null) || id.isEmpty()) {
			throw new IllegalArgumentException("Strategy's id cann't be null or empty.");
		}
		
		Strategy strategy = strategyDao.selectById(id);
		strategy.setDeleted(Model.DELETED_YES);
		strategyDao.update(strategy);
		return strategy;
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
	
	public int getStrategyCountByCreator(String creator) throws Exception {
		if ((creator == null) || creator.isEmpty()) {
			throw new IllegalArgumentException("Creator cann't be null or empty.");
		}
		
		return strategyDao.countByCreator(creator);
	}
	
	public Pagination getStrategiesByCreator(String creator, Pagination pagination) throws Exception {
		if ((creator == null) || creator.isEmpty()) {
			throw new IllegalArgumentException("Creator cann't be null or empty.");
		}
		
		if (pagination == null) {
			throw new NullPointerException("Pagination cann't be null.");
		}
		
		int offset = (pagination.getPageIndex() - 1) * pagination.getPageSize();
		int limit = pagination.getPageSize();
		List<Model> strategies = strategyDao.selectByCreator(creator, offset, limit);
		pagination.setObjects(strategies);
		return pagination;
	}

}
