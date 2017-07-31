package org.shaohuogun.picker.processor.service;

import java.util.Date;

import org.json.JSONObject;
import org.shaohuogun.common.Utility;
import org.shaohuogun.picker.request.model.AsyncRequest;
import org.shaohuogun.picker.request.service.RequestService;
import org.shaohuogun.picker.result.model.Result;
import org.shaohuogun.picker.result.service.ResultService;
import org.shaohuogun.picker.strategy.model.Strategy;
import org.shaohuogun.picker.strategy.service.StrategyPool;
import org.shaohuogun.picker.strategy.service.StrategyService;
import org.shaohuogun.picker.strategy.tag.StrategyTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessorService {
	
	private static final String KEY_URL = "url";

	@Autowired
	private RequestService requestService;

	@Autowired
	private StrategyService strategyService;

	@Autowired
	private ResultService resultService;
	
	public void process(AsyncRequest req) throws Exception {
		if (req == null) {
			throw new NullPointerException("Request cann't be null.");
		}
		
		try {
			req.setStartTime(new Date());
			req.setStatus(AsyncRequest.STATUS_PROCESSING);
			requestService.modifyRequest(req);
			
			JSONObject jsonContent = new JSONObject(req.getContent());
			String targetUrl = jsonContent.getString(KEY_URL);
			if ((targetUrl == null) || targetUrl.isEmpty()) {
				throw new Exception("Target url is empty.");
			}
			
			String strategyId = StrategyPool.getInstance().getSuitableStrategyId(targetUrl);
			if ((strategyId == null) || strategyId.isEmpty()) {
				throw new Exception("Cann't find a suitable strategy.");				
			}
			
			Strategy strategy = strategyService.getStrategy(strategyId);
			StrategyTag strategyTag = StrategyTag.parse(strategy.getXml());
			JSONObject jsonResult = PickerUtility.pickPage(targetUrl, strategyTag);
			
			Result result = new Result();
			result.setId(Utility.getUUID());
			result.setCreator(req.getCreator());
			result.setRequestId(req.getId());
			result.setStrategyId(strategyId);
			result.setJson(jsonResult.toString());
			resultService.createResult(result);

			req.setResultId(result.getId());
			req.setStatus(AsyncRequest.STATUS_CLOSED);
			req.setEndTime(new Date());
			requestService.modifyRequest(req);
		} catch (Exception e) {
			req.setStatus(AsyncRequest.STATUS_ERROR);
			requestService.modifyRequest(req);		
			throw e;
		}
	}

}
