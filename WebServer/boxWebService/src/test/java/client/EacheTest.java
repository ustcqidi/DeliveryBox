package client;

import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import net.sf.ehcache.Element;

import com.ustc.box.core.utils.EhcacheHelper;
import com.ustc.box.main.service.UserService;
import com.ustc.box.main.vo.UserResponse;

/**
 * @author gpzhang
 *
 * @date : 2014-11-28
 */
public class EacheTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ApplicationContext context = new FileSystemXmlApplicationContext(
				"target\\classes\\applicationContext.xml");
		UserService u = (UserService) context.getBean("userService");
	  

	}

}
