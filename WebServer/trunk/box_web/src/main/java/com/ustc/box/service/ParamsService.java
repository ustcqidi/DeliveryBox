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
import com.ustc.box.entity.AppUser;
import com.ustc.box.entity.UserInfo;
import com.ustc.box.utils.RequestUtils;

@Service
@Transactional
public class ParamsService {

	@Resource
	private HDao dao;


	public AppUser findUserByTel(String name) {
		return dao.findUniqueResult("from AppUser where tel = ? ", name);
	};

	public AppUser getAppUserById(Integer id) {
		return dao.get(AppUser.class, id);
	};

	public void deleteUserById(String id) {
		AppUser user = dao.get(AppUser.class, Integer.parseInt(id));
		dao.delete(user);
	};

	
	public void saveOrUpdate(AppUser appUser){
		if(appUser.getId() == null){
			dao.save(appUser);
		}else{
			dao.update(appUser);
		}
	}
	
	public PageBean<AppUser> page(int iDisplayStart, int iDisplayLength,
			String sSortDir_0, String sColumn, String username,String company,String tel) {
		PageBean<AppUser> pageBean = new PageBean<AppUser>();
		pageBean.setStart(iDisplayStart);
		pageBean.setLimit(iDisplayLength);
		String sql = "from AppUser where 1 = 1 ";
		if(StringUtils.isNotEmpty(username)){
			sql+=" and username like '%"+username+"%' ";
		}
		if(StringUtils.isNotEmpty(company)){
			sql+=" and company like '%"+company+"%' ";
		}
		if(StringUtils.isNotEmpty(tel)){
			sql+=" and tel like '%"+tel+"%' ";
		}
		
		if (!StringUtils.isEmpty(sSortDir_0) && !StringUtils.isEmpty(sColumn)) {
			sql += " Order by " + sColumn + " " + sSortDir_0;
		}
		return dao.findByPage(pageBean, sql.toString());
	}

}
