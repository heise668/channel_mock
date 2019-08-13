package com.qpay.channel.test.mock.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;




/*
 * 得到加签加密的 秘钥
 * 表 t_rsakey:id,rsa_key,rsa_type(0-私钥，1-公钥)
 */

@Service
public class Rsa_DBAPI {

	 @Autowired
	 private JdbcTemplate jdbctemplate;
	 
	 //根据id来获得秘钥
	 public Map RsaKey_id(String id)
	 {
		 String sql="select rsa_key,rsa_type from channelmock.t_rsakey where id=(?)";
		 Map resultMap=new HashMap();
		 resultMap=jdbctemplate.queryForMap(sql, id);		 		 
		 return resultMap;
		 
	 }
	 
	 //根据type来获得秘钥
	 public Map RsaKey_RsaType(String RsaType)
	 {
		 String sql="select rsa_key from channelmock.t_rsakey where rsa_type=(?)";
		 Map resultMap=new HashMap();
		 resultMap=jdbctemplate.queryForMap(sql, RsaType);		 		 
		 return resultMap;
		 
	 }
	
	 
}
