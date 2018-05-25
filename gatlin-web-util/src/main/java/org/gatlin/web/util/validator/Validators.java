package org.gatlin.web.util.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.gatlin.core.util.SpringContextUtil;
import org.gatlin.soa.bean.model.WithdrawContext;
import org.springframework.stereotype.Component;

@Component
public class Validators {

	public void withdraw(WithdrawContext context) { 
		Map<String, IWithdrawValidator> hooks = SpringContextUtil.getApplicationContext().getBeansOfType(IWithdrawValidator.class, false, true);
		List<IWithdrawValidator> list = new ArrayList<IWithdrawValidator>(hooks.values());
		Collections.sort(list, (o1, o2) -> o1.priority() - o2.priority());
		for (IWithdrawValidator validator : list)
			validator.validate(context);
	}
}
