package client;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpException;

import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.core.utils.SecureUtils;
import com.ustc.box.core.utils.Send;

public class Sender {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws ParseException, HttpException, IOException {
		  // 按照传入的格式生成一个simpledateformate对象  
		String url = "http://localhost:8080/bl/mobileDevices/sendMessage.shtml?message="+URLEncoder.encode("哈哈sss", "UTF-8")+"&mobile=13655523423&";
        System.out.println(url);  
		System.out.println(HttpUtils.sendGet(url)); 
	}

}
