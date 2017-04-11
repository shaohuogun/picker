package org.shaohuogun.picker.result.dao;

import org.apache.ibatis.session.SqlSession;
import org.shaohuogun.picker.result.model.Result;
import org.springframework.stereotype.Component;

@Component
public class ResultDao {

	private final SqlSession sqlSession;

	public ResultDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void insert(Result result) {
		sqlSession.insert("org.shaohuogun.picker.result.dao.ResultMapper.insert", result);
	}

	public Result selectById(String id) {
		return sqlSession.selectOne("org.shaohuogun.picker.result.dao.ResultMapper.selectById", id);
	}

	public Result selectBySent(Character sent) {
		return sqlSession.selectOne("org.shaohuogun.picker.result.dao.ResultMapper.selectBySent", sent);
	}
	
	public void update(Result result) {
		sqlSession.update("org.shaohuogun.picker.result.dao.ResultMapper.update", result);
	}
}
