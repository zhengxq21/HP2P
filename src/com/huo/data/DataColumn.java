package com.huo.data;

public class DataColumn
{
  private String name;
  private ColumnCollection parent;
  private String type;
  
  public DataColumn()
  {
    this.parent = null;
  }
  
  public DataColumn(String name)
  {
    this();
    this.name = name;
  }
  
  public DataColumn(String name, String type)
  {
    this();
    this.name = name;
    this.type = type;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String value)
    throws DataException
  {
    if (this.parent != null) {
      throw new DataException(
        "The column \"" + 
        this.name + 
        "\" already belongs to a collection of name attribute can not be modified!");
    }
    this.name = value;
  }
  
  public void setParent(ColumnCollection columns)
  {
    this.parent = columns;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String value)
  {
    this.type = value;
  }
}
