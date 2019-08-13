package com.qpay.channel.test.mock.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qpay.channel.test.mock.dao.ErrCode_DBAPI;
import com.qpay.channel.test.mock.dao.Rsa_DBAPI;
import com.qpay.channel.test.mock.dao.Trade_DBAPI;
import com.qpay.channel.test.mock.impl.TradeResultId;
import com.qpay.channel.test.mock.impl.Trade_Key;

import com.qpay.channel.test.mock.services.UPOP10101_ReturnMsg;

@RestController
@RequestMapping(value = "/qrc/api")

public class UPOP10102_TestMock {

	@Autowired
	private Trade_DBAPI tradeDB;

	@Autowired
	private ErrCode_DBAPI errCodeDB;

	@Autowired
	private Rsa_DBAPI rsaDB;
	
	private int resultlen=2;

	/*
	 * merBackTransReq.do?acqCode=49430000&certId=68759529225&currencyCode=156&
	 * orderNo=RF1010133020627000091260&orderTime=20190627205802&origOrderNo=
	 * FI1010132010627000091259&origOrderTime=20190627205753&reqType=0150000903&
	 * signature=gvEtiUmVcp2PJp5d2CFNmZWe5TRsq4q9uekN1EYN%
	 * 2BVQCSTfuxQkBAAn7ZssoE8vaqnLPzHRpk%2Bz%2FRm6zg9gojmVb6AwUtvxRW%
	 * 2FSUsQDgCXw3ywt81oe7jKSiah129J299QG%2BcwjS0f4n23JTQ60UzNYcYs3t%2FN2wYMM7L
	 * %2FwLHurEthlOIVwY%2BO47nVmvEtMM%
	 * 2BEzMgwVY1o7HEQV1FSY04oqaBuJZOYERC1JPUjilQzbUYnDdn5Z3qEmJJmRSIgOBj0vbpRShzeQ6SwGLrEoPVWemvbDLGURnKd2wgroTD41djfGpGcX9GQCLSsInU
	 * %2FVDzgPgUiIs6LmbOz9gn9cz9Q%3D%3D&txnAmt=1&version=1.0.0
	 */

	/*
	 * 付款方
	 */
	
	@RequestMapping("/issBackTransReq.do")
	public String merBackTransReq(HttpServletRequest request) {

		String trade_type = request.getParameter("reqType");

		// 组装返回报文
		HashMap returnMap = new HashMap();
		// 得到错误码
		Map errMap = new HashMap();

		// 得到交易信息
		Map tradeMap = new HashMap();

	
		/*
		 *  生成二维码  0510000903
		 */
		
		if (trade_type.equals("0510000903")) {
			Trade_Key tradeKey=new Trade_Key();
			TradeResultId resultId=new TradeResultId();
			
	        String Ordertime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());	        
	        String orderDate=new SimpleDateFormat("yyyyMMdd").format(new Date());
	        String qr_voucher="qr"+Ordertime;
	        
			tradeKey.setRequest_no(request.getParameter("orderNo"));
			tradeKey.setAmount("null");
			tradeKey.setMerchant_id(request.getParameter("acqCode"));
			tradeKey.setRequest_content(request.getParameter("payeeInfo"));
			tradeKey.setResult_id(resultId.resultIdByRequestNo(tradeKey.getRequest_no(), resultlen));
			
			tradeKey.setVoucher_no(qr_voucher);
			tradeKey.setCard_no(request.getParameter("orderType"));  //订单类型  
			tradeKey.setCert_no(request.getParameter("certId")); //证书ID
			tradeKey.setName("null");
			tradeKey.setPhone_no(request.getParameter("encryptCertId"));
			tradeKey.setTrade_type(trade_type);
			tradeKey.setRequest_time(request.getParameter("orderTime"));

			int t1=tradeDB.Trade_SaveAllParam(tradeKey);
			
			String qrCode="https://qr.95516.com/00010000/"+tradeKey.getVoucher_no();
			
			returnMap.put("version", request.getParameter("version"));
			returnMap.put("certId",request.getParameter("certId"));
			returnMap.put("reqType",tradeKey.getTrade_type());
			returnMap.put("acqCode", tradeKey.getMerchant_id());
			
			returnMap.put("qrCode", qrCode);
			returnMap.put("qrValidTime", "86400");
			returnMap.put("limitCount", "1");
			returnMap.put("respCode", "00");
			returnMap.put("respMsg", "成功[0000000]");
		
		}
			
		/*
		 *  查询订单 0120000903
		 *  如果成功返回的 trxNo就是支付流水号
		 */
		
