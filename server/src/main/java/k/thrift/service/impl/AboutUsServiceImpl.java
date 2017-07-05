package k.thrift.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.TException;

import k.dataAccessObject.CmsSessionFactory;
import k.dataAccessObject.mapper.AboutUsMapper;
import k.thrift.model.AboutUs;
import k.thrift.service.AboutUsService;
import k.thrift.service.AboutUsService.Iface;
import k.thrift.service.exception.AboutUsException;

public class AboutUsServiceImpl 
	implements k.thrift.service.AboutUsService.Iface {

	@Override
	public AboutUs selectAboutUs(int id) throws AboutUsException, TException {
		SqlSession ssesion = CmsSessionFactory.getSqlSession();
		try{
			AboutUsMapper mapper = ssesion.getMapper(AboutUsMapper.class);
			AboutUs aboutUs = mapper.selectByPrimaryKey(id);
			return aboutUs;
		}finally{
			ssesion.close();
		}
		
	}
	  
}
