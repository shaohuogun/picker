package org.shaohuogun.picker.strategy.model;

import org.shaohuogun.common.Model;

public class Strategy extends Model {

	private static final long serialVersionUID = 1L;
	
	private String urlRegex;
	
	private String name;
	
	private String xml;

	public String getUrlRegex() {
		return urlRegex;
	}

	public void setUrlRegex(String urlRegex) {
		this.urlRegex = urlRegex;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}
