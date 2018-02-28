package com.huo.data;

import java.util.ArrayList;
import java.util.List;

public class DataRow
{
  private RowCollection parent;
  private List<Object> values;
  
  public DataRow()
  {
    this.values = new ArrayList();
  }
  
  void setParent(RowCollection parent)
  {
    this.parent = parent;
  }
  
  public RowCollection getParent()
  {
    return this.parent;
  }
  
  void setInitLength(int length)
  {
    this.values.clear();
    for (int i = 0; i < length; i++) {
      this.values.add(null);
    }
  }
  
  public int getLength()
  {
    return this.values.size();
  }
  
  void addMeta()
  {
    this.values.add(null);
  }
  
  void removeMeta(int index)
  {
    this.values.remove(index);
  }
  
  public void set(int index, Object value)
  {
    this.values.set(index, value);
  }
  
  public void set(String name, Object value)
    throws DataException
  {
    int index = this.parent.getParent().columns.findIndex(name);
    if (index < 0) {
      throw new DataException("The column's name \"" + name + 
        "\" isn't exists!");
    }
    this.values.set(index, value);
  }
  
  public Object get(int index)
  {
    return this.values.get(index);
  }
  
  public Object get(String name)
    throws DataException
  {
    int index = this.parent.getParent().columns.findIndex(name);
    if (index < 0) {
      throw new DataException("The column's name \"" + name + 
        "\" isn't exists!");
    }
    return this.values.get(index);
  }
  
  public void clear()
  {
    setInitLength(this.values.size());
  }
  
  void clearMeta()
  {
    this.values.clear();
  }
}
