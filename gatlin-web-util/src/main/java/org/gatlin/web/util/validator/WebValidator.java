package org.gatlin.web.util.validator;

import org.gatlin.soa.bean.model.ValidatorContext;
import org.gatlin.soa.bean.param.SoaParam;

public interface WebValidator<PARAM extends SoaParam, CONTEXT extends ValidatorContext<PARAM>> {
	
	void validate(CONTEXT context);

	default int priority() {
		return 0;
	}
}
