package org.shaohuogun.picker.strategy.model;

import org.shaohuogun.common.Model;

public class Strategy extends Model {

	private static final long serialVersionUID = 1L;
	
	public static final String KEY_STRATEGY_NAME = "strategyName";
	
	private String name;
	
	private String xml;

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
