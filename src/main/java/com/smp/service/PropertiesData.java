package com.smp.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class PropertiesData{
	Properties properties = new Properties();
	public void initProp() throws IOException
	{
		URL u = new URL("http://localhost/connectBoxAPI.properties");
		properties.load(u.openStream());
	}
	
	public String getProp(String key)
	{
		String value = properties.getProperty(key);
		return value;
	}
}