package com.qpay.channel.test.mock.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class xmltomap {

	
	/**  
     * Map转换成Xml  
     * @param map  
     * @return  
     */  
    public static String map2Xmlstring(Map<String,Object> map){  
        StringBuffer sb = new StringBuffer("");  
        sb.append("<xml>");  
          
        Set<String> set = map.keySet();  
        for(Iterator<String> it=set.iterator(); it.hasNext();){  
            String key = it.next();  
            Object value = map.get(key);  
            sb.append("<").append(key).append(">");  
            sb.append(value);  
            sb.append("</").append(key).append(">");  
        }  
        sb.append("</xml>");  
        return sb.toString();  
    }  
    
    /**  
     * Xml string转换成Map  
     * @param xmlStr  
     * @return  
     */  
    public static Map<String,Object> xmlString2Map(String xmlStr){  
        Map<String,Object> map = new HashMap<String,Object>();  
        Document doc;  
        try {  
            doc = DocumentHelper.parseText(xmlStr);  
            Element el = doc.getRootElement();  
            map = recGetXmlElementValue(el,map);  
        } catch (DocumentException e) {  
            e.printStackTrace();  
        }  
        return map;  
    }  
    
    
    /**  
     * 循环解析xml  
     * @param ele  
     * @param map  
     * @return  
     */  
    @SuppressWarnings({ "unchecked" })  
    private static Map<String, Object> recGetXmlElementValue(Element ele, Map<String, Object> map){  
        List<Element> eleList = ele.elements();  
        if (eleList.size() == 0) {  
            map.put(ele.getName(), ele.getTextTrim());  
            return map;  
        } else {  
            for (Iterator<Element> iter = eleList.iterator(); iter.hasNext();) {  
                Element innerEle = iter.next();  
                recGetXmlElementValue(innerEle, map);  
            }  
            return map;  
        }  
    }  
	
	   //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
    
    public static void main(String[] args) {  
        Map<String,Object> map = new HashMap<String,Object>();  
        map.put("id1", "1111111");  
        map.put("id2", "2222222");  
        map.put("id3", "3333333");  
        System.out.println(map2Xmlstring(map));  
        
        String xmlStr = "<xml><id2><it1>it1</it1><it2>it2</it2></id2><id1>1111111</id1><id3>3333333</id3></xml>";  
    	String xml="<Response><Head><TxCode>A00201</TxCode><TransSerialNumber>100000000005_000000000000T201809261111111537931471846</TransSerialNumber><MessageFrom>100000000005</MessageFrom></Head><Body><FreezeType>02</FreezeType><Balance>30</Balance><Currency>RMB</Currency><StartTime>20180926111111</StartTime><ExpireTime>20180926145246</ExpireTime><SubjectType>01</SubjectType><CaseNumber>T0100002018080797157</CaseNumber><CaseType>0000</CaseType><OnlinePayCompanyID>Z09111000016</OnlinePayCompanyID><OnlinePayCompanyName>北京雅酷时空信息交换技术有限公司</OnlinePayCompanyName><DataType>01</DataType><Data>6225827891231566</Data><Reason>test</Reason><Remark>test</Remark><ApplicationTime>20180926111111</ApplicationTime><ApplicationOrgID>010000</ApplicationOrgID><ApplicationOrgName>公安部</ApplicationOrgName><OperatorIDType>08</OperatorIDType><OperatorIDNumber>1111</OperatorIDNumber><InvestigatorIDType>08</InvestigatorIDType><InvestigatorIDNumber>2222</InvestigatorIDNumber><InvestigatorName>刘文</InvestigatorName><ApplicationID>0201790686891021T0100002018080791479</ApplicationID><OperatorName>刘文</OperatorName><OperatorPhoneNumber>13100000000</OperatorPhoneNumber></Body></Response>";
    	
    	Map<String, Object> testmap=new HashMap<String,Object>();  
    	testmap=xmlString2Map(xml);
    	String postbuf="";
    	
        System.out.println(testmap);  

    	Iterator iter = testmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString();
			Object val = entry.getValue();
			
			
			System.out.println(toLowerCaseFirstOne(key)+"==="+val);
		}
    	
    }
}
