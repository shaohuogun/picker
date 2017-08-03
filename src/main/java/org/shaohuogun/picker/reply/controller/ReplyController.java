package org.shaohuogun.picker.reply.controller;

import org.shaohuogun.common.Controller;
import org.shaohuogun.common.Pagination;
import org.shaohuogun.picker.reply.model.Reply;
import org.shaohuogun.picker.reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyController extends Controller {
	
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping(value = "/api/request/{id}/replies", method = RequestMethod.GET)
	public Pagination getRepliesOfRequest(@PathVariable String id,
			@RequestParam(defaultValue = "1", required = false) int page) throws Exception {		
		int total = replyService.getReplyCountOfRequest(id);
		Pagination pagination = new Pagination();
		pagination.setTotal(total);
		pagination.setPageIndex(page);
		return replyService.getRepliesOfRequest(id, pagination);
	}
	
	@RequestMapping(value = "/api/reply/{id}", method = RequestMethod.GET)
	public Reply getReply(@PathVariable String id) throws Exception {		
		return replyService.getReply(id);
	}
	
}
