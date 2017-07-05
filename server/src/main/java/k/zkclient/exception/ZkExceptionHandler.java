package k.zkclient.exception;

import java.lang.Thread.UncaughtExceptionHandler;

public class ZkExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// TODO Auto-generated method stub
		if(t.isInterrupted()){
			System.out.println(t.getName()+"finished");
			e.printStackTrace();
		}
	}
	
}
