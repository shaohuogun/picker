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
import org.shaohuogun.picker.strategy.tag.StrategyTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessorService {

	private static final String KEY_URL = "url";
	private static final String KEY_AMOUNT = "amount";

	@Autowired
	private RequestService requestService;

	@Autowired
	private ResultService resultService;

	public void process(AsyncRequest req) throws Exception {
		if (req == null) {
			throw new NullPointerException("Request cann't be null.");
		}

		try {
			req.setStatus(AsyncRequest.STATUS_PROCESSING);
			req.setStartTime(new Date());
			requestService.modifyRequest(req);

			JSONObject jsonContent = new JSONObject(req.getContent());
			String url = jsonContent.getString(KEY_URL);
			if ((url == null) || url.isEmpty()) {
				throw new Exception("Target url is empty.");
			}

			Strategy strategy = StrategyPool.getInstance().getSuitableStrategyId(url);
			if (strategy == null) {
				throw new Exception("Cann't find a suitable strategy.");
			}

			StrategyTag strategyTag = StrategyTag.parse(strategy.getXml());
			int amount = jsonContent.getInt(KEY_AMOUNT);
			if (amount > 1) {
				for (int i = 1; i <= amount; i++) {
					JSONObject jsonResult = PickerUtility.pickPage((url + i), strategyTag);

					Result result = new Result();
					result.setId(Utility.getUUID());
					result.setCreator(req.getCreator());
					result.setRequestId(req.getId());
					result.setStrategyId(strategy.getId());
					result.setJson(jsonResult.toString());
					resultService.createResult(result);
				}
			} else {
				JSONObject jsonResult = PickerUtility.pickPage(url, strategyTag);

				Result result = new Result();
				result.setId(Utility.getUUID());
				result.setCreator(req.getCreator());
				result.setRequestId(req.getId());
				result.setStrategyId(strategy.getId());
				result.setJson(jsonResult.toString());
				resultService.createResult(result);
			}

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
