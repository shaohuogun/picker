package org.shaohuogun.picker.strategy.dao;

import org.apache.ibatis.annotations.Mapper;
import org.shaohuogun.picker.strategy.model.Strategy;

@Mapper
public interface StrategyMapper {

	void insert(Strategy strategy);
	
	Strategy selectById(String id);
	
}
