package k.zkclient;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import k.zkclient.exception.ZkExceptionHandler;

public class ZookeeperClient implements Watcher,Runnable{
	 private String serviceRoot;
	 private ZooKeeper zk;
	 private String processId;
	 private boolean dead = false;
	 private static ZookeeperClient zkClient= null;
	 private static final CountDownLatch connectedLatch = new CountDownLatch(1);
	 
	 public ZookeeperClient(String hostPort,String serviceRoot,String pid){
		 this.processId = pid;
		 this.serviceRoot = serviceRoot;
		 try{
			 zk = new ZooKeeper(hostPort,3000,this);
		 }catch(IOException ex){
			 ex.printStackTrace();
		 }
		 try {
			if(connectedLatch.await(3000, TimeUnit.SECONDS)){
				System.out.println("---znode register---");
				zk.create(serviceRoot+'/'+processId,processId.getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			}
		} catch (InterruptedException | KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 }
	  
//	 public void setData(final String hostPort) throws KeeperException, InterruptedException{
//		 zk.exists(serviceRoot+'/'+processId, new Watcher(){
//
//			@Override
//			public void process(WatchedEvent event) {
//				// TODO Auto-generated method stub
//				System.out.println("set data todo");
//				if(event.getType() == event.getType().None){
//					try {
//						zk.setData(serviceRoot+'/'+processId,hostPort.getBytes() ,-1);
//						
//					} catch (KeeperException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//			 
//		 });
//		
//	 }
//	 
	 public void close(){
		 try{
			 zk.close();
		 }catch(InterruptedException ex){
			 ex.printStackTrace();
		 }
//		 synchronized (this) {
//	            notifyAll();
//	     }
	 }
	  
	@Override
	public void process(WatchedEvent event) {
		    
	        if (event.getType() == Event.EventType.None) {
	            // We are are being told that the state of the
	            // connection has changed
	            switch (event.getState()) {
	            	case SyncConnected:
	                // In this particular example we don't need to do anything
	                // here - watches are automatically re-registered with 
	                // server and any watches triggered while the client was 
	                // disconnected will be delivered (in order of course)
	            		if(connectedLatch.getCount()>0)
	            				connectedLatch.countDown();
	            		break;
	            	case Expired:
	                // It's all over
	            		dead = true;
	            		try {
	            			zk.close();
	            		} catch (InterruptedException e) {
	            			e.printStackTrace();
	            		}
	            		break;
	            }
	        } else {
//	            if (path != null && path.equals(znode)) {
//	                // Something has changed on the node, let's find out
//	                zk.exists(znode, true, this, null);
//	            }
	        }
	       
	        System.out.println("\nEvent Received:%s"+event.toString());
	}
	 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			synchronized(this){
				while(!dead){
					wait();
				}
			}
		}catch(InterruptedException ex){
			ex.printStackTrace();
			Thread.currentThread().interrupt();
		}finally{
			this.close();
		}
	}
	 
	public static void start(String hostPort,String serviceRoot,String nodeName){
		
		ThreadFactory threadFactory = new ThreadFactory() {
	            @Override
	            public Thread newThread(Runnable r) {
	                // System.out.println("creating pooled thread");
	                final Thread thread = new Thread(r);
	                thread.setName("4k-zkclient-thread");
	                thread.setUncaughtExceptionHandler(new ZkExceptionHandler());
	                return thread;
	            }
	      };
	   
	      ExecutorService singleThreadPool = Executors.newSingleThreadScheduledExecutor(threadFactory);
	      zkClient = getIntance(hostPort,serviceRoot,nodeName);    
	      singleThreadPool.execute(zkClient);
		
	}
	
	private static ZookeeperClient getIntance(String serviceRoot,String hostPort,String nodeName){
		
		if(zkClient != null ){
			return zkClient;
		}else{
			//Get the process id
			//String name = ManagementFactory.getRuntimeMXBean().getName();
			//int index = name.indexOf('@');
			//Long processId = Long.parseLong(name.substring(0,index));
			zkClient = new ZookeeperClient(serviceRoot,hostPort,nodeName);
			
			return zkClient;
		}
	}
	//after start;
	public static  void closeAll(){
		zkClient.closeAll();
	}
	
}
