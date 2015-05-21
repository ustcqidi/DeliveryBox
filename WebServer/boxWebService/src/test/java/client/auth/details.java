package client.auth;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class details {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RequestMessage rmt = new RequestMessage();
        rmt.setCmd("detailuserinfo");
        RequestBasePart rbp = new RequestBasePart();
        rbp.setImei("353995055741049");
        rbp.setTel("13655513423");
        rbp.setToken("28827a74e51448a88ce1f7594efbb406");
        rmt.setBase(rbp);
        Gson gson = new Gson();
        Map<String, String> param = new HashMap<String, String>();
        param.put("courier", "顺丰速递");
        param.put("company", "科大讯飞");
        rmt.setParam(param);
		System.out.println(gson.toJson(rmt));
	    String res = HttpUtils.sendPost("http://120.27.45.163:8888/boxWebService/do?c=1005", gson.toJson(rmt));
	    System.out.println(res);

	}

}
