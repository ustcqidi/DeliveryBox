package client.auth;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class update {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RequestMessage rmt = new RequestMessage();
        rmt.setCmd("updateuserinfo");
        RequestBasePart rbp = new RequestBasePart();
        rbp.setImei("000000");
        rbp.setType("1");
        rbp.setToken("2bcd156d79614db0ab0f76ef6c1b406e");
        rmt.setBase(rbp);
        Gson gson = new Gson();
        Map<String, String> param = new HashMap<String, String>();
        param.put("tel", "13655513423");
        param.put("username", "zgp4");
        param.put("company", "zgp4");
        rmt.setParam(param);
		
	    String res = HttpUtils.sendPost("http://localhost:8088/boxWebService/do?c=1012", gson.toJson(rmt));
	    System.out.println(res);

	}

}
