package com.nixsolutions.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

import javax.management.RuntimeErrorException;

public class DaoFactory {

	private static final String USER_DAO = "dao.com.nixsolutions.usermanagement.db.UserDao";
	private final Properties properties;
	
	private final static DaoFactory INSTANCE =  new DaoFactory();
	
	public static DaoFactory getInstance(){
		return INSTANCE;
	}

	public DaoFactory() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ConnectionFactory getConenctionFactory() {
		String user = properties.getProperty("connection.user");
		String password = properties.getProperty("connection.password");
		String url = properties.getProperty("connection.url");
		String driver = properties.getProperty("connection.driver");
		return new ConnectionFactoryImpl(driver, url, user, password);
	}

	public UserDao getUserDao() {
		UserDao result = null;
		try {
			Class clazz = Class.forName(properties.getProperty(USER_DAO));
			result = (UserDao) clazz.newInstance();
			result.setConnectionFactory(getConenctionFactory());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}
