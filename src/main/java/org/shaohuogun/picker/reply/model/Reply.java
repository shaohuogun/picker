package org.shaohuogun.picker.reply.model;

import org.shaohuogun.common.Entity;

public class Reply extends Entity {

	private static final long serialVersionUID = 1L;

	public static final Character SENT_NOT = '0';
	public static final Character SENT_YES = '1';

	private String requestId;

	private String strategyId;

	private String content;

	private Character sent = SENT_NOT;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String json) {
		this.content = json;
	}

	public Character getSent() {
		return sent;
	}

	public void setSent(Character sent) {
		this.sent = sent;
	}

}
