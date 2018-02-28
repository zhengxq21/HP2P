package com.huo.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import oracle.sql.CLOB;

public class DataTable
{
  private String name;
  private TableCollection parent;
  public ColumnCollection columns;
  public RowCollection rows;
  
  public DataTable()
  {
    this.parent = null;
    this.columns = new ColumnCollection(this);
    this.rows = new RowCollection(this);
  }
  
  public DataTable(String name)
  {
    this();
    this.name = name;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
    throws DataException
  {
    if (this.parent != null) {
      throw new DataException(
        "The table \"" + 
        name + 
        "\" already belongs to a collection of name attribute can not be modified!");
    }
    this.name = name;
  }
  
  public void setParent(TableCollection tables)
  {
    this.parent = tables;
  }
  
  public DataRow newRow()
  {
    DataRow row = new DataRow();
    row.setInitLength(this.columns.getCount());
    row.setParent(this.rows);
    
    return row;
  }
  
  public void clear()
  {
    this.rows.clear();
  }
  
  public void parseResultSet(ResultSet input)
    throws SQLException, DataException
  {
    clear();
    
    ResultSetMetaData rsmd = input.getMetaData();
    int columnCount = rsmd.getColumnCount();
    for (int i = 0; i < columnCount; i++) {  //´úÂë¸Ä¶¯
      this.columns.add(new DataColumn(rsmd.getColumnLabel(i + 1), 
        rsmd.getColumnTypeName(i + 1)));
    }
    int i=0;
    for (; i<columnCount; input.next())  
    {
      DataRow dr = newRow();
      this.rows.add(dr);
      
      i = 0; //continue;
      if (this.columns.get(i).getType().equalsIgnoreCase("CLOB"))
      {
        CLOB myClob = (CLOB)input.getClob(i + 1);
        if (myClob == null)
        {
          dr.set(i, null);
        }
        else
        {
          BufferedReader br = new BufferedReader(myClob.getCharacterStream());
          String line = "";String t = "";
          try
          {
            t = br.readLine();
            while (t != null)
            {
              line = line + t;
              t = br.readLine();
            }
            br.close();
          }
          catch (IOException e)
          {
            e.printStackTrace();
          }
          dr.set(i, line);
        }
      }
      else
      {
        dr.set(i, input.getObject(i + 1));
      }
      i++;
    }
  }
}
