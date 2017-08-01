package org.shaohuogun.picker.result.service;

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
import org.shaohuogun.common.Model;
import org.shaohuogun.common.Pagination;
import org.shaohuogun.common.Utility;
import org.shaohuogun.picker.request.model.AsyncRequest;
import org.shaohuogun.picker.request.service.RequestService;
import org.shaohuogun.picker.result.dao.ResultDao;
import org.shaohuogun.picker.result.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResultService {
	
	@Autowired
	private RequestService requestService;

	@Autowired
	private ResultDao resultDao;

	@Transactional
	public Result createResult(Result result) throws Exception {
		if (result == null) {
			throw new NullPointerException("Result cann't be null.");
		}

		resultDao.insert(result);
		return resultDao.selectById(result.getId());
	}
	
	public int getResultCountOfRequest(String requestId) throws Exception {
		if ((requestId == null) || requestId.isEmpty()) {
			throw new IllegalArgumentException("Channel's id cann't be null or empty.");
		}
		
		return resultDao.countByRequestId(requestId);
	}

	public Pagination getResultsOfRequest(String requestId, Pagination pagination) throws Exception {
		if ((requestId == null) || requestId.isEmpty()) {
			throw new IllegalArgumentException("Channel's id cann't be null or empty.");
		}
		
		if (pagination == null) {
			throw new NullPointerException("Pagination cann't be null.");
		}

		int offset = (pagination.getPageIndex() - 1) * pagination.getPageSize();
		int limit = pagination.getPageSize();
		List<Model> results = resultDao.selectByRequestId(requestId, offset, limit);
		pagination.setObjects(results);
		return pagination;
	}
	
	public Result getResult(String id) throws Exception {
		if ((id == null) || id.isEmpty()) {
			throw new IllegalArgumentException("Result's id cann't be null or empty.");
		}

		return resultDao.selectById(id);
	}
	
	public Result getUnsentResult() {
		return resultDao.selectBySent(Result.SENT_NOT);
	}
	
	@Transactional
	public Result modifyResult(Result result) throws Exception {
		if (result == null) {
			throw new NullPointerException("Result cann't be null.");
		}
		
		resultDao.update(result);
		return resultDao.selectById(result.getId());
	}

	public void send(Result result) throws Exception {
		if (result == null) {
			throw new NullPointerException("Result cann't be null.");
		}
		
		JSONObject jsonResp = new JSONObject();
		AsyncRequest asyncReq = requestService.getRequest(result.getRequestId());
		jsonResp.put(AsyncRequest.KEY_SERIAL_NUMBER, asyncReq.getSerialNumber());
		jsonResp.put(AsyncRequest.KEY_CONTENT, result.getJson());

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(asyncReq.getHookUrl());
		StringEntity params = new StringEntity(jsonResp.toString(), Utility.ENCODE_UTF8);
		httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
		httpPost.addHeader("Accept", "application/json");
		httpPost.setEntity(params);
		HttpResponse httpResp = httpClient.execute(httpPost);
		
		StatusLine statusLine = httpResp.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new Exception("Fail to send result back to the client, status code: " + statusCode);
		}
		
		result.setLastModifyDate(new Date());
		result.setSent(Result.SENT_YES);
		resultDao.update(result);
	}

}
