package com.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * properties帮助类 默认加载config.properties
 */
public class PropertyUtils {

	private static Logger logger = Logger.getLogger(PropertyUtils.class);

	private static final String config = "config/property/conf.properties";

	private static Map<String, String> config_map = new HashMap<String, String>();

	static {
		load(config);
	}

	public static String getProperty(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return config_map.get(key);
	}

	public static void load(String name) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		Properties p = new Properties();
		try {
			p.load(is);
				for (Map.Entry<Object,Object> e : p.entrySet()) {
					config_map.put((String) e.getKey(), (String) e.getValue());
				}

		} catch (IOException e) {
			logger.fatal("load property file failed. file name: " + name, e);
		}
	}

}
