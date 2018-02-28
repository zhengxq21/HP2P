package com.huo.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColumnCollection
{
  private DataTable parent;
  private Map<String, DataColumn> columns;
  private List<String> index$name;
  
  public ColumnCollection(DataTable parent)
  {
    this.parent = parent;
    
    this.columns = new HashMap();
    this.index$name = new ArrayList();
  }
  
  public DataTable getParent()
  {
    return this.parent;
  }
  
  public void add(DataColumn column)
    throws DataException
  {
    if ((column.getName() == null) || (column.getName().trim().isEmpty())) {
      column.setName(getNewName());
    } else if (findIndex(column.getName()) >= 0) {
      throw new DataException("The column's name \"" + 
        column.getName() + "\" already exists!");
    }
    this.columns.put(column.getName(), column);
    this.index$name.add(column.getName());
    column.setParent(this);
    
    this.parent.rows.addRowsMeta();
  }
  
  public DataColumn get(String name)
  {
    return (DataColumn)this.columns.get(name);
  }
  
  public DataColumn get(int index)
  {
    return get((String)this.index$name.get(index));
  }
  
  public boolean isExistColumn(String name)
  {
    return this.index$name.contains(name);
  }
  
  public void remove(String name)
    throws DataException
  {
    int index = findIndex(name);
    if (index < 0) {
      throw new DataException("The column's name \"" + name + 
        "\" isn't exists!");
    }
    DataColumn column = (DataColumn)this.columns.get(name);
    column.setParent(null);
    
    this.columns.remove(name);
    this.index$name.remove(index);
    this.parent.rows.removeRowsMeta(index);
  }
  
  public void remove(int index)
    throws DataException
  {
    if ((index < 0) || (index >= this.index$name.size())) {
      throw new DataException("The column's index \"" + 
        String.valueOf(index) + "\" out of range!");
    }
    DataColumn column = (DataColumn)this.columns.get(this.index$name.get(index));
    column.setParent(null);
    
    this.columns.remove(this.index$name.get(index));
    this.index$name.remove(index);
    this.parent.rows.removeRowsMeta(index);
  }
  
  public void clear()
  {
    for (String key : this.columns.keySet()) {
      ((DataColumn)this.columns.get(key)).setParent(null);
    }
    this.columns.clear();
    this.index$name.clear();
    this.parent.rows.clearMeta();
  }
  
  public int getCount()
  {
    return this.index$name.size();
  }
  
  public int findIndex(String name)
  {
    for (int i = 0; i < this.index$name.size(); i++) {
      if (((String)this.index$name.get(i)).equals(name)) {
        return i;
      }
    }
    return -1;
  }
  
  private String getNewName()
  {
    String baseName = "DataColumn";
    int i = 1;
    while (findIndex(baseName + String.valueOf(i)) >= 0) {
      i++;
    }
    return baseName + String.valueOf(i);
  }
}
