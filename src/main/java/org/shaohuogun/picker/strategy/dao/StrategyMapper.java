package org.shaohuogun.picker.strategy.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.shaohuogun.picker.strategy.model.Strategy;

@Mapper
public interface StrategyMapper {

	void insert(Strategy strategy);
	
	Strategy selectById(String id);
	
	Strategy selectByName(String name);
	
	List<Strategy> selectAll();
	
	List<Strategy> selectFresh(Date refreshTime);
	
}
