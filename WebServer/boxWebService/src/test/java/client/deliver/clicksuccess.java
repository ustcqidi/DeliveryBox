package client.deliver;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class clicksuccess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		   RequestMessage rmt = new RequestMessage();
	        rmt.setCmd("clicksuccess");
	        RequestBasePart rbp = new RequestBasePart();
	        rbp.setImei("353995055741049");
	        rbp.setType("1");
	        rbp.setToken("28827a74e51448a88ce1f7594efbb406");
	        rbp.setTel("13655513423");
	        rmt.setBase(rbp);
	        Gson gson = new Gson();
	        Map<String, String> param = new HashMap<String, String>();
	        param.put("cabinetId", "ffffffff-98f7-204d-0033-c5870033c587");
	        param.put("boxId", "4");
	        rmt.setParam(param);
            String res = HttpUtils.sendPost("http://120.27.31.209:8888/boxWebService/do?c=1008", gson.toJson(rmt));
 	        System.out.println(res);
		
	}

}
