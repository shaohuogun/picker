package org.shaohuogun.picker.reply.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.shaohuogun.common.Entity;
import org.shaohuogun.picker.reply.model.Reply;
import org.springframework.stereotype.Component;

@Component
public class ReplyDao {

	private final SqlSession sqlSession;

	public ReplyDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void insert(Reply reply) {
		sqlSession.insert("org.shaohuogun.picker.reply.dao.ReplyMapper.insert", reply);
	}

	public int countByRequestId(String requestId) {
		return sqlSession.selectOne("org.shaohuogun.picker.reply.dao.ReplyMapper.countByRequestId", requestId);
	}

	public List<Entity> selectByRequestId(String requestId, int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return sqlSession.selectList("org.shaohuogun.picker.reply.dao.ReplyMapper.selectByRequestId", requestId,
				rowBounds);
	}

	public Reply selectById(String id) {
		return sqlSession.selectOne("org.shaohuogun.picker.reply.dao.ReplyMapper.selectById", id);
	}

	public Reply selectBySent(Character sent) {
		return sqlSession.selectOne("org.shaohuogun.picker.reply.dao.ReplyMapper.selectBySent", sent);
	}

	public void update(Reply reply) {
		sqlSession.update("org.shaohuogun.picker.reply.dao.ReplyMapper.update", reply);
	}
}
