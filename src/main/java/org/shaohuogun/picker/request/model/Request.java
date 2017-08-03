package org.shaohuogun.picker.request.model;

import java.util.Date;

import org.shaohuogun.common.Model;

public class Request extends Model {

	private static final long serialVersionUID = 1L;
	
	public static final String KEY_ACTION_TYPE = "actionType";
	public static final String KEY_SERIAL_NUMBER = "serialNumber";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_HOOK_URL = "hookUrl";
	
	public static final String STATUS_INITIAL = "initial";
	public static final String STATUS_PROCESSING = "processing";
	public static final String STATUS_SUSPENDED = "suspended";
	public static final String STATUS_CLOSED = "closed";
	public static final String STATUS_ERROR = "error";
	
	private String actionType;
	
	private String serialNumber;

	private String content;

	private String hookUrl;

	private String status = STATUS_INITIAL;

	private Date startTime;

	private Date endTime;

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getHookUrl() {
		return hookUrl;
	}

	public void setHookUrl(String hookUrl) {
		this.hookUrl = hookUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}	

}
