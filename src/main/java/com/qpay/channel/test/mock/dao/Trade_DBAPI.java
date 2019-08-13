package com.qpay.channel.test.mock.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.qpay.channel.test.mock.impl.Trade_Key;


/*
 * 交易记录表
 *T_trade:request_no(交易订单号),trade_type(交易类型),card_no,phone_no,name,cert_no,amount,
 *		result_id(交易结果码id),request_time,voucher_no(记录凭证码),merchant_id(商户号) 
 */

@Service
public class Trade_DBAPI {

	 @Autowired
	 private JdbcTemplate jdbctemplate;

	 //更新交易结果码Id（通过result_id 映射错误码表)
	 public int resultidUpdate(String result_id,String request_no){
		 String tradeSql="update channelmock.t_trade set result_id=(?) where request_no=(?)";
		 int t2=jdbctemplate.update(tradeSql,result_id,request_no);
		 return t2;
	 }
	 
	 //插入错误码
	 public int errSave(String id,String result_id,String err_code,String err_msg){
		 String tradeSql="insert into channelmock.t_return_code values(?,?,?,?)";
		 int t1=jdbctemplate.update(tradeSql,id,result_id,err_code,err_msg);
		 return t1;
	 }
	 
	 //根据订单号查询交易
	 public Map getTradeList(String request_no)
	 {
		 String sql="select * from channelmock.t_trade t "
		 		+ "where t.request_no=(?)";
		 Map resultMap=new HashMap();
		 resultMap=jdbctemplate.queryForMap(sql, request_no);
		 return resultMap;
		 
	 }
	 
	 
	 
	 /*
	  * ----------------------------
	  */
	 
	 //保存交易记录：Trade_TableRecord类
	 public int Trade_SaveAllParam(Trade_Key tradeRecord){
		 String tradeSql="insert into channelmock.t_trade"
		 		+ "(request_no,trade_type,card_no,phone_no,name,cert_no,amount,"
		 		+ "result_id,request_time,voucher_no,merchant_id,request_content) "
		 		+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		 int t1=jdbctemplate.update(tradeSql,
				 tradeRecord.getRequest_no(),
				 tradeRecord.getTrade_type(),
				 tradeRecord.getCard_no(),
				 tradeRecord.getPhone_no(),
				 tradeRecord.getName(),
				 tradeRecord.getCert_no(),
				 tradeRecord.getAmount(),
				 tradeRecord.getResult_id(),
				 tradeRecord.getRequest_time(),
				 tradeRecord.getVoucher_no(),
				 tradeRecord.getMerchant_id(),
				 tradeRecord.getRequest_content());
		 return t1;
	 }
	 
	 
	 //保存交易记录：Trade_TableRecord---主要字段
	 public int Trade_SaveMainParam(String request_no,String trade_type,String result_id,
			 String voucher_no,String request_content ){
		 String tradeSql="insert into channelmock.t_trade"
		 		+ "(request_no,trade_type,result_id,voucher_no,request_content) "
		 		+ "values(?,?,?,?,?)";
		 int t1=jdbctemplate.update(tradeSql,
				 request_no,trade_type,result_id,voucher_no,request_content);
		 return t1;
	 }
	 
	 
	 
	 //根据订单号查询交易
	 public Map getTrade_RequestNo(String request_no)
	 {
		 String sql="select * from channelmock.t_trade t "
		 		+ "where t.request_no=(?)";
		 Map resultMap=new HashMap();
		 resultMap=jdbctemplate.queryForMap(sql, request_no);
		 return resultMap;
		 
	 }
	 
	 //根据凭证号查询交易
	 public Map getTrade_Voucher(String voucher_no)
	 {
		 String sql="select * from channelmock.t_trade t "
		 		+ "where t.voucher_no=(?)";
		 Map resultMap=new HashMap();
		 resultMap=jdbctemplate.queryForMap(sql, voucher_no);
		 return resultMap;		 
	 }
	 
	 
	 //更新交易结果码Id（通过result_id 映射错误码表)
	 public int upDate_resultId(String result_id,String request_no){
		 String tradeSql="update channelmock.t_trade set result_id=(?) where request_no=(?)";
		 int t2=jdbctemplate.update(tradeSql,result_id,request_no);
		 return t2;
	 }
	 
}
