package weixin.popular.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.util.StringUtils;

/**
 * 验证事件消息签名 工具类
 *
 */
public class ValidateSignatureUtil {
	/**
	 * 验证事件消息签名
	 * @param signature
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean validateSignature(String signature,String token, String timestamp,String nonce) {
		String[] array = new String[]{token,timestamp,nonce};
		Arrays.sort(array);
		String s = StringUtils.arrayToDelimitedString(array, "");
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1"); 
			byte[] b = md.digest(s.getBytes());
			String bsignature = byte2hex(b);
			return bsignature.equals(signature);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 转化为16进制字符串
	 * 
	 * @param digest
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	
	public static void main(String args[]){
		
	}
}
