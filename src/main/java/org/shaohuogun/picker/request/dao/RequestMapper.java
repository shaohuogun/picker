package org.shaohuogun.picker.request.dao;

import org.apache.ibatis.annotations.Mapper;
import org.shaohuogun.picker.request.model.Request;

@Mapper
public interface RequestMapper {

	void insert(Request request);
	
	Request selectById(String id);
	
	Request selectByStatus(String status);
	
	void update(Request request);
	
}
