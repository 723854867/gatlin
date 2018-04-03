package org.gatlin.core.hook;

import org.gatlin.core.Gatlin;

public interface InitialHook {

	/**
	 * 初始化钩子
	 * 
	 * @param gatlin
	 * @throws Exception
	 */
	void init(Gatlin gatlin) throws Exception;
	
	/**
	 * 越小越先初始化，因此如果初始化钩子有先后顺序则需要重写该方法
	 * 
	 * @return
	 */
	default int priority() {
		return 0;
	}
}
