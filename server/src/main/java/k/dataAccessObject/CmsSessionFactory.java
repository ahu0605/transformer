package k.dataAccessObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;

import com.mysql.jdbc.Connection;

public class CmsSessionFactory {
	
	private static SqlSessionFactory sqlSessionFactory;
	
	static void init() {
		String resource = Starter.PATH+"/mybatis-cms.xml";
		InputStream is = null;
;
		try {
			System.out.println(resource);
			is =  new FileInputStream(new File(resource));
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	public static SqlSession getSqlSession(){
		return sqlSessionFactory.openSession();
	}
	
	public static SqlSession getSqlSession(boolean autoCommit){
		return sqlSessionFactory.openSession(autoCommit);
	}
	
	public static SqlSession getSqlSession(Connection  connection ){
		return sqlSessionFactory.openSession(connection);
	}
	
	public static SqlSession getSqlSession(TransactionIsolationLevel   level ){
		return sqlSessionFactory.openSession(level);
	}
}
