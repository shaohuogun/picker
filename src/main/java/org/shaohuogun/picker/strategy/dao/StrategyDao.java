package org.shaohuogun.picker.strategy.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.shaohuogun.picker.strategy.model.Strategy;
import org.springframework.stereotype.Component;

@Component
public class StrategyDao {

	private final SqlSession sqlSession;

	public StrategyDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void insert(Strategy strategy) {
		sqlSession.insert("org.shaohuogun.picker.strategy.dao.StrategyMapper.insert", strategy);
	}

	public Strategy selectById(String id) {
		return sqlSession.selectOne("org.shaohuogun.picker.strategy.dao.StrategyMapper.selectById", id);
	}

	public Strategy selectByName(String name) {
		return sqlSession.selectOne("org.shaohuogun.picker.strategy.dao.StrategyMapper.selectByName", name);
	}

	public List<Strategy> selectAll() {
		return sqlSession.selectList("org.shaohuogun.picker.strategy.dao.StrategyMapper.selectAll");
	}

	public List<Strategy> selectFresh(Date refreshTime) {
		return sqlSession.selectList("org.shaohuogun.picker.strategy.dao.StrategyMapper.selectFresh", refreshTime);
	}

}
