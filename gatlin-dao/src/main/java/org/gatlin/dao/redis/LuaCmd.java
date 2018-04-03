package org.gatlin.dao.redis;

public enum LuaCmd implements ILuaCmd {
	
	// 相等则删除
	DEL_IF_EQUALS(1),
	// 获取验证码
	CAPTCHA_OBTAIN(2),
	
	MULTILOCK(0),
	RELEASE_LOCK(0) {
		@Override
		public int keyCount() {
			throw new UnsupportedOperationException("lua RELEASE_LOCK key is variable!");
		}
	};
	
	private int keyCount;

	private LuaCmd(int keyCount) {
		this.keyCount = keyCount;
	}
	
	@Override
	public String key() {
		return name();
	}
	
	@Override
	public int keyCount() {
		return keyCount;
	}
}
