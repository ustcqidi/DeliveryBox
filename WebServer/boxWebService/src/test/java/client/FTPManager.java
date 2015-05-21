package client;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.tomcat.util.http.fileupload.IOUtils;

/**
 * FTP上传工具类
 */

public class FTPManager {
	private FTPClient ftpclient;

	private String ipAddress;

	private int ipPort = 21;

	private String userName;

	private String passWord;

	private static final String Encod = "UTF-8";

	private static final Log logger = LogFactory.getLog(FTPManager.class);

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getIpPort() {
		return ipPort;
	}

	public void setIpPort(int ipPort) {
		this.ipPort = ipPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public FTPManager() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param ip
	 *            String 机器IP
	 * @param port
	 *            String 机器FTP端口号
	 * @param username
	 *            String FTP用户名
	 * @param password
	 *            String FTP密码
	 * @throws Exception
	 */
	public FTPManager(String ip, int port, String username, String password) throws Exception {
		this.ipAddress = new String(ip);
		ipPort = port;
		userName = new String(username);
		passWord = new String(password);
	}

	/**
	 * 构造函数
	 * 
	 * @param ip
	 *            String 机器IP，默认端口为21
	 * @param username
	 *            String FTP用户名
	 * @param password
	 *            String FTP密码
	 * @throws Exception
	 */
	public FTPManager(String ip, String username, String password) throws Exception {
		ipAddress = new String(ip);
		userName = new String(username);
		passWord = new String(password);
	}

	/**
	 * 登录FTP服务器
	 * 
	 * @throws Exception
	 */
	public boolean connectServer() {
		boolean flag = true;
		try {
			ftpclient = new FTPClient();
			ftpclient.setControlEncoding(Encod);
			ftpclient.setConnectTimeout(1000000);
			ftpclient.connect(ipAddress, ipPort);
			ftpclient.login(userName, passWord);
			int reply = ftpclient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpclient.disconnect();
				logger.debug("FTP 服务拒绝连接！");
				flag = false;
			}
		} catch (SocketException e) {
			flag = false;
			logger.error(ExceptionUtils.getStackTrace(e));
			logger.error("登录ftp服务器 " + ipAddress + " 失败,连接超时！");
		} catch (IOException e) {
			flag = false;
			logger.error(ExceptionUtils.getStackTrace(e));
			logger.error("登录ftp服务器 " + ipAddress + " 失败，FTP服务器无法打开！");
		}
		return flag;
	}

	/**
	 * 退出FTP服务器
	 * 
	 * @throws Exception
	 */
	public void disConnectServer() {
		try {
			if (ftpclient != null && ftpclient.isConnected()) {
				ftpclient.logout();
				ftpclient.disconnect();
			}
		} catch (Exception e) {
			logger.error(e,e);
		}
	}

