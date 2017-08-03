package org.shaohuogun.picker.processor.service;

import java.util.Date;

import org.json.JSONObject;
import org.shaohuogun.common.Utility;
import org.shaohuogun.picker.request.model.Request;
import org.shaohuogun.picker.request.service.RequestService;
import org.shaohuogun.picker.reply.model.Reply;
import org.shaohuogun.picker.reply.service.ReplyService;
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
	private ReplyService replyService;

	public void process(Request req) throws Exception {
		if (req == null) {
			throw new NullPointerException("Request cann't be null.");
		}

		try {
			req.setStatus(Request.STATUS_PROCESSING);
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

					Reply reply = new Reply();
					reply.setId(Utility.getUUID());
					reply.setCreator(req.getCreator());
					reply.setRequestId(req.getId());
					reply.setStrategyId(strategy.getId());
					reply.setContent(jsonResult.toString());
					replyService.createReply(reply);
				}
			} else {
				JSONObject jsonResult = PickerUtility.pickPage(url, strategyTag);

				Reply reply = new Reply();
				reply.setId(Utility.getUUID());
				reply.setCreator(req.getCreator());
				reply.setRequestId(req.getId());
				reply.setStrategyId(strategy.getId());
				reply.setContent(jsonResult.toString());
				replyService.createReply(reply);
			}

			req.setStatus(Request.STATUS_CLOSED);
			req.setEndTime(new Date());
			requestService.modifyRequest(req);
		} catch (Exception e) {
			req.setStatus(Request.STATUS_ERROR);
			requestService.modifyRequest(req);
			throw e;
		}
	}

}