		if (trade_type.equals("0120000903")) {
			Trade_Key tradeKey=new Trade_Key();
			TradeResultId resultId=new TradeResultId();
			
		    String Ordertime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());	        
		    String orderDate=new SimpleDateFormat("yyyyMMdd").format(new Date());


			String qrCode=request.getParameter("qrCode");
			String voucher=qrCode.substring(qrCode.length()-21, qrCode.length());
			
			
			//二维码查询时，生成支付流水号
			tradeMap=tradeDB.getTrade_Voucher(voucher);
			
			String trxNo=voucher;
			String voucher_no=tradeMap.get("request_no").toString();
			String result_id=resultId.resultIdByRequestNo(voucher_no, resultlen);
			String request_content="二维码查询生成支付流水号";
			
			
			int t1=tradeDB.Trade_SaveMainParam(trxNo, 
					trade_type, 
					result_id, 
					voucher_no, 
					request_content);
			
			returnMap.put("version", request.getParameter("version"));
			returnMap.put("certId",request.getParameter("certId"));
			returnMap.put("reqType",request.getParameter("reqType"));
			returnMap.put("acqCode", request.getParameter("issCode"));

			returnMap.put("respCode","00");
			returnMap.put("respMsg","成功[0000000]");

			//成功时返回下面字段
			returnMap.put("txnNo",trxNo);
			returnMap.put("orderType",tradeMap.get("card_no").toString());
			returnMap.put("paymentValidTime","86398");
			returnMap.put("payeeInfo",tradeMap.get("request_content").toString());
			returnMap.put("acqCode",tradeMap.get("merchant_id").toString());
			returnMap.put("currencyCode","156");
			returnMap.put("encryptCertId",tradeMap.get("phone_no").toString());
			returnMap.put("invoiceSt","0");
			returnMap.put("orderNo",tradeMap.get("request_no").toString());

		}
		
		
		/*
		 * 0130000903--DB 支付交易	
		 * 包装二维码快捷--先生成二维码，然后自动跳转支付
		 */
		if (trade_type.equals("0130000903")) {

			
			Trade_Key tradeKey=new Trade_Key();
			TradeResultId resultId=new TradeResultId();
			
	        String Ordertime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());	        
	        String orderDate=new SimpleDateFormat("yyyyMMdd").format(new Date());

			String request_no=request.getParameter("txnNo");  //支付流水
			
			tradeMap=tradeDB.getTrade_RequestNo(request_no);  //根据流水号查询交易信息
			String orderNo=tradeMap.get("voucher_no").toString();	//原交易订单号		
			String result_id=tradeMap.get("result_id").toString();
			
			errMap = errCodeDB.getErr_ResultId(result_id);
			String respCode = errMap.get("err_code").toString();
			String respMsg = errMap.get("err_msg").toString();
			
			tradeMap.clear();
			tradeMap=tradeDB.getTrade_RequestNo(orderNo);  //根据orderNo查询原交易信息
			
			
			returnMap.put("version", request.getParameter("version"));
			returnMap.put("certId",request.getParameter("certId"));
			returnMap.put("reqType",request.getParameter("reqType"));
			returnMap.put("issCode", request.getParameter("issCode"));
			returnMap.put("txnNo", request.getParameter("trxNo"));			
			returnMap.put("txnAmt",request.getParameter("txnAmt"));
			returnMap.put("currencyCode", request.getParameter("currencyCode"));
			returnMap.put("payerInfo", tradeMap.get("request_content").toString());
			returnMap.put("voucherNum", Ordertime);
			returnMap.put("settleKey", "49430000   00049992   6153270704110636");
			returnMap.put("settleDate",orderDate);
			returnMap.put("comInfo","e0YwPTAyMDAmRjI1PTAwJkYzPTAwMDAwMCZGMzc9NzA0MDAwMDkxOTg4JkY2MD0wMzAwMDAwMDAwMDA3MDAxMDAwMDAwMDAwMjcwMDEwMDB9");
			returnMap.put("respCode", respCode);
			returnMap.put("respMsg", respMsg);
			returnMap.put("orderNo", orderNo);		
		}
		
		// 退款交易
		if (trade_type.equals("0150000903")) {
	        String Ordertime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());	        
	        String orderDate=new SimpleDateFormat("MMdd").format(new Date());
			Trade_Key tradeKey=new Trade_Key();
			TradeResultId resultId=new TradeResultId();

			tradeKey.setRequest_no(request.getParameter("orderNo"));
			tradeKey.setAmount(request.getParameter("txnAmt"));
			tradeKey.setMerchant_id(request.getParameter("acqCode"));
			tradeKey.setRequest_content("退款交易");
			String result_id = resultId.resultIdByRequestNo(tradeKey.getRequest_no(), resultlen);
			tradeKey.setResult_id(result_id);
			
			tradeKey.setVoucher_no(request.getParameter("origOrderNo"));
			tradeKey.setCard_no("null");  //
			tradeKey.setCert_no(request.getParameter("certId")); //证书ID
			tradeKey.setName("null");
			tradeKey.setPhone_no("null");
			tradeKey.setTrade_type(trade_type);
			tradeKey.setRequest_time(request.getParameter("orderTime"));			
			
			int t1=tradeDB.Trade_SaveAllParam(tradeKey);  //存储退款订单
			
			errMap = errCodeDB.getErr_ResultId(result_id);
			String respCode = errMap.get("err_code").toString();
			String respMsg = errMap.get("err_msg").toString();

			returnMap.put("acqCode", tradeKey.getMerchant_id());
			returnMap.put("comInfo",
					"e0YwPTAyMjAmRjI1PTAwJkYzPTIwMDAwMCZGMzc9NjI3MDAwMDkxMjE4JkY2MD0wMjcwMDAwMDAwMDA3MDAxMDAwMDAwMDAwMjcwMDF9");
			returnMap.put("respCode", respCode);
			returnMap.put("respMsg", respMsg);
			returnMap.put("settleDate", orderDate);
			returnMap.put("settleKey", "49430000   00049992   3694510627175607");
			returnMap.put("certId", tradeKey.getCert_no());
			returnMap.put("reqType", trade_type);
			returnMap.put("version", "1.0.0");

			

		}
		// 退款交易查询
		else if (trade_type.equals("0540000903")) {

	        String Ordertime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());	        
	        String orderDate=new SimpleDateFormat("yyyyMMdd").format(new Date());
			
			String request_no=request.getParameter("orderNo");
			tradeMap=tradeDB.getTradeList(request_no);
			
			errMap = errCodeDB.getErr_RequestNo(request_no);
			String respCode = errMap.get("err_code").toString();
			String respMsg = errMap.get("err_msg").toString();

			returnMap.put("version", "1.0.0");
			returnMap.put("acqCode", tradeMap.get("name").toString());
			returnMap.put("comInfo",
					"e0YwPTAyMjAmRjI1PTAwJkYzPTIwMDAwMCZGMzc9NjI3MDAwMDkxMjE4JkY2MD0wMjcwMDAwMDAwMDA3MDAxMDAwMDAwMDAwMjcwMDF9");
			returnMap.put("respCode", "00");
			returnMap.put("respMsg", "成功[0000000]");
			returnMap.put("orderNo", tradeMap.get("request_no").toString());
			returnMap.put("payerInfo", "HMignYrhq6utMcIq9aet2CS2nS6re/el2IDMzOm62atmLIncajW1TSezTAFs8BT+6v0yBsepnmNwBtGQi2Y14BLT7TDewmlWuh/ogvc979V/7u157XT7GKkhlOnP0WHSIjelWKyRQe5ZRmAeYYbpQOfhepHawtQCe2OygLKmS899CDla0/CFUsns1IICMbhwKqcFKlKRqcVTZmxI6HlBG6MVufJN7wXSdG8oRxVhJflBkRQIK+MDvN1Ki+JCBhdolKtcoVLuUfYSPYEzErE9vtKSxCbBITmPdou8C4KdAFPYOvlXS5CoAnT6UEL1CmnUqW3w4H1VWRW921Oz6evtSg==");	
			
			
			returnMap.put("origRespCode", respCode);
			returnMap.put("origRespMsg", respMsg);
			returnMap.put("settleDate", "0627");
			returnMap.put("settleKey", "49430000   00049992   3694510627175607");
			returnMap.put("reqType", trade_type);
			returnMap.put("certId", tradeMap.get("cert_no").toString());
			returnMap.put("currencyCode", "156");
			returnMap.put("encryptCertId", "68759529225");
			returnMap.put("orderTime", orderDate);
			returnMap.put("txnAmt", tradeMap.get("amount").toString());
			returnMap.put("voucherNum", Ordertime);
			
		}

		UPOP10101_ReturnMsg authReturn = new UPOP10101_ReturnMsg();

		// 得到密钥
		Map rsaMap = new HashMap();
		rsaMap = rsaDB.RsaKey_RsaType("0");  //0-私钥，1-公钥
		String rsa_key = rsaMap.get("rsa_key").toString();
		String rsa_type = rsaMap.get("rsa_type").toString();

		String authMsg = authReturn.returnMsg(returnMap);
		System.out.println("返回报文===" + authMsg);

		String retunMsg = authReturn.RsaMsg(authMsg, rsa_key, rsa_type);
		return retunMsg;
	}

}
