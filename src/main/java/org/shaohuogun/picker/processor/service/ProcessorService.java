package org.shaohuogun.picker.processor.service;

import java.util.Date;

import org.json.JSONObject;
import org.shaohuogun.common.Utility;
import org.shaohuogun.picker.request.model.Request;
import org.shaohuogun.picker.request.service.RequestService;
import org.shaohuogun.picker.result.model.Result;
import org.shaohuogun.picker.result.service.ResultService;
import org.shaohuogun.picker.strategy.model.Strategy;
import org.shaohuogun.picker.strategy.service.StrategyService;
import org.shaohuogun.picker.strategy.tag.StrategyTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessorService {

	@Autowired
	private RequestService requestService;

	@Autowired
	private StrategyService strategyService;

	@Autowired
	private ResultService resultService;
	
	public void process(Request request) throws Exception {
		if (request == null) {
			throw new Exception("Invalid argument.");
		}

		try {
			request.setStartTime(new Date());
			request.setStatus(Request.STATUS_PROCESSING);
			requestService.modifyRequest(request);

			Strategy strategy = strategyService.getStrategy(request.getStrategyId());
			StrategyTag strategyTag = StrategyTag.parse(strategy.getXml());
			JSONObject jsonResult = PickerUtility.pickPage(request.getTargetUrl(), strategyTag);

			Result result = new Result();
			result.setId(Utility.getUUID());
			result.setCreator(request.getCreator());
			result.setRequestId(request.getId());
			result.setJson(jsonResult.toString());
			resultService.createResult(result);

			request.setResultId(result.getId());
			request.setStatus(Request.STATUS_CLOSED);
			request.setEndTime(new Date());
			requestService.modifyRequest(request);
		} catch (Exception e) {
			request.setStatus(Request.STATUS_ERROR);
			requestService.modifyRequest(request);

			throw e;
		}
	}

}
