package org.shaohuogun.picker.request.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.shaohuogun.common.Controller;
import org.shaohuogun.common.Utility;
import org.shaohuogun.picker.request.model.Request;
import org.shaohuogun.picker.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController extends Controller {
	
	@Autowired
	private RequestService requestService;
	
	@RequestMapping(value = "/api/request", method = RequestMethod.POST)
	public void createRequest(HttpServletRequest req) throws Exception {
		req.setCharacterEncoding(Utility.UTF8);
		StringBuffer sb = new StringBuffer();
		InputStream is = req.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		
		String json = sb.toString();
		if ((json == null) || json.isEmpty()) {
			StringBuffer sb1 = new StringBuffer();
			sb1.append(req.getRemoteAddr());
			sb1.append("[");
			sb1.append(req.getRemoteHost());
			sb1.append(":");
			sb1.append(req.getRemotePort());
			sb1.append("]");
			throw new Exception("The picking request from [" + sb1.toString() + "]  is invalid.");
		}
		
		JSONObject jsonRequest = new JSONObject(sb.toString());
		Request request = new Request();
		request.setId(Utility.getUUID());
		request.setCreator("41f98331-11b4-4b70-8ab3-b2b3332324b5");
		String targetUrl = jsonRequest.getString(Request.KEY_TARGET_URL);
		String targetType = jsonRequest.getString(Request.KEY_TARGET_TYPE);
		String batchNo = jsonRequest.getString(Request.KEY_BATCH_NO);
		if ((targetUrl == null) || targetUrl.isEmpty() || (targetType == null) || targetType.isEmpty()
				|| (batchNo == null) || batchNo.isEmpty()) {
			throw new Exception("Invalid request.");
		}
		
		request.setTargetUrl(targetUrl);
		request.setTargetType(targetType);
		request.setBatchNo(batchNo);	
		requestService.createRequest(request);
	}
	
	@RequestMapping(value = "/api/request/{id}", method = RequestMethod.GET)
	public Request getRequest(@PathVariable String id) throws Exception {
		if ((id == null) || id.isEmpty()) {
			throw new Exception("Invalid argument.");
		}

		return requestService.getRequest(id);
	}

	@RequestMapping(value = "/api/request/{id}/redo", method = RequestMethod.GET)
	public Request redo(HttpServletRequest req, @PathVariable String id) throws Exception {
		if ((id == null) || id.isEmpty()) {
			throw new Exception("Invalid argument.");
		}

		Request request = requestService.getRequest(id);
		if (request == null) {
			throw new Exception("Invalid argument.");
		}

		request.setLastModifier("a11039eb-4ba1-441a-bfdb-0d40f61a53dd");
		request.setLastModifyDate(new Date());
		request.setStatus(Request.STATUS_INITIAL);
		return requestService.modifyRequest(request);
	}

}
