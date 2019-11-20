```
package com.dac.app;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dac.app.comman.dao.CommonDao;
import com.dac.app.user.bean.User;
import com.dac.app.user.service.UserService;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonDao commonDao;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("Application Started...!!");
	}
	

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUserId("superuser3");
		user.setFirstName("Dipak");
		user.setLastName("Aher");
		user.setEmailId("aherdipak3@gmail.com");
		user.setMobileNumber("9762927019");
		System.out.println(userService.addUser(user, null));
		Map<String,String> map = new LinkedHashMap<>();
		map.put("userId", "superuser2");
		map.put("firstName", "Dipak");
		List<User> list = (List<User>) commonDao.getData("User",map);
		for(User bean : list) {
		System.out.println(bean);
		}
		
	}

}

```
//------------------------------------------------------------------------------------

```
package com.dac.app.comman.dao;

import java.util.List;
import java.util.Map;

public interface CommonDao {

	boolean addData(Object obj);

	List<?> getData(String objName,Map<String,String>whereMap);

}

```
