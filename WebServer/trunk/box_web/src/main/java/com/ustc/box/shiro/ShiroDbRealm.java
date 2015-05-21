package com.ustc.box.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.ustc.box.entity.UserInfo;
import com.ustc.box.service.UserService;



public class ShiroDbRealm extends AuthorizingRealm {
	
	@Autowired
	protected UserService userService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		token.setPassword(DigestUtils.md5DigestAsHex(
				new String(token.getPassword()).getBytes()).toCharArray());
		UserInfo user = userService.findUserByUserName(token.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(new ShiroUser(user.getId(),user.getName(),
					user.getTrueName()),
					user.getPassword(), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		UserInfo user = userService.findUserByUserName(shiroUser.getName());

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		Role role = user.getRoleList().get(0);
//		// 基于Role的权限信息
		if(user.getName().equals("admin")){
			info.addRole("admin");
		}else{
			info.addRole("user");
		}
		
//		// 基于Permission的权限信息
//		List<Resource> resources = role.getResourceList();
//		List<String> permits = new ArrayList<String>();
//		if(user.getStatus() == 2||user.getLoginName().equals("admin")){
//			permits.add("goods");
//		}
//		for (Iterator<Resource> iterator = resources.iterator(); iterator
//				.hasNext();) {
//			Resource res = iterator.next();
//			permits.add(res.getResourceName());
//		}
       
//		info.addStringPermissions(permits);
		return info;
	}

}
