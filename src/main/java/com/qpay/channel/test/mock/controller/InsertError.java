package com.qpay.channel.test.mock.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qpay.channel.test.mock.baseService.CommonTools;
import com.qpay.channel.test.mock.dao.ErrCode_DBAPI;
import com.qpay.channel.test.mock.dao.Rsa_DBAPI;
import com.qpay.channel.test.mock.dao.Trade_DBAPI;
import com.qpay.channel.test.mock.impl.ErrCode_Key;

@RestController
@RequestMapping(value = "/error")

public class InsertError {

	@Autowired
	private Trade_DBAPI tradeDB;

	@Autowired
	private ErrCode_DBAPI errCodeDB;

	@Autowired
	private Rsa_DBAPI rsaDB;

	private CommonTools ct;

	/*
	 * insert.do?id=0001&result_id=0001&err_code=0001&err_msg=交易失败
	 */

	@RequestMapping("/insert.do")
	public String insert(HttpServletRequest request) {
		ct = new CommonTools();
		String result_id = request.getParameter("result_id");
		String err_code = request.getParameter("err_code");
		String err_msg = request.getParameter("err_msg");
		String channel_code = request.getParameter("channel_code");
		String result = "";

		System.out.println("err_msg==" + err_msg);

		int r = 0;
		try {
			// 使用JdbcTemplate访问数据库
			r = errCodeDB.saveErr_All(result_id, err_code, err_msg, channel_code);
		} catch (InvalidResultSetAccessException e) {
			throw new RuntimeException(e);
		} catch (DataAccessException e) {
			throw new RuntimeException(e);
		}

		if (r == 1) {
			System.out.println("交易数据插入成功==");
			result = "OK";
		} else
			result = "FAIL";
		return result;
	}

	@GetMapping("/save")
	public String errCode(Model model) {
		model.addAttribute("errCode", new ErrCode_Key());
		return "ErrCodePage";
	}

	@PostMapping("/save")
	public String saveSubmit(Model model, @ModelAttribute ErrCode_Key ErrCode_Key) throws IOException {
		model.addAttribute("err_code", ErrCode_Key.getErr_code());
		return "testbankresult1";
	}

	@RequestMapping("/find.do")
	public String findError(HttpServletRequest request) {
		String result_id = request.getParameter("result_id");
		Map resultMap = new HashMap();
		List result=new ArrayList();
		result=errCodeDB.getList(result_id);
		System.out.println(result.size());
		String r1=result.get(1).toString();
		r1=r1.replace("=", ":");
		System.out.println("r1=="+r1);
		
		
		
		String err = "111";
		try {
			resultMap = errCodeDB.getErr_ResultId(result_id);
			if (resultMap != null && resultMap.size() > 0) {
				err=resultMap.get("err_code").toString();
				return "err=="+err;
			}
			
			
			
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		return "err=="+err;

	}

}
