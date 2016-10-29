package javascript;

public class JSArray {

	public JSArray() {
		System.scriptEngine.eval("this.obj = new Array();");
	}

	public JSArray(Object[] obj) {
		System.scriptEngine.put("obj", obj);
		System.scriptEngine.eval("this.obj = obj");
	}

}
