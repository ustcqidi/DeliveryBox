package com.ustc.box.core.filter;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IOUtil {

	public final static int DEFAULT_BUFFER_SIZE = 1024 * 4;

	private final static Log LOG = LogFactory.getLog(IOUtil.class);

	public static String read(InputStream in, String encode) {
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(in, encode);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		return read(reader);
	}

	public static String readFromResource(String resource, String encode) throws IOException {
		InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			if (in == null) {
				return null;
			}

			String text = IOUtil.read(in, encode);
			return text;
		} finally {
			IOUtil.close(in);
		}
	}

	public static byte[] readByteArrayFromResource(String resource) throws IOException {
		InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			if (in == null) {
				return null;
			}

			return readByteArray(in);
		} finally {
			IOUtil.close(in);
		}
	}

	public static byte[] readByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	public static long copy(InputStream input, OutputStream output) throws IOException {
		final int EOF = -1;

		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

		long count = 0;
		int n = 0;
		while (EOF != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static String read(Reader reader) {
		try {

			StringWriter writer = new StringWriter();

			char[] buffer = new char[DEFAULT_BUFFER_SIZE];
			int n = 0;
			while (-1 != (n = reader.read(buffer))) {
				writer.write(buffer, 0, n);
			}

			return writer.toString();
		} catch (IOException ex) {
			throw new IllegalStateException("read error", ex);
		}
	}

	public static String read(Reader reader, int length) {
		try {
			char[] buffer = new char[length];

			int offset = 0;
			int rest = length;
			int len;
			while ((len = reader.read(buffer, offset, rest)) != -1) {
				rest -= len;
				offset += len;

				if (rest == 0) {
					break;
				}
			}

			return new String(buffer, 0, length - rest);
		} catch (IOException ex) {
			throw new IllegalStateException("read error", ex);
		}
	}

	public static String toString(java.util.Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public static String getStackTrace(Throwable ex) {
		StringWriter buf = new StringWriter();
		ex.printStackTrace(new PrintWriter(buf));

		return buf.toString();
	}

	public static String toString(StackTraceElement[] stackTrace) {
		StringBuilder buf = new StringBuilder();
		for (StackTraceElement item : stackTrace) {
			buf.append(item.toString());
			buf.append("\n");
		}
		return buf.toString();
	}

	public final static void close(Closeable x) {
		if (x != null) {
			try {
				x.close();
			} catch (Exception e) {
				LOG.error("close error", e);
			}
		}
	}

}
