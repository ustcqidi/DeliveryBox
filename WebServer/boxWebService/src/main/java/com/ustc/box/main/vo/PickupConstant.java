/**
 * 
 */
package com.ustc.box.main.vo;

/**
 * @author ZGP
 *
 * 
 */
public class PickupConstant {
	
	/**
	 * 快递员打开柜子,未确认状态
	 */
	public static Integer open = 0;
	/**
	 * 快递员确认柜子
	 */
	public static Integer confirm = 1;
	
	/**
	 * 用户已取件
	 */
	public static Integer useropen = 2;

	/**
	 * 快递员取消返回
	 */
	public static Integer cancel = 3;
	
	/**
	 * 快递员自己打开之前未确认的柜子
	 */
	public static Integer openself = 4;
	
	/**
	 * 快递员自取
	 */
	public static Integer deliveryopen = 5;
	
	/**
	 * 帮他代取
	 */
	public static Integer helpopen = 6;


	
}
