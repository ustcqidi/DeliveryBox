package client.auth;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class valudate1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		   RequestMessage rmt = new RequestMessage();
         rmt.setCmd("validatecode");
         RequestBasePart rbp = new RequestBasePart();
         rbp.setImei("000000");
         rbp.setTel("13655513423");
         rmt.setBase(rbp);
         Gson gson = new Gson();
         Map<String, String> param = new HashMap<String, String>();
      //   param.put("tel", "13655513423");
         rmt.setParam(param);
         System.out.println(gson.toJson(rmt));
         String res = HttpUtils.sendPost("http://120.27.45.163:8888/boxWebService/do?c=1002", gson.toJson(rmt));
 	       System.out.println(res);
		
	}

}
