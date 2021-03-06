


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
//------------------------------------------------------------------------------------

```
package com.dac.app.comman.service;

import java.util.Date;

public interface CommonMethod {

	void setAddedByAndDate(String addedBy, Date addedDate, String updatedBy, Date updatedDate);

}

```

//------------------------------------------------------------------------------------

```
package com.dac.app.user.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dac.app.comman.service.CommonMethod;


@Entity
@Table(name = "USER")
public class User implements CommonMethod{
	
	@Id
	@Column(name = "userId",length = 50)
	private String userId;
	
	@Column(name = "firstName",length = 50)
	private String firstName;
	
	@Column(name = "lastName",length = 50)
	private String lastName;
	
	@Column(name = "emailId",length = 50)
	private String emailId;
	
	@Column(name = "mobileNumber",length = 50)
	private String mobileNumber;
	
	@Column(name="addedBy",length = 50)
	private String addedBy;
	
	@Column(name="addedBy",length = 50)
	private Date addedDate;
	
	@Transient
	private String addedStrDate;
	
	@Column(name="updatedBy",length = 50)
	private String updatedBy;
	
	@Column(name="updatedDate",length = 50)
	private Date updatedDate;

	@Transient
	private String updatedStrDate;
	
	
	// getters & setters

	@Override
	public void setAddedByAndDate(String addedBy,Date addedDate,String updatedBy,Date updatedDate) {
			this.addedBy = addedBy;
			this.addedDate = addedDate;
			this.updatedBy = updatedBy;
			this.updatedDate = updatedDate;
		
	}
	
	
	
	

}

```
//------------------------------------------------------------------------------------

```
package com.dac.app.user.service;

import javax.servlet.http.HttpServletRequest;

import com.dac.app.user.bean.User;

public interface UserService {

	boolean addUser(User user, HttpServletRequest request);

}

```
//------------------------------------------------------------------------------------

```
package com.dac.app.user.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.app.comman.dao.CommonDao;
import com.dac.app.user.bean.User;
import com.dac.app.user.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Override
	public boolean addUser(User user,HttpServletRequest request) {
		boolean status = false;
		try {
			//status = userDao.addUser(user);
			status = commonDao.addData(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
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
//------------------------------------------------------------------------------------

```
package com.dac.app.comman.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDaoImpl implements CommonDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean addData(Object obj) {
		Session session = null;
		Transaction txn= null;
		boolean status = false;
		try {
			 session = sessionFactory.openSession();
			 txn = session.beginTransaction();
			 session.save(obj);
			 txn.commit();
			 status= true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		return status;
	}
	
	@Override
	public List<?> getData(String obj,Map<String,String>whereMap) {
		Session session = null;
		List<?> list = null;
		String whereStr = "";
		try {
			 session = sessionFactory.openSession();
			 if(whereMap!=null && !whereMap.isEmpty()) {
				 boolean firstTime = true;
				for(String colName : whereMap.keySet()) {
					if(firstTime) {
						whereStr = " where "+colName+" = '"+whereMap.get(colName)+"'";
						firstTime = false;
					}else {
						whereStr += " and "+colName+" = '"+whereMap.get(colName)+"'";
					}
				}
			 }
			Query<Object> qry = session.createQuery("from "+obj+whereStr,Object.class);
			 list = qry.list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		return list;
	}

}

```
