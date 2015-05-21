package client.take;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class take {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		   RequestMessage rmt = new RequestMessage();
	        rmt.setCmd("useropenbox");
	        RequestBasePart rbp = new RequestBasePart();
	        rbp.setImei("353995055741049");
	        rbp.setType("1");
	        rbp.setToken("28827a74e51448a88ce1f7594efbb406");
	        rbp.setTel("13655513423");
	        Gson gson = new Gson();
	        Map<String, String> param = new HashMap<String, String>();
	        param.put("boxId", "15");
	        param.put("cabinetId", "ffffffff-98f7-204d-0033-c5870033c587");
	        rmt.setBase(rbp);
	        rmt.setParam(param);
	        System.out.println(gson.toJson(rmt));
            String res = HttpUtils.sendPost("http://localhost:8088/boxWebService/do?c=1014", gson.toJson(rmt));
 	        System.out.println(res);
		
	}

}
