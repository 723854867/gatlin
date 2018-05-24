package org.gatlin.sdk.jisu.request;

import org.gatlin.sdk.jisu.JisuConfig;
import org.gatlin.sdk.jisu.result.JieQi;

public class JieQiRequest extends JisuRequet<JieQi, JieQiRequest> {

	public JieQiRequest() {
		super("jieqi/query", JisuConfig.JIE_QI);
	}
	
	public JieQiRequest year(String year) {
		this.params.put("year", year);
		return this;
	}
}
