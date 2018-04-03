package org.gatlin.core.bean;

import java.io.Serializable;

/**
 * 实体类
 * 
 * @author lynn
 */
public interface Entity<KEY> extends Serializable {

	KEY key();
}
