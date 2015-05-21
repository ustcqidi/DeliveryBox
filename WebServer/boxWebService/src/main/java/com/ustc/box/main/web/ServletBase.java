package com.ustc.box.main.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@SuppressWarnings("serial")
public class ServletBase extends HttpServlet {
	private static final Logger LOGGER = LogManager.getLogger(ServletBase.class);
	private static final int BYTES_ONE_READ = 1024;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");

		String web = "GET method not supported!";

		response.getWriter().println(web);
	}

	/**
	 * 从输入流中读取到字节，会涉及到字节数较多，需要读取多次的情况；
	 * 
	 * @param stream
	 *            输入流
	 * @return
	 * @throws IOException
	 */
	byte[] readInputStream(InputStream stream)
			throws IOException {
		byte[] buffer = new byte[BYTES_ONE_READ];
		List<byte[]> byteList = new ArrayList<byte[]>();
		int n = 0, sum = 0;
		do {
			n = stream.read(buffer);
			if (n <= 0)
				break;
			sum += n;
			byte[] t = new byte[n];
			System.arraycopy(buffer, 0, t, 0, n);
			byteList.add(t);
		} while (n > 0);
		byte[] results = new byte[sum];
		int pos = 0;
		for (byte[] tmp : byteList) {
			System.arraycopy(tmp, 0, results, pos, tmp.length);
			pos += tmp.length;
		}
		return results;
	}

	/**
	 * 把结果返回给客户端
	 * 
	 * @param response
	 *            输出流
	 * @param msg
	 *            要输出的信息
	 * @throws IOException
	 */
	protected void output(HttpServletResponse response, String msg) throws IOException {
		LOGGER.debug(">>>output(response,msg,xorKey)");
		OutputStream stream = response.getOutputStream();
		byte[] buffer = msg.getBytes("UTF-8");
		response.setContentLength(buffer.length);
		stream.write(buffer);
		stream.flush();
		stream.close();
		LOGGER.debug("<<<output(response,msg,xorKey)");
	}

}
