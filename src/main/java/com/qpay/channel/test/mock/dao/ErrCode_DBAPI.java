package com.qpay.channel.test.mock.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;




/*
 * 查询错误码
 * 表：channelmock.t_error_code -- result_id(结果码id),err_code,err_msg,channel_code(渠道号)
 */

@Service
public class ErrCode_DBAPI {

	 @Autowired
	 private JdbcTemplate jdbctemplate;
	 
	 
	 //根据result_id 查询错误码
	 public Map getErr_ResultId(String result_id)
	 {
		 String sql="select result_id,err_code,err_msg from channelmock.t_error_code where result_id=(?)";
		 Map resultMap=new HashMap();
		 try{
			 resultMap=jdbctemplate.queryForMap(sql, result_id);
			 if(resultMap!=null && resultMap.size()>10)
			 {
				 return resultMap;
			 }
		 } catch (EmptyResultDataAccessException e){
			 return null;
		 }
		 
		 return resultMap;
		 
	 }
	 
	 
	 
	 //根据result_id+channel_code 查询错误码
	 public Map getErr_ResultId2(String result_id,String channel_code)
	 {
		 String sql="select result_id,err_code,err_msg from channelmock.t_error_code "
		 		+ "where result_id=(?) and channel_code=(?)";
		 Map resultMap=new HashMap();
		 resultMap=jdbctemplate.queryForMap(sql, result_id,channel_code);
		 return resultMap;		 
	 }
	 

	 //根据订单号查询错误码
	 public Map getErr_RequestNo(String request_no)
	 {
		 String sql="select r.result_id,r.err_code,r.err_msg from channelmock.t_error_code r "
		 		+ "left join channelmock.t_trade t on r.result_id=t.result_id "
		 		+ "where t.request_no=(?)";
		 Map resultMap=new HashMap();
		 resultMap=jdbctemplate.queryForMap(sql, request_no);
		 return resultMap;		 
	 }
	 
	 //插入错误码:id,result_id,err_code,err_msg
	 public int saveErr(String result_id,String err_code,String err_msg){
		 String tradeSql="insert into channelmock.t_error_code values(?,?,?,?,null)";
		 int t1=jdbctemplate.update(tradeSql,result_id,err_code,err_msg);
		 return t1;
	 }
	 
	 //插入错误码:id,result_id,err_code,err_msg,channel_code
	 public int saveErr_All(String result_id,String err_code,String err_msg,String channel_code){
		 String tradeSql="insert into channelmock.t_error_code values(?,?,?,?)";
		 int t1=jdbctemplate.update(tradeSql,result_id,err_code,err_msg,channel_code);
		 return t1;
	 }
	 
	 
	 public List getList(String result_id){		
		 String sql="select result_id,err_code,err_msg from channelmock.t_error_code where result_id=(?)";
		List param = new ArrayList();
		 
		  param =jdbctemplate.queryForList(sql,result_id);
		 return  param;
	 	}	 
	 
}
