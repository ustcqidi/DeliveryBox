package com.ustc.box.utils;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter {

	/** 字符编码 */
	private String encoding;

	public void init(FilterConfig chain) throws ServletException {

		// 获取web.xml中该过滤器的参数
		this.encoding = chain.getInitParameter("encoding");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(this.encoding);
		response.setCharacterEncoding(this.encoding);

		// 下一个过滤器
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
