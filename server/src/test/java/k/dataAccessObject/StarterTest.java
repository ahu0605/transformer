package k.dataAccessObject;

import org.apache.ibatis.session.SqlSession;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import k.dataAccessObject.mapper.AboutUsMapper;

/**
 * Unit test for simple App.
 */
public class StarterTest 
    extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StarterTest( String testName )
    {
        super( testName );
      
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( StarterTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	try {
			Class.forName("k.dataAccessObject.Starter");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	SqlSession session = CmsSessionFactory.getSqlSession();
		try {
		  AboutUsMapper mapper = session.getMapper(AboutUsMapper.class);
		  k.thrift.model.AboutUs blog = mapper.selectByPrimaryKey(0);
		  System.out.println(blog.getTelephone());
		} finally {
		  session.close();
		}
        assertTrue( true );
    }
}
