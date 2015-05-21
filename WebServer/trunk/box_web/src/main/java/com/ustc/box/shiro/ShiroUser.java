package com.ustc.box.shiro;

import java.io.Serializable;


	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		private String name;
		private String trueName;
		private Integer id;

		
		public ShiroUser(Integer id,  String name,String trueName) {
			this.id = id;
			this.name = name;
			this.trueName = trueName;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getTrueName() {
			return trueName;
		}


		public void setTrueName(String trueName) {
			this.trueName = trueName;
		}


		public Integer getId() {
			return id;
		}


		public void setId(Integer id) {
			this.id = id;
		}
		
		
     	}