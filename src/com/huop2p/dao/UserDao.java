package com.huop2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.huop2p.database.Dao;

public class UserDao {

	
	public Long addUser(Connection conn, String email, String userName,String password, String refferee, String lastDate, String lastIP,String dealpwd, String mobilePhone, Integer rating,Integer creditrating, Integer vipstatus, String vipcreatetime,Integer authstep, String headImg, Integer enable,Long servicePersonId,double creditLimit) throws SQLException{
		
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.email.setValue(email);
		user.username.setValue(userName);
		user.password.setValue(password);
		user.lastDate.setValue(lastDate);
		user.refferee.setValue(refferee);
		user.dealpwd.setValue(password);
		if (StringUtils.isNotBlank(lastIP)) {
			user.lastIP.setValue(lastIP);
		}
		
		user.creditLimit.setValue(creditLimit);
		user.usableCreditLimit.setValue(creditLimit);
		user.authStep.setValue(authstep);
		user.mobilePhone.setValue(mobilePhone);
		user.rating.setValue(rating);
		user.creditrating.setValue(creditrating);
		user.vipStatus.setValue(vipstatus);
		user.vipCreateTime.setValue(vipcreatetime);
		user.headImg.setValue(headImg);
		user.enable.setValue(enable);
		user.createTime.setValue(new Date());
		return user.insert(conn);
	}
	
}
