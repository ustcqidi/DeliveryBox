package com.ustc.box.utils;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TomcatRunner {

	private static final Log log = LogFactory.getLog(TomcatRunner.class);

	public static final String SPRING_PROFILE_KEY = "spring.profiles.active";

	private String contextPath = "/";
	private String basedir = "target";
	private String webappDirLocation = "src/main/webapp";
	private String profile = "dev";
	private int port = 8080;
	private String URIEncoding = "UTF-8";

	public TomcatRunner() {
	}

	public TomcatRunner(String contextPath, int port) {
		this.contextPath = contextPath;
		this.port = port;
	}

	public void run() {
		long start = System.currentTimeMillis();
		try {
			Tomcat tomcat = new Tomcat();
			tomcat.setBaseDir(basedir);
			tomcat.setPort(port);
			String absPath = new File(webappDirLocation).getAbsolutePath();
			Context ctx = tomcat.addWebapp(contextPath, absPath);
			ctx.setReloadable(true);
			// tomcat.getConnector().setURIEncoding(URIEncoding);
			tomcat.start();
			long end = System.currentTimeMillis();
			log.info("- -  T O M C A T  S T A R T  - -");
			log.info("tomcat startup in " + (end - start) + " ms.");
			log.info("webapp dir: " + absPath);
			tomcat.getServer().await();
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	public String getContextPath() {
		return contextPath;
	}

	public TomcatRunner setContextPath(String contextPath) {
		this.contextPath = contextPath;
		return this;
	}

	public String getWebappDirLocation() {
		return webappDirLocation;
	}

	public TomcatRunner setWebappDirLocation(String webappDirLocation) {
		this.webappDirLocation = webappDirLocation;
		return this;
	}

	public int getPort() {
		return port;
	}

	public TomcatRunner setPort(int port) {
		this.port = port;
		return this;
	}

	public String getProfile() {
		return profile;
	}

	public TomcatRunner setProfile(String profile) {
		this.profile = profile;
		return this;
	}

	public String getBasedir() {
		return basedir;
	}

	public TomcatRunner setBasedir(String basedir) {
		this.basedir = basedir;
		return this;
	}

	public String getURIEncoding() {
		return URIEncoding;
	}

	public TomcatRunner setURIEncoding(String URIEncoding) {
		this.URIEncoding = URIEncoding;
		return this;
	}

}
