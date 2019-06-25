package com.sja.demo.bigdata.hbase;
 
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
 
/**
 *
 * @author author
 *
 */
 
public class ConfigUtil {
 
	private Properties props = new Properties();
 
	public ConfigUtil(String file) {
	String path = ConfigUtil.class.getClassLoader()
	.getResource(file).getPath();
	InputStream is = null;
	try {
	is = new FileInputStream(path);
	props.load(is);
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	} finally {
	try {
	is.close();
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	}
	}
 
	public int getInt(String key) {
	return Integer.parseInt(props.getProperty(key));
	}
 
	public String getString(String key) {
	return props.getProperty(key);
	}
 
}