package org.shaohuogun.picker.request.model;

import java.util.Date;

import org.shaohuogun.common.Model;

public class Request extends Model {

	private static final long serialVersionUID = 1L;
	
	public static final String KEY_TARGET_URL = "targetUrl";
	public static final String KEY_TARGET_TYPE = "targetType";
	public static final String KEY_BATCH_NO = "batchNo";

	public static final String STATUS_INITIAL = "initial";
	public static final String STATUS_PROCESSING = "processing";
	public static final String STATUS_SUSPENDED = "suspended";
	public static final String STATUS_CLOSED = "closed";
	public static final String STATUS_ERROR = "error";

	private String targetUrl;
	
	private String targetType;
	
	private String batchNo;

	private String resultId;

	private String status = STATUS_INITIAL;

	private Date startTime;

	private Date endTime;

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
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
