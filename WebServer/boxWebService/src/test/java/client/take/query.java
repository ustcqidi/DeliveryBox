package client.take;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class query {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		   RequestMessage rmt = new RequestMessage();
	        rmt.setCmd("userpickup");
	        RequestBasePart rbp = new RequestBasePart();
	        rbp.setImei("353995055741049");
	        rbp.setType("1");
	        rbp.setToken("340d574b5f18494a83b8e2d5c125ef67");
	        rbp.setTel("15715516028");
	        rmt.setBase(rbp);
	        Gson gson = new Gson();
	        Map<String, String> param = new HashMap<String, String>();
	        rmt.setParam(param);
            String res = HttpUtils.sendPost("http://localhost:8088/boxWebService/do?c=1013", gson.toJson(rmt));
 	        System.out.println(res);
		
	}

}
