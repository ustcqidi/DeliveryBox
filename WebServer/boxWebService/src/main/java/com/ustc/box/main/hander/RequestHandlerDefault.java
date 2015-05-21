package com.ustc.box.main.hander;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ustc.box.main.vo.RequestMessage;
import com.ustc.box.main.vo.ResponseBase;

/**
 * 未找到类型的处理类
 * 
 * @author Administrator
 * 
 */
@Service
public class RequestHandlerDefault extends RequestHandler {
	public String handle(RequestMessage base) {
		ResponseBase resp = ResponseBase.getDefaultErrorResp();
		Gson gson = new Gson();
		return gson.toJson(resp);
	}

}
