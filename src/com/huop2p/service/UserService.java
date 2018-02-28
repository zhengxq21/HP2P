package com.huop2p.service;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huo.Convert;
import com.huo.data.dao.MySQL;
import com.huop2p.dao.UserDao;

public class UserService {
	public static Log log = LogFactory.getLog(UserService.class);

	
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}




	public Long addUser(String email, String userName, String password,
			String refferee, String lastDate, String lastIP, String dealpwd,
			String mobilePhone, Integer rating, Integer creditrating,
			Integer vipstatus, String vipcreatetime, Integer creditlimit,
			Integer authstep, String headImg, Integer enable,
			Long servicePersonId) throws Exception{
		Connection conn = MySQL.getConnection();
		long userId = -1L;
		try {
			
//			得到信息额度类型
			double creditLimit = 1000.0;
			userId = userDao.addUser(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit);
			
		}catch(Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		}
		
		return null;
	}
	
}
