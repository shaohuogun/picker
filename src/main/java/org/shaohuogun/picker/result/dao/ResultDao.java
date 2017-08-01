package org.shaohuogun.picker.result.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.shaohuogun.common.Model;
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

	public int countByRequestId(String requestId) {
		return sqlSession.selectOne("org.shaohuogun.picker.result.dao.ResultMapper.countByRequestId", requestId);
	}

	public List<Model> selectByRequestId(String requestId, int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return sqlSession.selectList("org.shaohuogun.picker.result.dao.ResultMapper.selectByRequestId", requestId,
				rowBounds);
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
