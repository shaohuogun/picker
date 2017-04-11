package org.shaohuogun.picker.request.dao;

import org.apache.ibatis.session.SqlSession;
import org.shaohuogun.picker.request.model.Request;
import org.springframework.stereotype.Component;

@Component
public class RequestDao {

	private final SqlSession sqlSession;

	public RequestDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void insert(Request request) {
		sqlSession.insert("org.shaohuogun.picker.request.dao.RequestMapper.insert", request);
	}

	public Request selectById(String id) {
		return sqlSession.selectOne("org.shaohuogun.picker.request.dao.RequestMapper.selectById", id);
	}

	public Request selectByStatus(String status) {
		return sqlSession.selectOne("org.shaohuogun.picker.request.dao.RequestMapper.selectByStatus", status);
	}

	public void update(Request request) {
		sqlSession.update("org.shaohuogun.picker.request.dao.RequestMapper.update", request);
	}

}
