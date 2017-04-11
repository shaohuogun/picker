package org.shaohuogun.picker.strategy.tag;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ObjectTag extends ResultTag {

	public static final String TAG_NAME = "object";

	private List<ResultTag> resultTags = new ArrayList<ResultTag>();

	public ObjectTag(Tag parent) {
		super(parent);
	}

	@Override
	public String getName() {
		return TAG_NAME;
	}

	public List<ResultTag> getResultTags() {
		return resultTags;
	}

	public void addResultTag(ResultTag resultTag) throws Exception {
		if (resultTag == null) {
			throw new Exception("Invalid arguments.");
		}

		this.resultTags.add(resultTag);
	}

	public static final ObjectTag parse(Node node, Tag parent) throws Exception {
		if (node == null) {
			throw new Exception("Invalid arguments.");
		}

		ObjectTag objectTag = new ObjectTag(parent);
		objectTag.setXpath(parseAttribute(node, FIELD_XPATH, false, objectTag.getUri()));
		objectTag.setJsonKey(parseAttribute(node, FIELD_JSON_KEY, false, objectTag.getUri()));

		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String tagName = childNode.getNodeName();
			if (StringTag.TAG_NAME.equalsIgnoreCase(tagName)) {
				objectTag.addResultTag(StringTag.parse(childNode, objectTag));
			} else if (ArrayTag.TAG_NAME.equalsIgnoreCase(tagName)) {
				objectTag.addResultTag(ArrayTag.parse(childNode, objectTag));
			} else if (ObjectTag.TAG_NAME.equalsIgnoreCase(tagName)) {
				objectTag.addResultTag(ObjectTag.parse(childNode, objectTag));
			}
		}

		return objectTag;
	}

}
