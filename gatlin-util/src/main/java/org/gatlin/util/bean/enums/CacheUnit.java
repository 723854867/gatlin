package org.gatlin.util.bean.enums;

// 内存单位
public enum CacheUnit {

	B,
	KB {
		@Override
		public long bytes(long size) {
			return 1024 * size;
		}
	},
	MB {
		@Override
		public long bytes(long size) {
			return 1024 * 1024 * size;
		}
	},
	GB {
		@Override
		public long bytes(long size) {
			return 1024 * 1024 * 1024 * size;
		}
	};
	
	public long bytes(long size) {
		return size;
	}
}
