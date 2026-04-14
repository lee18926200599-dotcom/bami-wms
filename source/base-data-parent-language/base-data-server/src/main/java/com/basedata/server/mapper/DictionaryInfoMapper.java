package com.basedata.server.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basedata.server.entity.DictionaryInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictionaryInfoMapper extends BaseMapper<DictionaryInfo> {

    int updateById(DictionaryInfo dictionaryInfo);
}
