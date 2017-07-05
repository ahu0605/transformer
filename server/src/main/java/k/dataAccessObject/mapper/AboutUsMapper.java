package k.dataAccessObject.mapper;

import k.thrift.model.AboutUs;

public interface AboutUsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AboutUs record);

    int insertSelective(AboutUs record);

    AboutUs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AboutUs record);

    int updateByPrimaryKey(AboutUs record);
}