package com.huo.data.dao;

import java.sql.SQLException;

public class Field {

	public Object parent;
	public String name;
	public int type;
	protected Object value;
	public boolean readOnly;

	
	public void setValue(Object value)throws SQLException {
		if(this.readOnly) {
			throw new SQLException("Readonly field cannot be assigned.");
		}
		
		this.value = value;
		
		if(this.parent!=null) {
			((Table)this.parent).fields.add(this);  //当用户调用setter方法的时候把改值设置到父类的fields数据中
		}
		
		
	}
	
	public Field(Object parent,String name,int type,boolean readOnly) {
		this.parent = parent;
		this.name = name;
		this.type = type;
		this.readOnly = readOnly;
	}
	
	
}
