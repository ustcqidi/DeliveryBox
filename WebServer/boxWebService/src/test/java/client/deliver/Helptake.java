package client.deliver;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class Helptake {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
      Gson gson = new Gson();
      Map<String, String> param = new HashMap<String, String>();
      param.put("tel", "15156879326");
      param.put("cabinetId", "ffffffff-98f7-204d-0033-c5870033c587");
      param.put("validatecode", "2112");
      System.out.println(gson.toJson(param));
	    String res = HttpUtils.sendPost("http://120.27.31.209:8888/boxWebService/helptake", gson.toJson(param));
	    System.out.println(res);

	}

}
