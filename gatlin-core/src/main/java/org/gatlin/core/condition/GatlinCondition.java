package org.gatlin.core.condition;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.Option;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public abstract class GatlinCondition<T> implements Condition {
	
	private Option<T> option;
	
	protected GatlinCondition(Option<T> option) {
		this.option = option;
	}

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		T value = GatlinConfigration.get(option);
		return null == value ? false : checkCondition(value);
	}

	protected abstract boolean checkCondition(T value);
}
