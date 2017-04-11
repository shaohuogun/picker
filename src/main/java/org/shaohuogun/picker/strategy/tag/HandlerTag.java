package org.shaohuogun.picker.strategy.tag;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class HandlerTag extends Tag {

	public static final String TAG_NAME = "handler";

	public static final String FIELD_ID = "id";
	public static final String FIELD_SCRIPT_TYPE = "scriptType";

	public final static String SCRIPT_TYPE_JS = "javascript";
	public final static String SCRIPT_TYPE_PY = "python";

	private String id;

	private String scriptType;

	private String script;

	public HandlerTag(Tag parent) {
		super(parent);
	}

	@Override
	public String getName() {
		return TAG_NAME;
	}

	@Override
	protected String getUri() {
		String uri = getName();
		// 对新构造的对象属性"id"初始化赋值之前该值为空
		if (this.id != null) {
			uri += "[" + this.id + "]";
		}

		if (getParent() != null) {
			uri = getParent().getUri() + URI_SEPERATOR + uri;
		}

		return uri;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScriptType() {
		return scriptType;
	}

	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	private static final String parseAttribute(Node node, String attribute, boolean nullable, String tagUri)
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

	public static final HandlerTag parse(Node node, Tag parent) throws Exception {
		if (node == null) {
			throw new Exception("Invalid arguments.");
		}

		HandlerTag handlerTag = new HandlerTag(parent);
		handlerTag.setId(parseAttribute(node, FIELD_ID, false, handlerTag.getUri()));
		handlerTag.setScriptType(parseAttribute(node, FIELD_SCRIPT_TYPE, false, handlerTag.getUri()));
		handlerTag.setScript(node.getTextContent());

		return handlerTag;
	}

}