	/**
	 * 在FTP服务器上建立指定的目录, 上传文件时保证目录的存在目录格式必须以"/"根目录开头
	 * 
	 * @param pathList
	 *            String
	 * @throws Exception
	 */
	public boolean makeDirectory(String dir) {
		boolean flag = true;
		try {
			flag = ftpclient.makeDirectory(dir);
			if (flag) {
				logger.debug("make Directory " + dir + " succeed");
			} else {
				logger.debug("make Directory " + dir + " false");
			}
		} catch (Exception e) {
			flag = false;
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return flag;
	}

	/**
	 * 取得指定目录下的所有文件名，不包括目录名称 分析nameList得到的输入流中的数，得到指定目录下的所有文件名
	 * 
	 * @param fullPath
	 *            String
	 * @return ArrayList
	 * @throws Exception
	 */
	public List<String> fileNames(String fullPath) throws Exception {
		List<String> list = new ArrayList<String>();
		try {
			String[] names = ftpclient.listNames();
			if (names == null) {
				return list;
			}
			for (int i = 0; i < names.length; i++) {
				list.add(names[i]);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

	/**
	 * 列出服务器上文件和目录
	 * 
	 * @param regStr
	 *            --匹配的正则表达式
	 */
	public String[] getRemoteFiles(String path) {
		String files[] = null;
		try {
			boolean x = ftpclient.changeWorkingDirectory(path);
			if (x) {
				files = ftpclient.listNames();
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return files;
	}

	/**
	 * 上传文件到FTP服务器,destination路径以FTP服务器的"/"开始，带文件名、
	 * 
	 * @param source
	 *            String
	 * @param destination
	 *            String
	 * @throws Exception
	 */
	public boolean upFile(String path, String filename, InputStream input) {
		boolean flag = false;
		try {
			ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpclient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			ftpclient.changeWorkingDirectory("/");
			//创建多层目录,临时办法
			if (!StringUtils.isEmpty(path)) {
				String[] paths = path.split("/|\\\\");
				for (int i = 0; i < paths.length; i++) {
					String p = paths[i];
					if (!StringUtils.isEmpty(p)) {
						ftpclient.makeDirectory(p);
						ftpclient.changeWorkingDirectory(p);
					}
				}
			}
			ftpclient.setDataTimeout(1000000);
			ftpclient.enterLocalPassiveMode();
			flag = ftpclient.storeFile(filename, input);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return flag;
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean deleteFile(String pathAndFileName) {
		boolean flag = false;
		try {
			flag = ftpclient.deleteFile(pathAndFileName);
		} catch (IOException ioe) {
			logger.error(ExceptionUtils.getStackTrace(ioe));
		}
		return flag;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean isExist(String path, String fileName) throws Exception {
		boolean isExist = false;
		String fileNames[] = null;
		try {
			fileNames = this.getRemoteFiles(path);
		} catch (Exception e) {
			e.printStackTrace();
			return isExist;
		}
		if (fileNames != null) {
			for (int i = 0; i < fileNames.length; i++) {
				if (fileName.equals(fileNames[i])) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}

	/**
	 * 从FTP文件服务器上下载文件，输出到字节数组中
	 * 
	 * @param SourceFileName
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] downFile(String SourceFileName) {

		ByteArrayOutputStream byteOut = null;
		InputStream ftpIn = null;
		try {
			ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpclient.enterLocalPassiveMode();
			ftpclient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

			byteOut = new ByteArrayOutputStream();
			ftpIn = ftpclient.retrieveFileStream(SourceFileName);
			if (ftpIn == null) {
				return null;
			}
			byte[] buf = new byte[204800];
			int bufsize = 0;
			while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
				byteOut.write(buf, 0, bufsize);
			}
			return byteOut.toByteArray();
		} catch (IOException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			try {
				byteOut.close();
				ftpIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 调用示例 FTPUtil fUp = new FTPUtil("192.150.189.22", 21, "admin", "admin");
	 * fUp.login(); fUp.buildList("/adfadsg/sfsdfd/cc"); String destination =
	 * "/test.zip"; fUp.upFile(
	 * "C:\\Documents and Settings\\Administrator\\My Documents\\sample.zip"
	 * ,destination); ArrayList filename = fUp.fileNames("/"); for (int i = 0; i
	 * < filename.size(); i++) { System.out.println(filename.get(i).toString());
	 * } fUp.logout();
	 * 
	 * @param args
	 *            String[]
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FTPManager fUp = new FTPManager("120.27.31.209", "root", "2014Ustc");
		fUp.connectServer();
		FileInputStream fin = new FileInputStream("d:\\about1.jpg");
		fUp.upFile("/opt/imgs/test", "test.jpg", fin);
//		fUp.deleteFile("/test/test.txt");
//		fUp.getRemoteFiles("/");
//		List<String> list = fUp.fileNames("/");
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).toString());
//		}
//		fUp.downFile("/test/test.txt");
		fin.close();
		fUp.disConnectServer();
		System.out.println("程序运行完成！");
	}
}