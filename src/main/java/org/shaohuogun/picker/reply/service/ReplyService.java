package org.shaohuogun.picker.reply.service;

import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.shaohuogun.common.Entity;
import org.shaohuogun.common.Pagination;
import org.shaohuogun.common.Utility;
import org.shaohuogun.picker.request.model.Request;
import org.shaohuogun.picker.request.service.RequestService;
import org.shaohuogun.picker.reply.dao.ReplyDao;
import org.shaohuogun.picker.reply.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyService {
	
	@Autowired
	private RequestService requestService;

	@Autowired
	private ReplyDao replyDao;

	@Transactional
	public Reply createReply(Reply reply) throws Exception {
		if (reply == null) {
			throw new NullPointerException("Reply cann't be null.");
		}

		replyDao.insert(reply);
		return replyDao.selectById(reply.getId());
	}
	
	public int getReplyCountOfRequest(String requestId) throws Exception {
		if ((requestId == null) || requestId.isEmpty()) {
			throw new IllegalArgumentException("Channel's id cann't be null or empty.");
		}
		
		return replyDao.countByRequestId(requestId);
	}

	public Pagination getRepliesOfRequest(String requestId, Pagination pagination) throws Exception {
		if ((requestId == null) || requestId.isEmpty()) {
			throw new IllegalArgumentException("Channel's id cann't be null or empty.");
		}
		
		if (pagination == null) {
			throw new NullPointerException("Pagination cann't be null.");
		}

		int offset = (pagination.getPageIndex() - 1) * pagination.getPageSize();
		int limit = pagination.getPageSize();
		List<Entity> replies = replyDao.selectByRequestId(requestId, offset, limit);
		pagination.setObjects(replies);
		return pagination;
	}
	
	public Reply getReply(String id) throws Exception {
		if ((id == null) || id.isEmpty()) {
			throw new IllegalArgumentException("Reply's id cann't be null or empty.");
		}

		return replyDao.selectById(id);
	}
	
	public Reply getUnsentReply() {
		return replyDao.selectBySent(Reply.SENT_NOT);
	}
	
	@Transactional
	public Reply modifyReply(Reply reply) throws Exception {
		if (reply == null) {
			throw new NullPointerException("Reply cann't be null.");
		}
		
		replyDao.update(reply);
		return replyDao.selectById(reply.getId());
	}

	public void send(Reply reply) throws Exception {
		if (reply == null) {
			throw new NullPointerException("Reply cann't be null.");
		}
		
		JSONObject jsonResp = new JSONObject();
		Request req = requestService.getRequest(reply.getRequestId());
		jsonResp.put(Request.KEY_SERIAL_NUMBER, req.getSerialNumber());
		jsonResp.put(Request.KEY_CONTENT, reply.getContent());

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(req.getHookUrl());
		StringEntity params = new StringEntity(jsonResp.toString(), Utility.ENCODE_UTF8);
		httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
		httpPost.addHeader("Accept", "application/json");
		httpPost.setEntity(params);
		HttpResponse httpResp = httpClient.execute(httpPost);
		
		StatusLine statusLine = httpResp.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new Exception("Fail to reply to the client, status code: " + statusCode);
		}
		
		reply.setLastModifyDate(new Date());
		reply.setSent(Reply.SENT_YES);
		replyDao.update(reply);
	}

}
