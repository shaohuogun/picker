package org.shaohuogun.picker.result.dao;

import org.apache.ibatis.annotations.Mapper;
import org.shaohuogun.picker.result.model.Result;

@Mapper
public interface ResultMapper {

	void insert(Result result);
	
	Result selectById(String id);
	
	Result selectBySent(Character sent);
	
	void update(Result result);
	
}
