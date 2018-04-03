package org.gatlin.util.serial;

import java.lang.reflect.Type;
import java.nio.charset.Charset;

import org.gatlin.util.Consts;

public class GsonSerializer implements Serializer {
	
	public static final GsonSerializer INSTANCE = new GsonSerializer();
	public static final GsonSerializer ANNOTATED = new GsonSerializer(true);
	
	protected Charset charset = Consts.UTF_8;
	protected boolean annotated;
	
	public GsonSerializer() {}
	
	public GsonSerializer(boolean annotated) {
		this.annotated = annotated;
	}
	
	@Override
	public byte[] serial(Object object) {
		String value = annotated ? SerializeUtil.GSON_ANNO.toJson(object) : SerializeUtil.GSON.toJson(object);
		return value.getBytes(charset);
	}

	@Override
	public <ENTITY> ENTITY deserial(byte[] buffer, Type type) {
		String value = new String(buffer, charset);
		return annotated ? SerializeUtil.GSON_ANNO.fromJson(value, type) : SerializeUtil.GSON.fromJson(value, type);
	}

	@Override
	public <ENTITY> ENTITY deserial(byte[] buffer, Class<ENTITY> clazz) {
		String value = new String(buffer, charset);
		return annotated ? SerializeUtil.GSON_ANNO.fromJson(value, clazz) : SerializeUtil.GSON.fromJson(value, clazz);
	}
}
