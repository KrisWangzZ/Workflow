package engine.hibernate;

import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.Connection;

public class MySQLConnectionFactory {
	
	private static String driver;
	private static String url;
	private static String username;
	private static String pwd;
	private static String useUnicode;
	private static String characterEncoding;
	
	private static MySQLConnectionFactory connectionFactory = new MySQLConnectionFactory();
	
	private Connection connection = null;
	
	
	static{
		Properties p = new Properties();
		
		try {
			p.load(new DefaultResourceLoader().getResource("config.properties").getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver = p.getProperty("driver");
		url = p.getProperty("url");
		username = p.getProperty("user");
		pwd = p.getProperty("password");
		useUnicode = p.getProperty("useUnicode");
		characterEncoding = p.getProperty("p.getProperty");
		
	}
	
	public Connection getConnection(){
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, pwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
		
	}
	
	public static MySQLConnectionFactory getInstance(){
		return connectionFactory;
	}
	
	public static void main(String[] arg){
		MySQLConnectionFactory connectionFactory = MySQLConnectionFactory.getInstance();
		
		connectionFactory.getConnection();
		if(connectionFactory.getConnection() != null){
        	System.out.println("成功链接MySQL");
        }
	}

}
