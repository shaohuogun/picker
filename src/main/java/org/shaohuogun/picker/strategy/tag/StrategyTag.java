package org.shaohuogun.picker.strategy.tag;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StrategyTag extends Tag {
	
	public static final String TAG_NAME = "strategy";

	private ResultTag resultTag;

	private Map<String, HandlerTag> handlerTagMap = new HashMap<String, HandlerTag>();

	public StrategyTag(Tag parent) {
		super(parent);
	}
	
	@Override
	public String getName() {
		return TAG_NAME;
	}
	
	@Override
	protected String getUri() {
		return getName();
	}

	public ResultTag getResultTag() {
		return resultTag;
	}

	public void setResultTag(ResultTag resultTag) {
		this.resultTag = resultTag;
	}

	public HandlerTag getHandlerTag(String handlerId) throws Exception {
		if (handlerId == null) {
			throw new Exception("Invalid arguments.");
		}
		
		return handlerTagMap.get(handlerId);
	}

	public void addHandlerTag(HandlerTag handlerTag) throws Exception {
		if (handlerTag == null) {
			throw new Exception("Invalid arguments.");
		}

		this.handlerTagMap.put(handlerTag.getId(), handlerTag);
	}

	public static final StrategyTag parse(final String xml) throws Exception {
		if ((xml == null) || xml.isEmpty()) {
			throw new Exception("Invalid arguments.");
		}

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

		InputStream is = new ByteArrayInputStream(xml.getBytes());
		Document doc = docBuilder.parse(is);

		// 获取Document对象的根节点
		Element root = doc.getDocumentElement();
		if (root == null) {
			throw new Exception("Strategy XML's format is incorrect.");
		}

		NodeList nodes = root.getChildNodes();
		if ((nodes == null) || (nodes.getLength() == 0)) {
			throw new Exception("Strategy XML is empty.");
		}

		StrategyTag strategyTag = new StrategyTag(null);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			String tagName = node.getNodeName();
			if (ObjectTag.TAG_NAME.equalsIgnoreCase(tagName)) {
				strategyTag.setResultTag(ObjectTag.parse(node, strategyTag));
			} else if (ArrayTag.TAG_NAME.equalsIgnoreCase(tagName)) {
				strategyTag.setResultTag(ArrayTag.parse(node, strategyTag));
			} else if (StringTag.TAG_NAME.equalsIgnoreCase(tagName)) {
				strategyTag.setResultTag(StringTag.parse(node, strategyTag));
			} else if (HandlerTag.TAG_NAME.equalsIgnoreCase(tagName)) {
				strategyTag.addHandlerTag(HandlerTag.parse(node, strategyTag));
			}
		}
		
		return strategyTag;
	}
	
}
