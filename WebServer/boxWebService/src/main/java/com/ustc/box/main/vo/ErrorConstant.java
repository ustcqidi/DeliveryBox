/**
 * 
 */
package com.ustc.box.main.vo;

/**
 * @author ZGP
 *
 * 
 */
public class ErrorConstant {
	
	/**
	 * 验证码错误
	 */
	public static String NOVALIDATECODE = "000002";
	/**
	 * 无权限
	 */
	public static String NOPERMIT = "000001";
	/**
	 * 号码已被注册
	 */
	public static String TELHASREG = "000003";
	/**
	 * 验证码发送错误
	 */
	public static String VALIDATECODEERROR = "000004";
	/**
	 *  柜子已经被打开
	 */
	public static String HASOPEN = "000005";
	
	/**
	 * 该类型柜子已满
	 */
	public static String ISFULL = "000010";
	
	/**
	 *  解析错误
	 */
	public static String ERRORPARAM = "999997";
	/**
	 * Post请求没按照一定的格式发送
	 */
	public static String POSTERRORPARAM = "999998";
	/**
	 * 打开柜子出错
	 */
	public static String OPENERROR = "000020";
	
	/**
	 * 没有权限打开该柜子
	 */
	public static String HASNOPERMIT = "000021";
	
	/**
	 * 扫描错柜子
	 */
	public static String SCANERROR = "000022";
	
	/**
	 * 不存在该柜子
	 */
	public static String NOTHINGERROR = "000023";
	/**
	 * 未知错误
	 */
	public static String NOTKNOWN = "999999";
	/**
	 * 余额不足
	 */
	public static String NOMONEY = "000012";
	
}
