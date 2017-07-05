package k.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import k.dataAccessObject.Starter;
import k.thrift.service.AboutUsService;
import k.thrift.service.impl.AboutUsServiceImpl;

public class ThriftServer {
	static  TServer server = null; 	  
	public static void StartServer() {  
		try {  
		   TServerTransport serverTransport = new TServerSocket(
				   Starter.getCfg().getThriftLocalPort()
				   );
		   
		   Factory portFactory = new TBinaryProtocol.Factory(true, true);
		   Args args = new Args(serverTransport);
		   args.processor(registerService());
		   args.protocolFactory(portFactory);
		   
		   server = new TThreadPoolServer(args);  	  
		   System.out.println("Starting the simple server...");  
		   server.serve();  
		      
		 } catch (Exception e) {  
		   e.printStackTrace();  
		 }  
	}  
	
	public static TMultiplexedProcessor registerService(){
		
		TMultiplexedProcessor processor = new TMultiplexedProcessor();
		
		AboutUsService.Processor<AboutUsServiceImpl> aboutUs 
			= new AboutUsService.Processor<>(new AboutUsServiceImpl());
		processor.registerProcessor("AboutUsService", aboutUs);
			
		return processor;
		   
	}
	
	public static void stop() {  
		if(server != null){
			server.stop();
		}
	}  
}
