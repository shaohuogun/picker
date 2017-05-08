package org.shaohuogun.picker.processor.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.shaohuogun.picker.strategy.tag.ArrayTag;
import org.shaohuogun.picker.strategy.tag.ObjectTag;
import org.shaohuogun.picker.strategy.tag.ResultTag;
import org.shaohuogun.picker.strategy.tag.StrategyTag;
import org.shaohuogun.picker.strategy.tag.StringTag;
import org.shaohuogun.picker.strategy.tag.Tag;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public abstract class PickerUtility {

	private final static String pickString(final DomElement domElement, final StringTag stringTag) throws Exception {
		if (domElement == null) {
			throw new NullPointerException("Page's DOM data structure doesn't have expected node.");
		}

		if (stringTag == null) {
			throw new NullPointerException("StringTag cann't be null.");
		}

		String result = null;
		String attribute = stringTag.getAttribute();
		String type = stringTag.getType();
		String handler = stringTag.getHandler();
		if ((attribute == null) || attribute.isEmpty()) {
			if (StringTag.TYPE_TEXT.equalsIgnoreCase(type)) {
				result = domElement.asText();
			} else if (StringTag.TYPE_XML.equalsIgnoreCase(type)) {
				result = domElement.asXml();
			}
		} else {
			result = domElement.getAttribute(attribute);
		}

		if ((handler != null) && !handler.isEmpty()) {
			Tag root = stringTag.getRoot();
			if (root instanceof StrategyTag) {
				String script = ((StrategyTag) root).getHandlerTag(handler).getScript();
				result = ScriptUtility.execute(script, result);
			}
		}

		return result;
	}

	private final static JSONObject pickObject(final DomElement domElement, final List<ResultTag> resultTags)
			throws Exception {
		if (domElement == null) {
			throw new NullPointerException("Page's DOM data structure doesn't have expected node.");
		}

		if ((resultTags == null) || resultTags.isEmpty()) {
			throw new IllegalArgumentException("ResultTags cann't be null or empty.");
		}

		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < resultTags.size(); i++) {
			ResultTag resultTag = resultTags.get(i);
			String jsonKey = resultTag.getJsonKey();
			if (resultTag instanceof StringTag) {
				jsonObject.put(jsonKey,
						pickString(domElement.getFirstByXPath(resultTag.getXpath()), (StringTag) resultTag));
			} else if (resultTag instanceof ObjectTag) {
				jsonObject.put(jsonKey, pickObject(domElement.getFirstByXPath(resultTag.getXpath()),
						((ObjectTag) resultTag).getResultTags()));
			} else if (resultTag instanceof ArrayTag) {
				jsonObject.put(jsonKey,
						pickArray(domElement.getByXPath(resultTag.getXpath()), ((ArrayTag) resultTag).getResultTags()));
			}
		}

		return jsonObject;
	}

	private final static JSONArray pickArray(final List<DomElement> domElements, final List<ResultTag> resultTags)
			throws Exception {
		if ((domElements == null) || domElements.isEmpty()) {
			throw new IllegalArgumentException("Page's DOM data structure doesn't have expected nodes.");
		}

		if ((resultTags == null) || resultTags.isEmpty()) {
			throw new IllegalArgumentException("ResultTags cann't be null or empty.");
		}

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < domElements.size(); i++) {
			jsonArray.put(pickObject(domElements.get(i), resultTags));
		}

		return jsonArray;
	}

	public final static JSONObject pickPage(String url, StrategyTag strategyTag) throws Exception {
		if ((url == null) || url.isEmpty()) {
			throw new IllegalArgumentException("Url cann't be null or empty.");
		}
		
		if (strategyTag == null) {
			throw new NullPointerException("StrategyTag cann't be null.");
		}
		
		JSONObject jsonResult = new JSONObject();

		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setAppletEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setDownloadImages(false);
		webClient.getOptions().setGeolocationEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setPopupBlockerEnabled(false);
		
		try {
			HtmlPage page = webClient.getPage(url);
			if (page == null) {
				throw new NullPointerException("Fail to load the page [" + url + "].");
			}

			ResultTag resultTag = strategyTag.getResultTag();
			String jsonKey = resultTag.getJsonKey();
			if (resultTag instanceof ObjectTag) {
				jsonResult.put(jsonKey, pickObject(page.getFirstByXPath(resultTag.getXpath()),
						((ObjectTag) resultTag).getResultTags()));
			} else if (resultTag instanceof ArrayTag) {
				jsonResult.put(jsonKey,
						pickArray(page.getByXPath(resultTag.getXpath()), ((ArrayTag) resultTag).getResultTags()));
			} else if (resultTag instanceof StringTag) {
				jsonResult.put(jsonKey, pickString(page.getFirstByXPath(resultTag.getXpath()), (StringTag) resultTag));
			}
		} finally {
			webClient.close();
		}

		return jsonResult;
	}

}
