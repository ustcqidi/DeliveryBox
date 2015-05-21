package client.take;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class nearby {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		   RequestMessage rmt = new RequestMessage();
	        rmt.setCmd("nearbydelivery");
	        RequestBasePart rbp = new RequestBasePart();
	        rbp.setImei("866022023088393");
	        rbp.setType("1");
	        rbp.setToken("48bdc0f10b49481cbc89248320ad28dd");
	        rbp.setTel("15955199848");
	        rmt.setBase(rbp);
	        Gson gson = new Gson();
	        Map<String, String> param = new HashMap<String, String>();
	        rmt.setParam(param);
	        System.out.println(gson.toJson(rmt));
            String res = HttpUtils.sendPost("http://120.27.31.209:8888/boxWebService/do?c=1017", gson.toJson(rmt));
 	        System.out.println(res);
		
	}

}
