package org.gatlin.soa.resource.bean.info;

import java.io.Serializable;

public class ResourceInfo implements Serializable {

	private static final long serialVersionUID = 2425573888253597900L;

	private long id;
	private int cfgId;
	private long owner;
	private long bytes;
	private String url;
	private String name;
	private String path;
	private int priority;
	private int created;
	private int updated;
}
