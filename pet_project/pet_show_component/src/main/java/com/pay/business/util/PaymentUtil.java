package com.pay.business.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @ClassName: PaymentUtil 
 * @Description:加密工具类
 * @author zhoulibo
 * @date 2017年3月1日 下午5:12:11
 */
public class PaymentUtil {
	
	/**
	 * 参数加密
	 * @param map
	 * @param keyValue  商户密钥
	 * @return
	 * @throws Exception 
	 */
	public static String getSign(Map<String,Object> map,String keyValue) throws Exception{
		//密文
		StringBuffer buffer = new StringBuffer();
		buffer.append("keyValue=" + keyValue);
		
		List<String> keys = new ArrayList<String>(map.keySet());
		//排序
        Collections.sort(keys);
		//参数值拼接进行加密
		for(String key :keys){
			
			if(!"sign".equals(key)&&!"keyValue".equals(key)){
				String value = map.get(key)==null?"":map.get(key).toString();
				buffer.append("&"+key + "=" + value);
			}
		}
		
		String sNewString = getSign(buffer.toString().toUpperCase(), "MD5");
		
		return sNewString;
	}
	
	/**
	 * 获取加密签名
	 * @param str 字符
	 * @param type 加密类型
	 * @return 
	 * @throws Exception
	 */
	public static String getSign(String str, String type) throws Exception {
		MessageDigest crypt = MessageDigest.getInstance(type);
		crypt.reset();
		crypt.update(str.getBytes("UTF-8"));
		return str = byteToHex(crypt.digest());
	}
	
	/**
	 * 
	* @Title: byteToHex 
	* @Description: 字节转换 16 进制
	* @param @param hash
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
	/**
	 * 验签
	 * @param map
	 * @param keyValue  商户密钥
	 * @return
	 * @throws Exception 
	 */
	public static boolean checkSign(Map<String,Object> map,String keyValue) throws Exception{
		if(map.get("sign")==null){
			return false;
		}
		//密文
		String sign = map.get("sign").toString();
		map.remove("sign");
		String sNewString = getSign(map, keyValue);
		
		return sNewString.equals(sign);
	}
	
	public static void main(String[] args) throws Exception {
		Map<String,Object> map = new HashMap<>();
		
		//支付签名
		map.put("payMoney", "0.02");
		map.put("bussOrderNum", "1486245625411");
		map.put("notifyUrl", "http://api.pay.html");
		map.put("appKey", "270461df13a412f373ae6c2771ccd926");//内网appKey
		//map.put("appKey", "234e74508bfcc3cdf5545906320aeb2b");//外网appKey
		map.put("orderName", "充值话费");
		map.put("returnUrl", "");
		map.put("orderDescribe", "手机电信卡话费充值");
//		map.put("sign", "387ca231498d271b4281dcb037630767");
		String s =getSign(map,"be29c66b2d0b166c90d7a346209259149470faf76e53bf52b39d1a98a8d9250b");		//内网密钥
		
		/*//退款签名
		map.put("refundType", "N");
		map.put("refundMoney", "0.02");
		map.put("orderNum", "DD60000000401176183494");
		map.put("appKey", "270461df13a412f373ae6c2771ccd926");//内网appKey
		map.put("appKey", "234e74508bfcc3cdf5545906320aeb2b");//外网appKey
		String s =getSign(map,"be29c66b2d0b166c90d7a346209259149470faf76e53bf52b39d1a98a8d9250b");//内网密钥
		//String s =getSign(map,"5abc744cf47f1a40595331e1d6f616e799bd30aa2344bc08db9e77b25f1d4e04");//外网密钥*/
		
		
		System.out.println(s);
		
	}
}
