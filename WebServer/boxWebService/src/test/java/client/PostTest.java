package client;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class PostTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//         String res = HttpUtils.sendPost("http://localhost:8088/boxWebService/notify","");
//		System.out.println(res);
		String tel = "+8613655513423";
		if(tel.length() > 11){
			System.out.println(tel.substring(tel.length() - 11, tel.length()));
		}
	}

}
