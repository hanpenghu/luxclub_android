package com.hsbank.util.java.collection.obj;

import java.io.Serializable;
import java.util.Map;

/**
 * 序列化map
 * @author Arthur.Xie
 * 2015-01-31
 */
public class SerializableMap implements Serializable {
    /**序列化Id*/
	private static final long serialVersionUID = -428771987983017160L;
	private Map<String,Object> map;
 
    public Map<String, Object> getMap() {
        return map;
    }
 
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
