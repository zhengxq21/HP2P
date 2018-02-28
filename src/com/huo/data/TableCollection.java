package com.huo.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableCollection
{
  private Map<String, DataTable> tables;
  private List<String> index$name;
  
  public TableCollection()
  {
    this.tables = new HashMap();
    this.index$name = new ArrayList();
  }
  
  public void add(DataTable table)
    throws DataException
  {
    if ((table.getName() == null) || (table.getName().trim().isEmpty())) {
      table.setName(getNewName());
    } else if (findIndex(table.getName()) >= 0) {
      throw new DataException("The table's name \"" + 
        table.getName() + "\" already exists!");
    }
    this.tables.put(table.getName(), table);
    this.index$name.add(table.getName());
    table.setParent(this);
  }
  
  public DataTable get(String name)
  {
    return (DataTable)this.tables.get(name);
  }
  
  public DataTable get(int index)
  {
    return get((String)this.index$name.get(index));
  }
  
  public boolean isExistTable(String name)
  {
    return this.index$name.contains(name);
  }
  
  public void remove(String name)
    throws DataException
  {
    int index = findIndex(name);
    if (index < 0) {
      throw new DataException("The table's name \"" + name + 
        "\" isn't exists!");
    }
    DataTable table = (DataTable)this.tables.get(name);
    table.setParent(null);
    
    this.tables.remove(name);
    this.index$name.remove(index);
  }
  
  public void remove(int index)
    throws DataException
  {
    if ((index < 0) || (index >= this.index$name.size())) {
      throw new DataException("The table's index \"" + 
        String.valueOf(index) + "\" out of range!");
    }
    DataTable table = (DataTable)this.tables.get(this.index$name.get(index));
    table.setParent(null);
    
    this.tables.remove(this.index$name.get(index));
    this.index$name.remove(index);
  }
  
  public void clear()
  {
    for (String key : this.tables.keySet()) {
      ((DataTable)this.tables.get(key)).setParent(null);
    }
    this.tables.clear();
    this.index$name.clear();
  }
  
  public int getCount()
  {
    return this.index$name.size();
  }
  
  private int findIndex(String name)
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
    String baseName = "DataTable";
    int i = 1;
    while (findIndex(baseName + String.valueOf(i)) >= 0) {
      i++;
    }
    return baseName + String.valueOf(i);
  }
}
