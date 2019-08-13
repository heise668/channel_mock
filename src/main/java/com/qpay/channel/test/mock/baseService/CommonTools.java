package com.qpay.channel.test.mock.baseService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ProxySelector;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


//import com.meidusa.fastjson.JSONObject;
import com.alibaba.fastjson.*;

@SuppressWarnings("deprecation")
public class CommonTools {

	private static String EQUALS = "equals";
	private static String EQUALSIGNORECASE = "equalsIgnoreCase";
	private static String ENDSWITH = "endsWith";
	private static String STARTSWITH = "startsWith";
	private static String CONTAINS = "contains";
	private static final String UID_FORMAT = "00000";
	DecimalFormat df = new DecimalFormat(UID_FORMAT);
	
	private HashMap<String, String> header = new HashMap<String, String>();

	public static final String ENCODE = "UTF-8";
	
	private FileInputStream fis = null;
	private InputStreamReader isw = null;
	private BufferedReader br = null;

	static AtomicInteger seq = new AtomicInteger(0);
	
	private static final String SEQ_FORMAT = "0000000000";
	private static final String IP_FORMAT = "000";
	DecimalFormat seqdf = new DecimalFormat(SEQ_FORMAT);
	DecimalFormat ipdf = new DecimalFormat(IP_FORMAT);

	/*发送post请求*
	 * url--请求地址，request-请求报文
	 * return returnString
	 */
	public String post(String url, String request) {
		
		String returnString = "";
		
		@SuppressWarnings("resource")
		DefaultHttpClient client = new DefaultHttpClient();
		ProxySelectorRoutePlanner routePlanner = new ProxySelectorRoutePlanner(
				client.getConnectionManager().getSchemeRegistry(),
				ProxySelector.getDefault());
		client.setRoutePlanner(routePlanner);
		final HttpPost method = new HttpPost(url);
		method.setHeader("Content-Type", "application/x-www-form-urlencoded");

		if (!header.isEmpty()) {
			Set<String> keysSet = header.keySet();
			Iterator<String> iterator = keysSet.iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = header.get(key);
				method.addHeader(key, value);
			}
		}


		try {
			if (!request.equals("")) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				String[] values = request.split("&");

				for (String value : values) {
					String[] keyValue = value.split("=");
					NameValuePair pair = null;
					if (keyValue.length != 2) {
						pair = new BasicNameValuePair(keyValue[0], "");
					} else {
						pair = new BasicNameValuePair(keyValue[0], keyValue[1]);
					}
					params.add(pair);
				}

				// StringEntity reqEntity = new StringEntity(request, "UTF-8");
				method.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}
			// 加上如下语句,是防止生产环境的https证书的验证
			SSLSocketFactory.getSocketFactory().setHostnameVerifier(
					new AllowAllHostnameVerifier());
			HttpResponse response = client.execute(method);
			HttpEntity responseEntity = response.getEntity();

