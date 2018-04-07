package com.hsbank.util.java.tool.config.impl;

import android.util.Log;

import com.hsbank.util.java.constant.BasicTypeDefaultValue;
import com.hsbank.util.java.tool.config.IConfigFile;
import com.hsbank.util.java.type.NumberUtil;
import com.hsbank.util.java.type.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties配置文件_接口实现类
 * @author xwy
 * 2011-04-20
 */
public class PropConfigFile implements IConfigFile {
	/**<配置文件名称, PropConfigFile实例>*/
    private static Map<String, PropConfigFile> _instanceMap = new HashMap<String, PropConfigFile>();
    /**配置文件名称*/
	private String _configFileName = null;
    /**Properties对象*/
    private Properties _prop = null;
    
    /**
     * 得到一个指定配置文件名称的实例
     * @param configFileName 配置文件名称
     * @return
     */
    public static synchronized PropConfigFile getInstance(String configFileName) {
    	PropConfigFile resultObj = null;
    	if (configFileName == null) {
    		//没有指定配置文件名称，返回一个空对象
    		Log.d(PropConfigFile.class.getName(), "The configFileName is null, please check it.");
    	} else if (configFileName.equals("")) {
    		//没有指定配置文件名称，返回一个空对象
    		Log.d(PropConfigFile.class.getName(), "The configFileName is empty, please check it.");
    		resultObj = new PropConfigFile();
    	} else {
			if (_instanceMap.containsKey(configFileName)) {
	            resultObj = _instanceMap.get(configFileName);
	        } else {
	            resultObj = new PropConfigFile(configFileName);	
	            _instanceMap.put(configFileName, resultObj);
	        }
    	}
        return resultObj;
    }
    
    /**
	 * 定义一个私有的构造函数
	 */
	private PropConfigFile() {
		this._prop = new Properties();
	}
	
	/**
	 * 定义一个私有的构造函数
	 * @param configFileName
	 */
	private PropConfigFile(String configFileName) {
		this._configFileName = configFileName;
		//加载配置文件
		load();
	}
    
	/**
	 * 加载web应用中“WEB-INF\classes”路径下的指定名称的配置文件
	 * <p>
	 * 注：适用于web应用
	 */
	@Override
	public void load() {
		this._prop = new Properties();
        //在不打成war\jar包的情况下，可使用如下方式加载“WEB-INF\classes”下的配置文件
        //_log.debug(this.getClass().getResource("/" + propFileName).getPath());
        InputStream is = this.getClass().getResourceAsStream("/" + this._configFileName);
        if (is == null) {
            //在打成war\jar包的情况下，可使用如下方式读到“WEB-INF\classes”下的配置文件
            is = ClassLoader.getSystemResourceAsStream(this._configFileName);
        } 
        if (is != null) {
            try {
            	//因为Properties的load方法是using the ISO-8859-1 character encoding.   
                //所以如果读的是中文字段，需要做如下方式转码:   
                //_log.debug(new String(prop.getProperty("prop_item_name").getBytes("ISO-8859-1"),"UTF-8") );
                //同时要捕获转码异常。
            	this._prop.load(is);
            } catch(IOException e) {
                Log.d(this.getClass().getName(), "", e);
            }
        } else {
            Log.d(this.getClass().getName(), "Can't load the Properties File： " + this._configFileName);
        }  
	}

	@Override
	public void reload() {
		load();
	}

	@Override
	public String getString(String key) {
		return this._prop.getProperty(key);
	}

	@Override
	public String getString(String key, String defaultValue) {
		return this._prop.getProperty(key, defaultValue);
	}

	@Override
	public int getInt(String key) {
		return NumberUtil.toInt(this._prop.getProperty(key), BasicTypeDefaultValue.DEFAULT_INT);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return NumberUtil.toInt(this._prop.getProperty(key), defaultValue);
	}

	@Override
	public long getLong(String key) {
		return NumberUtil.toLong(this._prop.getProperty(key), BasicTypeDefaultValue.DEFAULT_LONG);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		return NumberUtil.toLong(this._prop.getProperty(key), defaultValue);
	}

	@Override
	public boolean getBoolean(String name) {
		return StringUtil.toBoolean(getString(name), BasicTypeDefaultValue.DEFAULT_BOOLEAN);
	}

	@Override
	public boolean getBoolean(String name, boolean defaultValue) {
		return StringUtil.toBoolean(getString(name), defaultValue);
	}
}
