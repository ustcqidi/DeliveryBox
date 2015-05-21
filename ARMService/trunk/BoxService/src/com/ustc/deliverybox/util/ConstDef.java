package com.ustc.deliverybox.util;

public interface ConstDef {

	public static final int TOAST_DURATION = 2000;

	public static final int SPLASH_DISPLAY_LENGHT = 1500;

	public static final String BASE_URL = "http://120.25.225.67";
	//public static final String BASE_URL = "http://bilintek.cn";
	public static final String BACKEND_URL = BASE_URL+":80/";

	public static final int HOUROFDAY = 1;
	public static final int[] CHECK_UPDATE_POINTS ={6,13,21 };
	public static final String CHECK_UPDATE_URL = BASE_URL+":8080/update/OpenBox/version_info.json";
	public static String OPEN_BOX_URL = BASE_URL+":8888/boxWebService/helptake";
    public static final String  PREF_LAST_UPDATE_CHECK = "pref_last_update_check";
    public static final String PREF_INSTALL_FILE = "install_file";
    public static final String PREF_UPDATE_EXTRA_INFO = "update_extra_info";
    public static final String PREF_DOWNLOAD_URL = "download_url";
    public static final String FILE_NAM = "file_name";
    public static final int UPDATE_FREQ_NONE = -2;
    public static final int UPDATE_FREQ = 24*60*60*1000;

}
