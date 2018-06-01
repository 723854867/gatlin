package org.gatlin.util.lang;

import org.gatlin.util.bean.IEnum;

public class EnumUtil {

	public static <E extends Enum<?> & IEnum> E valueOf(Class<E> enumClass, int code) {
		E[] enumConstants = enumClass.getEnumConstants();
		for (E e : enumConstants) {
			if (e.mark() == code)
				return e;
		}
		return null;
	}
}
