package org.shaohuogun.picker.request.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.shaohuogun.common.Controller;
import org.shaohuogun.common.Pagination;
import org.shaohuogun.common.Utility;
import org.shaohuogun.picker.request.model.Request;
import org.shaohuogun.picker.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController extends Controller {
	
	@Autowired
	private RequestService requestService;
	
	@RequestMapping(value = "/api/request", method = RequestMethod.POST)
	public String createRequest(HttpServletRequest req) throws Exception {
		req.setCharacterEncoding(Utility.ENCODE_UTF8);
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
		
		JSONObject jsonReq = new JSONObject(sb.toString());
		String actionType = jsonReq.getString(Request.KEY_ACTION_TYPE);
		String content = jsonReq.getString(Request.KEY_CONTENT);
		String hookUrl = jsonReq.getString(Request.KEY_HOOK_URL);
		if ((actionType == null) || actionType.isEmpty()) {
			throw new IllegalArgumentException("Action type cann't be null or empty.");
		}

		if ((content == null) || content.isEmpty()) {
			throw new IllegalArgumentException("Content cann't be null or empty.");
		}

		if ((hookUrl == null) || hookUrl.isEmpty()) {
			throw new IllegalArgumentException("Hook url cann't be null or empty.");
		}
		
		String uuid = Utility.getUUID();
		Request asyncReq = new Request();
		asyncReq.setId(uuid);
		asyncReq.setCreator("41f98331-11b4-4b70-8ab3-b2b3332324b5");
		asyncReq.setActionType(actionType);
		asyncReq.setSerialNumber(uuid);
		asyncReq.setContent(content);
		asyncReq.setHookUrl(hookUrl);
		requestService.createRequest(asyncReq);
		return asyncReq.getSerialNumber();
	}
	
	@RequestMapping(value = "/api/request/{id}", method = RequestMethod.GET)
	public Request getRequest(@PathVariable String id) throws Exception {
		return requestService.getRequest(id);
	}
	
	@RequestMapping(value = "/api/requests", method = RequestMethod.GET)
	public Pagination getRequests(@RequestParam(defaultValue = "1", required = false) int page) throws Exception {
		String creator = "41f98331-11b4-4b70-8ab3-b2b3332324b5";

		int total = requestService.getRequestCountOfCreator(creator);
		Pagination pagination = new Pagination();
		pagination.setTotal(total);
		pagination.setPageIndex(page);
		return requestService.getRequestsOfCreator(creator, pagination);
	}	

	@RequestMapping(value = "/api/request/{id}/redo", method = RequestMethod.GET)
	public Request redoRequest(@PathVariable String id) throws Exception {
		Request req = requestService.getRequest(id);
		if (req == null) {
			throw new Exception("Invalid argument.");
		}

		req.setLastModifier("a11039eb-4ba1-441a-bfdb-0d40f61a53dd");
		req.setLastModifyDate(new Date());
		req.setStatus(Request.STATUS_INITIAL);
		return requestService.modifyRequest(req);
	}

}
