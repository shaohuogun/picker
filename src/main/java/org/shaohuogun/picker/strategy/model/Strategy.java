package org.shaohuogun.picker.strategy.model;

import org.shaohuogun.common.Entity;

public class Strategy extends Entity {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String urlRegex;
	
	private String xml;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrlRegex() {
		return urlRegex;
	}

	public void setUrlRegex(String urlRegex) {
		this.urlRegex = urlRegex;
	}
	
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}
