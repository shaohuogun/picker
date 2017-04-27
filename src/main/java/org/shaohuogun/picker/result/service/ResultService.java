package org.shaohuogun.picker.result.service;

import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.shaohuogun.common.Utility;
import org.shaohuogun.picker.request.model.Request;
import org.shaohuogun.picker.request.service.RequestService;
import org.shaohuogun.picker.result.dao.ResultDao;
import org.shaohuogun.picker.result.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResultService {
	
	@Value("${reader.service.url}")
	private String readerSeriveUrl;
	
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
		
		JSONObject jsonResult = new JSONObject(result.getJson());
		Request request = requestService.getRequest(result.getRequestId());
		jsonResult.put(Request.KEY_TARGET_URL, request.getTargetUrl());
		jsonResult.put(Request.KEY_TARGET_TYPE, request.getTargetType());
		jsonResult.put(Request.KEY_BATCH_NO, request.getBatchNo());

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(readerSeriveUrl);
		StringEntity params = new StringEntity(jsonResult.toString(), Utility.ENCODE_UTF8);
		httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
		httpPost.addHeader("Accept", "application/json");
		httpPost.setEntity(params);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		StatusLine statusLine = httpResponse.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new Exception("Fail to send result back to the client.");
		}
		
		result.setLastModifyDate(new Date());
		result.setSent(Result.SENT_YES);
		resultDao.update(result);
	}

}
