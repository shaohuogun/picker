package org.shaohuogun.picker.strategy.tag;

import org.w3c.dom.Node;

public class StringTag extends ResultTag {

	public static final String TAG_NAME = "string";

	public static final String FIELD_ATTRIBUTE = "attribute";
	public static final String FIELD_HANDLER = "handler";
	
	private String attribute;
	
	private String handler;

	public StringTag(Tag parent) {
		super(parent);
	}

	@Override
	public String getName() {
		return TAG_NAME;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public static final StringTag parse(Node node, Tag parent) throws Exception {
		if (node == null) {
			throw new Exception("Invalid arguments.");
		}

		StringTag stringTag = new StringTag(parent);
		stringTag.setXpath(parseAttribute(node, FIELD_XPATH, false, stringTag.getUri()));
		stringTag.setJsonKey(parseAttribute(node, FIELD_JSON_KEY, false, stringTag.getUri()));
		stringTag.setAttribute(parseAttribute(node, FIELD_ATTRIBUTE, true, stringTag.getUri()));
		stringTag.setHandler(parseAttribute(node, FIELD_HANDLER, true, stringTag.getUri()));

		return stringTag;
	}

}