			if (responseEntity != null) {
				returnString = EntityUtils.toString(responseEntity);
			}
			EntityUtils.consume(responseEntity);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return (returnString);
	}

	public String postJsonContent(String url, String request)
			throws ClientProtocolException, IOException {
		String returnString = "";
		@SuppressWarnings({ "resource" })
		HttpClient client = new DefaultHttpClient();

		HttpPost method = new HttpPost(url);
		method.addHeader("Content-Type", "application/json;charset=UTF-8");

		StringEntity reqEntity = new StringEntity(request, "UTF-8");
		method.setEntity(reqEntity);
		HttpResponse response = client.execute(method);
		HttpEntity responseEntity = response.getEntity();
		if (responseEntity != null) {
			returnString = EntityUtils.toString(responseEntity, "UTF-8");

		}
		return (returnString);
	}

	public String get(String url, String request) {
		String returnString = "";
		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();

		HttpGet method = new HttpGet(url + "?" + request);

		if (!header.isEmpty()) {
			Set<String> keysSet = header.keySet();
			Iterator<String> iterator = keysSet.iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = header.get(key);
				method.addHeader(key, value);
			}
		}


		try {
			HttpResponse response = client.execute(method);
			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {

			}
			HttpEntity responseEntity = response.getEntity();

			if (responseEntity != null) {
				returnString = EntityUtils.toString(responseEntity);
			}
			EntityUtils.consume(responseEntity);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return (returnString);
	}

	// 返回系统当前时间, 格式: yyyyMMddHHmmss
	public String returnSystemDate() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String systemCurrDate = df.format(date);
		return "" + systemCurrDate;
	}

	// 按照加密顺序排序, 并且按照形参separator来分割, 注意入参需要list类型[...,...,...]
	public String sortStringWithSeparator(String[] inputString, String separator) {
		Arrays.sort(inputString);
		StringBuilder outputString = new StringBuilder("");
		for (int i = 0; i < inputString.length; i++) {
			outputString.append(inputString[i]);
			outputString.append(separator);
		}
		String request = outputString.substring(0, outputString.length() - 1);
		return request.toString();
	}

	// 字符串中删除特定字符
	public String removeFromString(String inputString, String orgSeparator,
			String finalSeparator, String type, String key) {
		String[] arrayString = inputString.split(orgSeparator);
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < arrayString.length; i++) {

			if (CommonTools.EQUALS.equalsIgnoreCase(type)) {
				if (!arrayString[i].equals(key)) {
					list.add(arrayString[i]);
				}
			}

			if (CommonTools.EQUALSIGNORECASE.equalsIgnoreCase(type)) {
				if (!arrayString[i].equalsIgnoreCase(key)) {
					list.add(arrayString[i]);
				}
			}

			if (CommonTools.ENDSWITH.equalsIgnoreCase(type)) {
				if (!arrayString[i].endsWith(key)) {
					list.add(arrayString[i]);
				}
			}

			if (CommonTools.STARTSWITH.equalsIgnoreCase(type)) {
				if (!arrayString[i].startsWith(key)) {
					list.add(arrayString[i]);
				}
			}

			if (CommonTools.CONTAINS.equalsIgnoreCase(type)) {
				if (!arrayString[i].contains(key)) {
					list.add(arrayString[i]);
				}
			}
		}

		StringBuilder outputString = new StringBuilder("");
		for (int i = 0; i < list.size(); i++) {
			outputString.append(list.get(i));
			outputString.append(finalSeparator);
		}
		String finalString = outputString.substring(0,
				outputString.length() - 1);

		return finalString;

	}

	// URL ENCODE
	public String textEncode(String text, String codeType) throws IOException {
		text = java.net.URLEncoder.encode(text, codeType);
		return text;
	}

	// RUL DECODE
	public String textDncode(String text, String codeType) throws IOException {
		text = java.net.URLDecoder.decode(text, codeType);
		return text;
	}

	
	// 返回随机数, 入参length为随机数长度
	/**
	 * 
	 * @param length
	 *            随机数长度
	 * @return
	 */
	public String returnRandomNumber(int length) {
		String len = "1";
		for (int i = 0; i <= length + 1; i++) {
			if (i > 1) {
				len = len + "0";
			}
		}
		int value = (int) (Math.random() * Integer.parseInt(len));
		return Integer.toString(value);
	}

	
	public String getRanduid() {
		int M = 1;
		int N = 1000;
		int R = (int) (M + Math.random() * (N - M));
		return df.format(R);
	}
	
	// 压力环境的5W个测试账户随机返回其中之一UID
	public String getRandomUid() {
		int M = 1;
		int N = 50000;
		int R = (int) (M + Math.random() * (N - M));
		return "test" + df.format(R);
	}

	// 压力环境的5W个测试账户随机返回其中之一MemberId
	public String getRandomMemberId() {
		int M = 18749;
		int N = 68748;
		int R = (int) (M + Math.random() * (N - M));
		return "1000055" + R;
	}

	// 压力环境5W个测试账户随机返回其中一AccountNo
	public String getRandomAccountNo() {
		return "2001002001" + getRandomMemberId() + "00001";
	}

	// 根据memberId返回AccountNo
	/**
	 * 
	 * @param memberId
	 *            要返回账户号的MemberId
	 * @return
	 */
	public String getAccountNo(String memberId) {
		return "2001002001" + memberId + "00001";
	}

	// 待删除, 杨晨叶以前使用的1W个账户, 请别再使用此方法, 我留着纯粹是为了方便寻找他的账户以备不时之需
	public String getRandomYangMemberId() {
		int M = 13584;
		int N = 13593;
		int R = (int) (M + Math.random() * (N - M));
		return "1000048" + R;
	}

	// 获取时间
	/**
	 * 
	 * @param formerSeconds
	 *            时间, 多久前? 单位: 秒
	 * @param dataFormat
	 *            时间格式
	 * @return
	 */
	public String getTime(int formerSeconds, String dataFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)
				- formerSeconds);
		if (dataFormat.equals("nop")) {
			return "" + calendar.getTime();
		} else {
			SimpleDateFormat df = new SimpleDateFormat(dataFormat);
			return df.format(calendar.getTime());
		}
	}

	// 获取IP最后一段, 例如10.65.111.13则获取13
	public String getIp() throws UnknownHostException {
		InetAddress localhost = InetAddress.getLocalHost();
		String hostAddress = localhost.getHostAddress();
		String[] addressSplit = hostAddress.split("\\.");
		String ip = "";
		if (addressSplit.length == 4) {
			ip = addressSplit[3];
		}
		return ip;
	}

	// 发送GET请求
	public String get(String url) {
		String returnString = "";
		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();
		HttpGet method = new HttpGet(url);
		try {
			HttpResponse response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
				returnString = "ERROR SC :"
						+ response.getStatusLine().getStatusCode() + "返回： "
						+ response + EntityUtils.toString(entity);
			} else {
				returnString = EntityUtils.toString(entity);
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (returnString);
	}

	// 通过KEY值返回JSON的VALUE
	/**
	 * @param obj
	 *            JSON
	 * @param key
	 *            要查找的KEY值
	 * @return VALUE值
	 */
	public String getValueFromJSON(String obj, String key) {
//		JSONObject jsonObject = com.meidusa.fastjson.JSONObject.parseObject(obj, JSONObject.class);
		JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(obj,JSONObject.class);
		String Value = jsonObject.get(key).toString();
		return Value;
	}

	public void CSVFileUtil() throws FileNotFoundException,
			UnsupportedEncodingException {
		fis = new FileInputStream("/Users/sunjie/Desktop/testDate.csv");
		isw = new InputStreamReader(fis, ENCODE);
		br = new BufferedReader(isw);
	}

	public void readLine() throws IOException {
		String instring;
		ArrayList<String> idList = new ArrayList<String>();
		br.readLine(); // 跳过第一行
		try {
			while ((instring = br.readLine().trim()) != null) {
				if (0 != instring.length()) {
					idList.add(instring);
				}
			}
		} catch (Exception e) {

		}
		System.out.println(idList.get(getRandomFromRange(0, 32508)));
		br.close();
		isw.close();
		fis.close();
	}

	public int getRandomFromRange(int start, int end) {
		int M = start;
		int N = end;
		int R = (int) (M + Math.random() * (N - M));
		return R;
	}

	public String returnRandomLetter() {
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		return letters[(int) (Math.random() * 26)];
	}

	public String returnUniqueSeq() throws NumberFormatException,
			UnknownHostException {
		int no = seq.incrementAndGet();
		return returnRandomLetter() + returnRandomLetter()
				+ returnRandomLetter() + ipdf.format(Integer.parseInt(getIp()))
				+ returnSystemDate() + seqdf.format(no);
	}

	// 调试程序
	public static void main(String[] args) throws IOException {
		CommonTools test = new CommonTools();
		String msg="phone_no=122211111,cert_no=1234567890,card_no=89990001,err_msg=ok,request_no=125,name=name1,err_code=0000";
		String[] splitString = msg.toString().split(",");
		System.out.println("splitString===="+splitString.length);		
		String sortString = test.sortStringWithSeparator(splitString, "&");

		System.out.println(sortString);
	}

}
