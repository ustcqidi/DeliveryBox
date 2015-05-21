package com.ustc.box.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.ustc.box.dao.HDao;
import com.ustc.box.dao.PageBean;
import com.ustc.box.entity.UserInfo;
import com.ustc.box.utils.RequestUtils;

@Service
@Transactional
public class UserService {

	@Resource
	private HDao dao;

	/**
	 * 根据用户名密码来查询用户信息
	 * 
	 * @param username
	 *            用户登录名
	 * @param password
	 *            密码
	 * @return
	 */
	public UserInfo queryLogin(String username, String password) {

		return dao.findUniqueResult("from UserInfo where name = ? and password  = ?",
				username, password);
	}

	public UserInfo findUserByUserName(String name) {
		return dao.findUniqueResult("from UserInfo where name = ? ", name);
	};

	public UserInfo getUserById(Integer id) {
		return dao.get(UserInfo.class, id);
	};

	public void deleteUserById(String id) {
		UserInfo user = dao.get(UserInfo.class, Integer.parseInt(id));
		dao.delete(user);
	};

	public PageBean<UserInfo> page(int iDisplayStart, int iDisplayLength,
			String sSortDir_0, String sColumn, String name) {
		PageBean<UserInfo> pageBean = new PageBean<UserInfo>();
		pageBean.setStart(iDisplayStart);
		pageBean.setLimit(iDisplayLength);
		String sql = "from UserInfo where name like '%" + name
				+ "%' and name <> 'admin' ";
		if (!StringUtils.isEmpty(sSortDir_0) && !StringUtils.isEmpty(sColumn)) {
			sql += " Order by " + sColumn + " " + sSortDir_0;
		}
		return dao.findByPage(pageBean, sql.toString());
	}

	public String updatePasswd(Map<String, String> map) {

		Integer userId = RequestUtils.getCurrentShiroUser().getId();
		String newPassword = map.get("newPassword");
		String checkPassword = map.get("checkPassword");

		if (!StringUtils.equals(newPassword, checkPassword)) {
			return "两次密码不一致";// 两次密码一直
		}
		String oldPassword = DigestUtils.md5DigestAsHex(map.get("oldPassword")
				.toString().getBytes());
		boolean successful = checkPassword(userId, oldPassword);
		if (!successful) {
			return "密码不正确！";
		}
		UserInfo user = getUserById(userId);
		newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
		user.setPassword(newPassword);
		dao.update(user);
		return "1";
	}

	public void resertPasswd(String id) {
		UserInfo user = getUserById(Integer.parseInt(id));
		user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
	}

	public boolean checkPassword(Integer UserInfoId, String password) {
		UserInfo UserInfo = getUserById(UserInfoId);
		return password.equals(UserInfo.getPassword());
	}

	public String saveOrUpdate(String id, String name, String trueName,
			String desc) {
		if (name.equals("admin")) {
			return "2";
		}
		if (StringUtils.isNotEmpty(id)) {
			UserInfo user = dao.get(UserInfo.class, Integer.parseInt(id));
			user.setTrueName(trueName);
			user.setDes(desc);
			if (checkUsername(id, name)) {
				return "0";
			} else {
				user.setName(name);
				dao.update(user);
				return "1";
			}

		} else {
			UserInfo user = new UserInfo();
			user.setDes(desc);
			user.setTrueName(trueName);
			user.setPassword(DigestUtils.md5DigestAsHex(new String("123456")
					.getBytes()));
			if (checkUsername(name)) {
				return "0";
			} else {
				user.setName(name);
				dao.save(user);
				return "1";
			}
		}
	}

	/**
	 * 查询用户登录名数量
	 * 
	 * @param username
	 *            用户登录名
	 * @return 是否存在同名的username
	 */
	public boolean checkUsername(String username) {
		List<UserInfo> list = dao
				.find("from UserInfo where name = ?", username);
		return list != null && list.size() > 0;
	}

	/**
	 * 查询用户登录名数量
	 * 
	 * @param username
	 *            用户登录名
	 * @return 是否存在同名的username
	 */
	public boolean checkUsername(String userId, String username) {
		List<UserInfo> list = dao.find(
				"from UserInfo where name = ? and id != ?", username,
				Integer.parseInt(userId));
		return list != null && list.size() > 0;
	}

}
