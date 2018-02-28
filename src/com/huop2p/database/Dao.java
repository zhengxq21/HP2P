package com.huop2p.database;

import java.sql.Types;

import com.huo.data.dao.Field;
import com.huo.data.dao.Table;

public class Dao {

	
	public class Tables{
		
		public class t_user extends Table{
			public Field id = new Field(this,"`id`",Types.BIGINT,true);
			public Field email = new Field(this,"`email`",Types.VARCHAR,false);
			public Field username = new Field(this,"`username`",Types.VARCHAR,false);
			public Field password = new Field(this, "`password`", Types.VARCHAR, false);
			public Field dealpwd = new Field(this, "`dealpwd`", Types.VARCHAR, false);
			public Field mobilePhone = new Field(this, "`mobilePhone`", Types.VARCHAR, false);
			public Field refferee = new Field(this, "`refferee`", Types.VARCHAR, false);
			public Field rating = new Field(this, "`rating`", Types.INTEGER, false);
			public Field investrating = new Field(this, "`investrating`", Types.INTEGER, false);
			public Field creditrating = new Field(this, "`creditrating`", Types.INTEGER, false);
			public Field lastIP = new Field(this, "`lastIP`", Types.VARCHAR, false);
			public Field lastDate = new Field(this, "`lastDate`", Types.TIMESTAMP, false);
			public Field vipStatus = new Field(this, "`vipStatus`", Types.INTEGER, false);
			public Field vipCreateTime = new Field(this, "`vipCreateTime`", Types.TIMESTAMP, false);
			public Field creditLimit = new Field(this, "`creditLimit`", Types.BIGINT, false);
			public Field authStep = new Field(this, "`authStep`", Types.INTEGER, false);
			public Field headImg = new Field(this, "`headImg`", Types.VARCHAR, false);
			public Field enable = new Field(this, "`enable`", Types.INTEGER, false);
			public Field createTime = new Field(this, "`createTime`", Types.TIMESTAMP, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field usableSum = new Field(this, "`usableSum`", Types.DECIMAL, false);
			public Field freezeSum = new Field(this, "`freezeSum`", Types.DECIMAL, false);
			public Field dueinSum = new Field(this, "`dueinSum`", Types.DECIMAL, false);
			public Field dueoutSum = new Field(this, "`dueoutSum`", Types.DECIMAL, false);
			public Field kefuId = new Field(this, "`kefuId`", Types.BIGINT, false);
			public Field adminId = new Field(this, "`adminId`", Types.BIGINT, false);
			public Field groupId = new Field(this, "`groupId`", Types.BIGINT, false);
			public Field usableCreditLimit = new Field(this, "`usableCreditLimit`", Types.BIGINT, false);
			public Field creditlimtor = new Field(this, "`creditlimtor`", Types.BIGINT, false);
			public Field vipFee = new Field(this, "`vipFee`", Types.DECIMAL, false);
			public Field feeStatus = new Field(this, "`feeStatus`", Types.INTEGER, false);
			public Field loginCount = new Field(this, "`loginCount`", Types.BIGINT, false);
			public Field lockTime = new Field(this, "`lockTime`", Types.TIMESTAMP, false);
			public Field cashStatus = new Field(this, "`cashStatus`", Types.INTEGER, false);
			public Field xmax = new Field(this, "`xmax`", Types.DECIMAL, false);
			public Field x = new Field(this, "`x`", Types.DECIMAL, false);
			public Field xmin = new Field(this, "`xmin`", Types.DECIMAL, false);
			public Field isFirstVip = new Field(this, "`isFirstVip`", Types.INTEGER, false);
			public Field sign = new Field(this, "`sign`", Types.VARCHAR, false);
			public Field sign2 = new Field(this, "`sign2`", Types.VARCHAR, false);
			public Field loginErrorCount = new Field(this, "`loginErrorCount`", Types.INTEGER, false);
			public Field isLoginLimit = new Field(this, "`isLoginLimit`", Types.INTEGER, false);
			public Field isApplyPro = new Field(this, "`isApplyPro`", Types.INTEGER, false);
			public Field regClient = new Field(this, "`regClient`", Types.INTEGER, false);
			public Field pnrid = new Field(this, "`pnrid`", Types.VARCHAR, false);
			public Field pnrBindCard = new Field(this, "`pnrBindCard`", Types.VARCHAR, false);
			public Field pnrusrcust = new Field(this, "`pnrusrcust`", Types.VARCHAR, false);
			public Field eid = new Field(this, "`eid`", Types.VARCHAR, false);
			public Field eiscard = new Field(this, "`eiscard`", Types.INTEGER, false);
			public t_user(){
				name = "`t_user`";
			}
			
		}
		
	}
	
}
