package org.shaohuogun.picker.processor.service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public abstract class ScriptUtility {

	private final static String KEY_PARAM = "param";
	private final static String KEY_RESULT = "result";

	public final static String execute(String script, String param) throws Exception {
		if ((script == null) || script.isEmpty()) {
			throw new IllegalArgumentException("Script cann't be null or empty.");
		}
		
		if ((param == null) || param.isEmpty()) {
			throw new IllegalArgumentException("Param cann't be null or empty.");
		}
		
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		engine.put(KEY_PARAM, param);
		engine.eval(script);
		return (String) engine.get(KEY_RESULT);
	}

}
