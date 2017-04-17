package org.shaohuogun.picker.result.model;

import org.shaohuogun.common.Model;

public class Result extends Model {

	private static final long serialVersionUID = 1L;
	
	public static final Character SENT_NOT = '0';
	public static final Character SENT_YES = '1';
	
	private String requestId;
	
	private String strategyId;

	private String json;
	
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

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Character getSent() {
		return sent;
	}

	public void setSent(Character sent) {
		this.sent = sent;
	}

}
