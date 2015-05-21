package com.ustc.box.core.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author <a href="mailto:peng.wu@foxmail.com">wu.peng</a>
 * 
 */
public class HttpUtils {

	private static final Log log = LogFactory.getLog(HttpUtils.class);

	public static byte[] get(String url) {
		return get(url, 0);
	}

	public static byte[] get(String url, int timeout) {
		HttpClient hc = new HttpClient();
		hc.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		hc.getHttpConnectionManager().getParams().setSoTimeout(timeout);
		GetMethod method = null;
		InputStream in = null;
		try {
			method = new GetMethod(url);
			method.setFollowRedirects(false);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			method.getParams().setParameter(HttpMethodParams.HTTP_URI_CHARSET, "utf-8");
			int code = hc.executeMethod(method);
			if (code == HttpStatus.SC_OK) {
				in = method.getResponseBodyAsStream();
				return IOUtil.readByteArray(in);
			}
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			if (method != null) {
				method.releaseConnection();
				method = null;
			}
			IOUtil.close(in);
		}
		return new byte[] {};
	}

	public static byte[] post(String url, Map<String, String> params) {
		return post(url, params, 0);
	}

	public static byte[] post(String url, Map<String, String> params, int timeout) {
		HttpClient hc = new HttpClient();
		hc.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		hc.getHttpConnectionManager().getParams().setSoTimeout(timeout);
		PostMethod method = null;
		InputStream in = null;
		try {
			method = new PostMethod(url);
			method.setFollowRedirects(false);
			List<NameValuePair> _params = new ArrayList<NameValuePair>();
			if (params != null) {
				for (String key : params.keySet()) {
					_params.add(new NameValuePair(key, params.get(key)));
				}
			}
			method.setRequestBody(_params.toArray(new NameValuePair[] {}));
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			method.getParams().setParameter(HttpMethodParams.HTTP_URI_CHARSET, "utf-8");
			int code = hc.executeMethod(method);
			if (code == HttpStatus.SC_OK) {
				in = method.getResponseBodyAsStream();
				return IOUtil.readByteArray(in);
			}
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			if (method != null) {
				method.releaseConnection();
				method = null;
			}
			IOUtil.close(in);
		}
		return new byte[] {};
	}

	public static Map<String, String> getParams(ServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		if (request == null) {
			return params;
		}
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			params.put(paramName, paramValue);
		}
		return params;
	}

	/**
	 * 发送http post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws runtime
	 *             exception
	 */
	public static String rest(String url, Map<String, String> params) {
		try {
			return new String(HttpUtils.post(url, params), "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e, e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 发送http post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws runtime
	 *             exception
	 */
	public static String rest(String url, Map<String, String> params, int timeout) {
		try {
			return new String(HttpUtils.post(url, params, timeout), "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e, e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 发送http get请求
	 * 
	 * @param url
	 * @return
	 * @throws runtime
	 *             exception
	 */
	public static String rest(String url) {
		try {
			return new String(HttpUtils.get(url), "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e, e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 发送http get请求
	 * 
	 * @param url
	 * @return
	 * @throws runtime
	 *             exception
	 */
	public static String rest(String url, int timeout) {
		try {
			return new String(HttpUtils.get(url, timeout), "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e, e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * @param url
	 * @return size of the resource or -1 if unknown
	 */
	public static int getSize(String url) {
		try {
			URL _url = new URL(url);
			HttpURLConnection httpconn = (HttpURLConnection) _url.openConnection();
			return httpconn.getContentLength();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return -1;
	}

	public static String getFormatSize(String url) {
		int size = getSize(url);
		if (size == -1)
			return "未知";
		DecimalFormat dfK = new DecimalFormat("###");
		DecimalFormat dfM = new DecimalFormat("###.#");
		float f;
		if (size < 1024 * 50) {
			return "50K以下";
		} else if (size < 1024 * 1024) {
			f = (float) ((float) size / (float) 1024);
			return (dfK.format(new Float(f).doubleValue()) + "KB");
		} else {
			f = (float) ((float) size / (float) (1024 * 1024));
			return (dfM.format(new Float(f).doubleValue()) + "MB");
		}
	}

	public static String getUserAgent(HttpServletRequest request) {
		if (request == null)
			return "";
		return request.getHeader("User-Agent");
	}

	public static boolean isIE6(HttpServletRequest request) {
		return getUserAgent(request).contains("MSIE 6.0");
	}

	public static boolean isIE7(HttpServletRequest request) {
		return getUserAgent(request).contains("MSIE 7.0");
	}

	public static boolean isIE8(HttpServletRequest request) {
		return getUserAgent(request).contains("MSIE 8.0");
	}

	public static boolean isLowIE(HttpServletRequest request) {
		return isIE6(request) || isIE7(request) || isIE8(request);
	}

}
