package client.registerbox;

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
      rmt.setCmd("registerboxupdate");
      Gson gson = new Gson();
      Map<String, String> param = new HashMap<String, String>();
      param.put("boxId", "201412010211");
      param.put("imei", "021213213213");
      param.put("cabinetType", "a");
      param.put("province", "安徽");
      param.put("city", "芜湖");
      param.put("region", "蜀山区");
      param.put("communityAddress", "梦圆小区");
      param.put("communityUser", "张先生");
      param.put("communityContact", "13655512232");
      param.put("communityPropertyCompany", "华帝物业");
      param.put("manager", "饶刚");
      rmt.setParam(param);
	    String res = HttpUtils.sendPost("http://localhost:8088/boxWebService/register?c=2002", gson.toJson(rmt));
	    System.out.println(res);

	}

}
