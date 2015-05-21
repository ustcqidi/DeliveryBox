package client.auth;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class Login {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RequestMessage rmt = new RequestMessage();
        rmt.setCmd("login");
        RequestBasePart rbp = new RequestBasePart();
        rbp.setImei("000000");
        rbp.setTel("13655513423");
        rbp.setType("1");
        rmt.setBase(rbp);
        Gson gson = new Gson();
        Map<String, String> param = new HashMap<String, String>();
        param.put("validatecode", "6638");
        rmt.setParam(param);
		System.out.println(gson.toJson(rmt));
	    String res = HttpUtils.sendPost("http://120.27.31.209:8888/boxWebService/do?c=1003", gson.toJson(rmt));
	    System.out.println(res);
//		

	}

}
