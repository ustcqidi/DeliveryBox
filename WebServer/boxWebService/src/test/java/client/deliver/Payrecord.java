package client.deliver;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class Payrecord {

	public static void main(String[] args) {
		    RequestMessage rmt = new RequestMessage();
		    rmt.setCmd("chargerecord");
		    RequestBasePart rbp = new RequestBasePart();
	        rbp.setImei("353995055741049");
	        rbp.setType("1");
	        rbp.setToken("5f409f266f0f41adac6d03ac766ae85a");
	        rbp.setTel("13655513423");
	        rmt.setBase(rbp);
	        Gson gson = new Gson();
	        Map<String, String> param = new HashMap<String, String>();
	        rmt.setParam(param);
          String res = HttpUtils.sendPost("http://localhost:8088/boxWebService/do?c=1016", gson.toJson(rmt));
	        System.out.println(res);

	}
}
