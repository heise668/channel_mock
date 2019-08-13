package com.qpay.channel.test.mock.services;
import com.alibaba.fastjson.JSONObject;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 1250052380@qq.com on 2015/5/19.
 */


public class XmlUtil {
    public static JSONObject xml2JSON(byte[] xml) throws JDOMException, IOException {
        JSONObject json = new JSONObject();
        InputStream is = new ByteArrayInputStream(xml);
        SAXBuilder sb = new SAXBuilder();
        org.jdom2.Document doc = sb.build(is);
        Element root = doc.getRootElement();
        json.put(root.getName(), iterateElement(root));
        return json;
    }

    private static JSONObject iterateElement(Element element) {
        List node = element.getChildren();
        Element et = null;
        JSONObject obj = new JSONObject();
        List list = null;
        for (int i = 0; i < node.size(); i++) {
            list = new LinkedList();
            et = (Element) node.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.getChildren().size() == 0)
                    continue;
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                obj.put(et.getName(), list);
            } else {
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                obj.put(et.getName(), list);
            }
        }
        return obj;
    }

    public static void main(String[] args) throws JDOMException, IOException {
//        String xml="<?xml version=\"1.0\" encoding=\"utf-8\" ?><MoBaoAccount MessageType=\"UserMobilePay\" PlatformID=\"b2ctest\"><OrderNo>M20150521084825</OrderNo><TradeAmt>5000.00</TradeAmt><Commission>0.5</Commission><UserID>zhuxiaolong</UserID><MerchID>zhuxiaolong1</MerchID><tradeType>0</tradeType><CustParam>123</CustParam> <NotifyUrl>http://mobaopay.com/callback.do</NotifyUrl><TradeSummary>订单</TradeSummary></MoBaoAccount>";
    	String xml="<Response><Head><TxCode>A00201</TxCode><TransSerialNumber>100000000005_000000000000T201809261111111537931471846</TransSerialNumber><MessageFrom>100000000005</MessageFrom></Head><Body><FreezeType>02</FreezeType><Balance>30</Balance><Currency>RMB</Currency><StartTime>20180926111111</StartTime><ExpireTime>20180926145246</ExpireTime><SubjectType>01</SubjectType><CaseNumber>T0100002018080797157</CaseNumber><CaseType>0000</CaseType><OnlinePayCompanyID>Z09111000016</OnlinePayCompanyID><OnlinePayCompanyName>北京雅酷时空信息交换技术有限公司</OnlinePayCompanyName><DataType>01</DataType><Data>6225827891231566</Data><Reason>test</Reason><Remark>test</Remark><ApplicationTime>20180926111111</ApplicationTime><ApplicationOrgID>010000</ApplicationOrgID><ApplicationOrgName>公安部</ApplicationOrgName><OperatorIDType>08</OperatorIDType><OperatorIDNumber>1111</OperatorIDNumber><InvestigatorIDType>08</InvestigatorIDType><InvestigatorIDNumber>2222</InvestigatorIDNumber><InvestigatorName>刘文</InvestigatorName><ApplicationID>0201790686891021T0100002018080791479</ApplicationID><OperatorName>刘文</OperatorName><OperatorPhoneNumber>13100000000</OperatorPhoneNumber></Body></Response>";

        JSONObject json=xml2JSON(xml.getBytes());
        System.out.println(json.toJSONString());
        System.out.println(json.toJSONString());
    }
}