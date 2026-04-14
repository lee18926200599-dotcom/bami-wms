package com.basedata.server.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basedata.server.entity.DictionaryItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictionaryItemMapper extends BaseMapper<DictionaryItem> {

    int updateById(DictionaryItem dictionaryItem);

}
