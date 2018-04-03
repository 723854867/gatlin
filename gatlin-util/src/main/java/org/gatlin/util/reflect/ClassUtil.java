package org.gatlin.util.reflect;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil {
	
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassUtil.class.getClassLoader();
			if (cl == null) {
				// getClassLoader() returning null indicates the bootstrap ClassLoader
				try {
					cl = ClassLoader.getSystemClassLoader();
				}
				catch (Throwable ex) {
					// Cannot access system ClassLoader - oh well, maybe the caller can live with null...
				}
			}
		}
		return cl;
	}
	/**
	 * 只能扫描文件系统中的文件，不能扫描打包中的文件夹
	 * 
	 * @param pack
	 * @param inner
	 * @return
	 */
	public static List<Class<?>> scanPackage(String pack, boolean inner) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String path = pack.replaceAll("\\.", "\\/");
		path = getDefaultClassLoader().getResource(path).getPath();
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
            throw new RuntimeException("package " + pack + " not exist!");
        }
		scanFile(classes, file, pack, inner);
		return classes;
	}

	public static void scanFile(List<Class<?>> classes, File directory, String parentPackage, final boolean inner) {
		File[] files = directory.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				String name = file.getName();
				boolean flag = file.isDirectory() || name.endsWith(".class");
				if (!inner) {
                    flag = flag && !name.contains("$");
                }
				return flag;
			}
		});
		for (File file : files) {
			String pack = parentPackage + "." + file.getName().replaceAll(".class", "");
			if (file.isDirectory()) {
                scanFile(classes, file, pack, inner);
            } else {
				try {
					Class<?> clazz = Class.forName(pack);
					classes.add(clazz);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Class - " + pack + " not found!", e);
				}
			}
		}
	}

	public static boolean isString(Object value) {
		return value.getClass() == String.class;
	}

	public static boolean isInteger(Object value) {
		return value.getClass() == int.class || value.getClass() == Integer.class;
	}

	public static boolean isDouble(Object value) {
		return value.getClass() == double.class || value.getClass() == Double.class;
	}

	public static boolean isByte(Object value) {
		return value.getClass() == byte.class || value.getClass() == Byte.class;
	}

	public static boolean isFloat(Object value) {
		return value.getClass() == float.class || value.getClass() == Float.class;
	}

	public static boolean isShort(Object value) {
		return value.getClass() == short.class || value.getClass() == Short.class;
	}

	public static boolean isLong(Object value) {
		return value.getClass() == long.class || value.getClass() == Long.class;
	}

	public static boolean isNumber(Object value) {
		return isInteger(value) || isLong(value) || isDouble(value) || isShort(value) || isByte(value) || isFloat(value);
	}
	
	public static boolean isBool(Object value) { 
		return value.getClass() == boolean.class || value.getClass() == Boolean.class;
	}
}
