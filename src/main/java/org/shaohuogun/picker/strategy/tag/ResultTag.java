package org.shaohuogun.picker.strategy.tag;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class ResultTag extends Tag {

	public static final String FIELD_XPATH = "xpath";
	public static final String FIELD_JSON_KEY = "jsonKey";

	private String xpath;

	private String jsonKey;

	public ResultTag(Tag parent) {
		super(parent);
	}
	
	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getJsonKey() {
		return jsonKey;
	}

	public void setJsonKey(String jsonKey) {
		this.jsonKey = jsonKey;
	}
	
	protected String getUri() {
		String uri = getName();
		// 对新构造的对象属性"xpath"初始化赋值之前该值为空
		if (this.xpath != null) {
			uri += "[" + this.xpath + "]";
		}
		
		if (getParent() != null) {
			uri = getParent().getUri() + URI_SEPERATOR + uri;
		}

		return uri;
	}
	
	protected static final String parseAttribute(Node node, String attribute, boolean nullable, String tagUri)
			throws Exception {
		if ((node == null) || (attribute == null) || attribute.isEmpty() || (tagUri == null) || tagUri.isEmpty()) {
			throw new Exception("Invalid arguments.");
		}

		NamedNodeMap namedNodeMap = node.getAttributes();
		Node attributeNode = namedNodeMap.getNamedItem(attribute);
		if ((attributeNode == null) || (attributeNode.getNodeValue() == null)
				|| attributeNode.getNodeValue().isEmpty()) {
			if (nullable) {
				return null;
			} else {
				throw new Exception(tagUri + " requires [" + attribute + "].");
			}
		}
		
		return attributeNode.getNodeValue();
	}

}
