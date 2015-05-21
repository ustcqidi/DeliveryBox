package com.ustc.box.shiro;

import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;

public class RootPathRememberMeManager extends CookieRememberMeManager {

	public RootPathRememberMeManager() {
		super();
	}

	public RootPathRememberMeManager(int maxAge) {
		Cookie cookie = new SimpleCookie(DEFAULT_REMEMBER_ME_COOKIE_NAME);
		cookie.setPath(Cookie.ROOT_PATH);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(maxAge * 24 * 60 * 60);
		setCookie(cookie);
	}

}
