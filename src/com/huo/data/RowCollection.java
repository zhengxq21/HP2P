package com.huo.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowCollection
{
  private DataTable parent;
  private List<DataRow> rows;
  public List<Map<String, Object>> rowsMap;
  
  public RowCollection(DataTable parent)
  {
    this.parent = parent;
    
    this.rows = new ArrayList();
    this.rowsMap = new ArrayList();
  }
  
  public void add(DataRow row)
    throws DataException
  {
    if ((row.getParent() != this) || 
      (row.getLength() != this.parent.columns.getCount())) {
      throw new DataException(
        "The DataRow object does not match with the table or column!");
    }
    this.rows.add(row);
  }
  
  public DataTable getParent()
  {
    return this.parent;
  }
  
  void addRowsMeta()
  {
    for (DataRow dr : this.rows) {
      dr.addMeta();
    }
  }
  
  void removeRowsMeta(int index)
  {
    for (DataRow dr : this.rows) {
      dr.removeMeta(index);
    }
  }
  
  public DataRow get(int index)
    throws DataException
  {
    if ((index < 0) || (index > this.rows.size())) {
      throw new DataException("Index out of range!");
    }
    return (DataRow)this.rows.get(index);
  }
  
  public void remove(int index)
    throws DataException
  {
    if ((index < 0) || (index >= this.rows.size())) {
      throw new DataException("Index out of range!");
    }
    this.rows.remove(index);
  }
  
  public void clear()
  {
    this.rows.clear();
  }
  
  void clearMeta()
  {
    for (DataRow dr : this.rows) {
      dr.clearMeta();
    }
  }
  
  public int getCount()
  {
    return this.rows.size();
  }
  
  public List<DataRow> getRowsList()
  {
    return this.rows;
  }
  
  public void genRowsMap()
  {
    this.rowsMap = new ArrayList();
    for (DataRow dr : this.rows)
    {
      Map<String, Object> map = new HashMap();
      for (int i = 0; i < dr.getLength(); i++) {
        map.put(this.parent.columns.get(i).getName(), dr.get(i));
      }
      this.rowsMap.add(map);
      map = null;
    }
  }
}
