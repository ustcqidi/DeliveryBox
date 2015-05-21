package client.registerbox;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.ustc.box.core.utils.HttpUtils;
import com.ustc.box.main.vo.RequestBasePart;
import com.ustc.box.main.vo.RequestMessage;

public class registerbox {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
      Gson gson = new Gson();
      Map<String, String> param = new HashMap<String, String>();
      param.put("cabinetId", "19880405");
      param.put("province", "安徽");
      param.put("city", "合肥");
      param.put("region", "蜀山区");
      param.put("smallCount", "2");
      param.put("communityName", "大溪地");
      param.put("cabinetName", "第一个");
      System.out.println(param);
      String res = HttpUtils.sendPost("http://localhost:8088/boxWebService/register?c=2001", gson.toJson(param));
      System.out.println(res);

	}

}
