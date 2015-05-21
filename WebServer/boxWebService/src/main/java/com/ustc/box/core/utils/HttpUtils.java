package com.ustc.box.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.eclipse.jdt.internal.compiler.ast.PostfixExpression;

import com.ustc.box.core.filter.IOUtil;

/**
 * @author gpzhang
 *
 * @date : 2014-11-20
 */
public class HttpUtils {

	  public static String sendPost(String url, String msg) {
			HttpClient client = new HttpClient();
//			NameValuePair[] nameValuePairs = new NameValuePair[3];
//			nameValuePairs[0] = new NameValuePair("apikey", apikey);
//			nameValuePairs[1] = new NameValuePair("text", text);
//			nameValuePairs[2] = new NameValuePair("mobile", mobile);
			PostMethod method = new PostMethod(url);
			RequestEntity requestEntity = null;
			try {
				requestEntity = new StringRequestEntity(msg, "application/json", "utf-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			method.setRequestEntity(requestEntity);
			HttpMethodParams param = method.getParams();
			param.setContentCharset("utf-8");
			try {
				client.executeMethod(method);
				return method.getResponseBodyAsString();
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
	    }  

	  public static String sendPostFile(String url, String path) {
			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(url);
			FilePart fp = null;
			try {
				fp = new FilePart("filedata", new File(path));
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}  
	        Part[] parts = { fp }; 
			MultipartRequestEntity mre = new MultipartRequestEntity(parts, method.getParams());
			method.setRequestEntity(mre);
			try {
				client.executeMethod(method);
				return method.getResponseBodyAsString();
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
	    } 
	  
	   public static String sendGet(String url) {
	        String result = "";
	        HttpClient hc = new HttpClient();
			hc.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
			hc.getHttpConnectionManager().getParams().setSoTimeout(6000);
			GetMethod method = null;
			InputStream in = null;
			try {
				method = new GetMethod(url);
				method.setFollowRedirects(false);
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
				method.getParams().setParameter(HttpMethodParams.HTTP_URI_CHARSET, "utf-8");
				int code = hc.executeMethod(method);
				if (code == HttpStatus.SC_OK) {
					result = method.getResponseBodyAsString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (method != null) {
					method.releaseConnection();
					method = null;
				}
				IOUtil.close(in);
			}
	        return result;
	    }
	   
	   
	   public static String get(String url, int timeout) {
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
					return method.getResponseBodyAsString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (method != null) {
					method.releaseConnection();
					method = null;
				}
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return  null;
		}

}
