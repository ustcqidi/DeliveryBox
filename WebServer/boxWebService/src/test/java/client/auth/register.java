package client.auth;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class register {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RequestMessage rmt = new RequestMessage();
      rmt.setCmd("register");
      RequestBasePart rbp = new RequestBasePart();
      rbp.setImei("000000");
      rbp.setType("1");
      rmt.setBase(rbp);
      Gson gson = new Gson();
      Map<String, String> param = new HashMap<String, String>();
      param.put("tel", "000000");
      param.put("validatecode", "1065");
      rmt.setParam(param);
		
	    String res = HttpUtils.sendPost("http://120.27.31.209:8888/boxWebService/do?c=1001", gson.toJson(rmt));
	    System.out.println(res);

	}

}
