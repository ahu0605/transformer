package k.dataAccessObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import k.thrift.ThriftServer;
import k.zkclient.ZookeeperClient;


public class Starter {
	public static final String PATH=System.getProperty("res");
	private static Logger log = LoggerFactory.getLogger(Starter.class); 
	public static Config cfg;
	
	static{
		init();
		CmsSessionFactory.init();
	}
	
	public static void init() {
		InputStream is = null;
		Properties property = new Properties();
		try{
			is = new FileInputStream(new File(PATH+"/config.properties"));
			if( is == null){
				log.error("-----Starter load properties failed-----");
				throw new RuntimeException();
			}
			property.load(is);
		}catch(IOException  io){
			log.error(io.getMessage());
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
		}
		
		cfg = new Config();
		cfg.setServicePath(property.getProperty("service_path"));
		cfg.setZookeeper(property.getProperty("zookeeper"));
		cfg.setThriftLocalhost(property.getProperty("local_thrift_host"));
		cfg.setThriftLocalPort(Integer.parseInt(
					property.getProperty("local_thrift_port")
				));
		
	}
	
	public static Config getCfg() {
		return cfg;
	}

	public static class Config{
		
		private String zookeeper;
		private String servicePath;
		private String thriftLocalhost;
		private int thriftLocalPort;
		
		public String getZookeeper() {
			return zookeeper;
		}
		public void setZookeeper(String zookeeper) {
			this.zookeeper = zookeeper;
		}
		public String getServicePath() {
			return servicePath;
		}
		public void setServicePath(String servicePath) {
			this.servicePath = servicePath;
		}
		public String getThriftLocalhost() {
			return thriftLocalhost;
		}
		public void setThriftLocalhost(String thriftLocalhost) {
			this.thriftLocalhost = thriftLocalhost;
		}
		public int getThriftLocalPort() {
			return thriftLocalPort;
		}
		public void setThriftLocalPort(int thriftLocalPort) {
			this.thriftLocalPort = thriftLocalPort;
		}
			
	}
	
	
	public static void main(String[] args) {
		
		ZookeeperClient.start( cfg.getZookeeper(),cfg.getServicePath(),cfg.getThriftLocalhost()+":"+cfg.thriftLocalPort);
		ThriftServer.StartServer();
		
	}
	
}
