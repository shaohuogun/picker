package org.shaohuogun.picker.strategy.tag;

import org.w3c.dom.Node;

public class StringTag extends ResultTag {

	public static final String TAG_NAME = "string";

	public static final String FIELD_ATTRIBUTE = "attribute";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_HANDLER = "handler";

	public static final String TYPE_TEXT = "text";
	public static final String TYPE_XML = "xml";

	private String attribute;

	private String type = TYPE_TEXT;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public static final StringTag parse(Node node, Tag parent) throws Exception {
		if (node == null) {
			throw new NullPointerException("Node cann't be null.");
		}

		StringTag stringTag = new StringTag(parent);
		stringTag.setXpath(parseAttribute(node, FIELD_XPATH, false, stringTag.getUri()));
		stringTag.setJsonKey(parseAttribute(node, FIELD_JSON_KEY, false, stringTag.getUri()));
		stringTag.setAttribute(parseAttribute(node, FIELD_ATTRIBUTE, true, stringTag.getUri()));

		String type = parseAttribute(node, FIELD_TYPE, true, stringTag.getUri());
		if ((type != null) && !type.isEmpty()) {
			stringTag.setType(type);
		}
		
		stringTag.setHandler(parseAttribute(node, FIELD_HANDLER, true, stringTag.getUri()));

		return stringTag;
	}

}
