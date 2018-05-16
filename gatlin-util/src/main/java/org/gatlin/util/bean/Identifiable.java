package org.gatlin.util.bean;

import java.io.Serializable;

/**
 * 在同一类型中有唯一编号的
 * 
 * @author lynn
 */
public interface Identifiable<KEY> extends Serializable {

	KEY key();
}
