package k.dataAccessObject;

import k.thrift.ThriftServer;
import k.zkclient.ZookeeperClient;

public class Stoper {
	public static void main(String[] args) {
		
		ThriftServer.stop();
		ZookeeperClient.closeAll();
		
	}
}
