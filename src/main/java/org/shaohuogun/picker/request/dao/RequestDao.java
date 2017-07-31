package org.shaohuogun.picker.request.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.shaohuogun.common.Model;
import org.shaohuogun.picker.request.model.AsyncRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestDao {

	private final SqlSession sqlSession;

	public RequestDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void insert(AsyncRequest request) {
		sqlSession.insert("org.shaohuogun.picker.request.dao.RequestMapper.insert", request);
	}

	public AsyncRequest selectById(String id) {
		return sqlSession.selectOne("org.shaohuogun.picker.request.dao.RequestMapper.selectById", id);
	}

	public AsyncRequest selectByStatus(String status) {
		return sqlSession.selectOne("org.shaohuogun.picker.request.dao.RequestMapper.selectByStatus", status);
	}

	public void update(AsyncRequest request) {
		sqlSession.update("org.shaohuogun.picker.request.dao.RequestMapper.update", request);
	}
	
	public int countByCreator(String creator) {
		return sqlSession.selectOne("org.shaohuogun.picker.request.dao.RequestMapper.countByCreator", creator);
	}

	public List<Model> selectByCreator(String creator, int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return sqlSession.selectList("org.shaohuogun.picker.request.dao.RequestMapper.selectByCreator", creator,
				rowBounds);
	}

}
