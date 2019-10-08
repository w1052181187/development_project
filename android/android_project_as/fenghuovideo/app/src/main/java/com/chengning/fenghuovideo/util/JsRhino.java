package com.chengning.fenghuovideo.util;

import android.util.Log;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;

public class JsRhino {

	private static final boolean DEBUG = false;
	private static final String TAG = JsRhino.class.getSimpleName();
	
	private static JsRhino jsRhino = null;

	private Context rhino;
	private Scriptable scope;
	
	private boolean hasEvaluateJs = false;
	
	public static JsRhino getInst(){
		if(jsRhino == null){
			jsRhino = new JsRhino();
			jsRhino.initRhino();
		}
		return jsRhino;
	}

	private synchronized void initRhino() {
		if (DEBUG) {
			Log.d(TAG, "initScript begin time: " + System.currentTimeMillis());
		}
		try {
			rhino = Context.enter();
			rhino.setOptimizationLevel(-1);
			scope = rhino.initStandardObjects();
//			ScriptableObject.putProperty(scope, "javaContext",
//					Context.javaToJS(andContext, scope));
//			ScriptableObject.putProperty(scope, "javaLoader",
//					Context.javaToJS(andContext.getClassLoader(), scope));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (DEBUG) {
			Log.d(TAG, "initScript end time: " + System.currentTimeMillis());
		}
	}
	
	public boolean hasEvaluateJs(){
		return hasEvaluateJs;
	}
	
	public synchronized void evaluateJs(String js, android.content.Context andContext) {
		if (DEBUG) {
			Log.d(TAG, "evaluateJs begin time: " + System.currentTimeMillis());
		}
		try {
			rhino.evaluateString(scope, js, andContext.getClass()
					.getSimpleName(), 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		hasEvaluateJs = true;
		if (DEBUG) {
			Log.d(TAG, "evaluateJs end time: " + System.currentTimeMillis());
		}
	}
	
	public synchronized String runFunction(String functionName,
			Object[] functionParams){
		if (DEBUG) {
			Log.d(TAG, "runFunction begin time: " + System.currentTimeMillis());
		}
		String ret = null;
		try {
			Function function = (Function) scope.get(functionName, scope);
			Object result = function.call(rhino, scope, scope, functionParams);
			if (result instanceof String) {
				ret = (String) result;
			} else if (result instanceof NativeJavaObject) {
				ret = (String) ((NativeJavaObject) result)
						.getDefaultValue(String.class);
			} else if (result instanceof NativeObject) {
				ret = (String) ((NativeObject) result)
						.getDefaultValue(String.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (DEBUG) {
			Log.d(TAG, "runFunction end time: " + System.currentTimeMillis());
		}
		return ret;
	}

	public void destroy() {
		try {
			Context.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
