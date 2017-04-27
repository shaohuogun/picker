package org.shaohuogun.picker.strategy.tag;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ArrayTag extends ResultTag {

	public static final String TAG_NAME = "array";

	private List<ResultTag> resultTags = new ArrayList<ResultTag>();
	
	public ArrayTag(Tag parent) {
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
			throw new NullPointerException("Result tag cann't be null.");
		}
		
		this.resultTags.add(resultTag);
	}
	
	public static final ArrayTag parse(Node node, Tag parent) throws Exception {
		if (node == null) {
			throw new NullPointerException("Node cann't be null.");
		}
		
		ArrayTag arrayTag = new ArrayTag(parent);
		arrayTag.setXpath(parseAttribute(node, FIELD_XPATH, false, arrayTag.getUri()));
		arrayTag.setJsonKey(parseAttribute(node, FIELD_JSON_KEY, false, arrayTag.getUri()));

		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String tagName = childNode.getNodeName();
			if (StringTag.TAG_NAME.equalsIgnoreCase(tagName)) {
				arrayTag.addResultTag(StringTag.parse(childNode, arrayTag));
			} else if (ArrayTag.TAG_NAME.equalsIgnoreCase(tagName)) {
				arrayTag.addResultTag(ArrayTag.parse(childNode, arrayTag));
			} else if (ObjectTag.TAG_NAME.equalsIgnoreCase(tagName)) {
				arrayTag.addResultTag(ObjectTag.parse(childNode, arrayTag));
			}
		}
		
		return arrayTag;
	}

}
