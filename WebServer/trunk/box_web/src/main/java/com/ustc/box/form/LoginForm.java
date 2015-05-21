package com.ustc.box.form;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {

	@NotEmpty(message = "用户名不能为空")
	private String username;

	@NotEmpty(message = "密码不能为空")
	private String password;

	@NotEmpty(message = "验证码不能为空")
	private String checkcode;

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String getCheckcode() {

		return checkcode;
	}

	public void setCheckcode(String checkcode) {

		this.checkcode = checkcode;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("LoginForm [username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", checkcode=");
		builder.append(checkcode);
		builder.append("]");
		return builder.toString();
	}

}
