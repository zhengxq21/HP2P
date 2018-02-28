package com.huo.data.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Table extends DataObject{

	public List<Field> fields = new ArrayList();
	
	
	public long insert(Connection conn)throws SQLException{
		if(this.fields.size()==0) {
			throw new SQLException("Do not provide a list of fields."); //ÎÞ¸³Öµinsert
		}
		String insertFieldsList = "";
		String insertValuesList = "";
		Parameter[] parameters = new Parameter[fields.size()];
		for(int i = 0;i < this.fields.size(); i++) {
			if(i>0) {
				insertFieldsList = insertFieldsList + ", ";
				insertValuesList = insertValuesList + ", ";
			}
			insertFieldsList = insertFieldsList + fields.get(i).name;
			insertValuesList = insertValuesList + "?";
			parameters[i] = new Parameter(this.fields.get(i).type,ParameterDirection.IN,this.fields.get(i).value);
		}
		String command = "insert into " + Database.getObjectFullName(this.name) + " (" + insertFieldsList + 
			      ") values (" + insertValuesList + ")";
		long result = Database.executeNonQuery(conn, command, parameters);
	    this.fields.clear();
	    
	    return result;
	}
	
}
