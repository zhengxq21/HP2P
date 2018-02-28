package com.huo.data;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import oracle.sql.CLOB;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataSet
{
  private String name;
  public TableCollection tables;
  
  public DataSet()
  {
    this("DataSet");
  }
  
  public DataSet(String name)
  {
    this.name = name;
    this.tables = new TableCollection();
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String value)
  {
    this.name = value;
  }
  
  public void clear()
  {
    this.tables.clear();
  }
  
  public Document toXmlDocument()
    throws DataException
  {
    Document document = DocumentHelper.createDocument();
    Element rootElement = document.addElement(this.name);
    for (int i = 0; i < this.tables.getCount(); i++)
    {
      DataTable table = this.tables.get(i);
      for (int j = 0; j < table.rows.getCount(); j++)
      {
        Element rowElement = rootElement.addElement(table.getName());
        for (int k = 0; k < table.columns.getCount(); k++)
        {
          Element columnElement = rowElement.addElement(table.columns
            .get(k).getName());
          columnElement.setText(table.rows.get(j).get(k) == null ? "" : 
            table.rows.get(j).get(k).toString());
        }
      }
    }
    return document;
  }
  
  public String asXml()
    throws DataException
  {
    Document document = toXmlDocument();
    
    return document.asXML();
  }
  
  public void parseSimpleText(String text)
    throws DataException, DocumentException
  {
    clear();
    
    Document document = DocumentHelper.parseText(text);
    Element rootElement = document.getRootElement();
    
    Iterator<?> tableIterator = rootElement.elementIterator();
    Iterator<?> columnIterator;
    for (; tableIterator.hasNext(); columnIterator.hasNext())
    {
      Element tableElement = (Element)tableIterator.next();
      DataTable dt = this.tables.get(tableElement.getName());
      if (dt == null)
      {
        dt = new DataTable(tableElement.getName());
        this.tables.add(dt);
      }
      DataRow dr = dt.newRow();
      dt.rows.add(dr);
      
      columnIterator = tableElement.elementIterator();
      
      continue;
      /*Element columnElement = (Element)columnIterator.next();
      if (!dt.columns.isExistColumn(columnElement.getName())) {
        dt.columns.add(new DataColumn(columnElement.getName(), 
          "Object"));
      }
      dr.set(columnElement.getName(), columnElement.getText());*/
    }
  }
  
  public void parseText(String text)
    throws DataException, DocumentException
  {
    clear();
    
    Document document = DocumentHelper.parseText(text);
    Element element = document.getRootElement().element("schema")
      .element("element");
    setName(element.attributeValue("name"));
    element = element.element("complexType").element("choice");
    
    Iterator<?> tableIterator = element.elementIterator();
    Iterator<?> columnIterator;
    for (; tableIterator.hasNext(); columnIterator.hasNext())
    {
      Element tableElement = (Element)tableIterator.next();
      DataTable dt = new DataTable(tableElement.attributeValue("name"));
      this.tables.add(dt);
      
      columnIterator = tableElement.element("complexType")
        .element("sequence").elementIterator();
      
      //continue;    //改动
      Element columnElement = (Element)columnIterator.next();
      
      dt.columns.add(new DataColumn(columnElement
        .attributeValue("name"), convertType(columnElement
        .attributeValue("type"))));
    }
    element = 
      document.getRootElement().element("diffgram").element(getName());
    
    tableIterator = element.elementIterator();
    //代码改动
    /*Iterator<?> columnIterator;
    for (; tableIterator.hasNext(); columnIterator.hasNext())
    {
      Element tableElement = (Element)tableIterator.next();
      DataTable dt = this.tables.get(tableElement.getName());
      DataRow dr = dt.newRow();
      dt.rows.add(dr);
      
      columnIterator = tableElement.elementIterator();
      
      continue;
      Element columnElement = (Element)columnIterator.next();
      dr.set(columnElement.getName(), columnElement.getText());
    }*/ 
  }
  
  private String convertType(String input)
  {
    return input.substring(3, 4).toUpperCase() + input.substring(4);
  }
  
  public void parseJaxwsSoapResult(List<Object> input)
    throws DataException
  {
    clear();
    
    Node node = ((ElementNSImpl)input.get(0)).getFirstChild();
    setName(node.getAttributes().getNamedItem("name").getTextContent());
    node = node.getFirstChild().getFirstChild();
    NodeList tableNodes = node.getChildNodes();
    for (int i = 0; i < tableNodes.getLength(); i++)
    {
      Node tableNode = tableNodes.item(i);
      DataTable dt = new DataTable(tableNode.getAttributes()
        .getNamedItem("name").getTextContent());
      this.tables.add(dt);
      
      NodeList columnNodes = tableNode.getFirstChild().getFirstChild()
        .getChildNodes();
      for (int j = 0; j < columnNodes.getLength(); j++)
      {
        Node columnNode = columnNodes.item(j);
        
        dt.columns.add(new DataColumn(columnNode.getAttributes()
          .getNamedItem("name").getTextContent(), 
          convertType(columnNode.getAttributes()
          .getNamedItem("type").getTextContent())));
      }
    }
    node = ((ElementNSImpl)input.get(1)).getFirstChild();
    tableNodes = node.getChildNodes();
    for (int i = 0; i < tableNodes.getLength(); i++)
    {
      Node tableNode = tableNodes.item(i);
      DataTable dt = this.tables.get(tableNode.getNodeName());
      DataRow dr = dt.newRow();
      dt.rows.add(dr);
      
      NodeList columnNodes = tableNode.getChildNodes();
      for (int j = 0; j < columnNodes.getLength(); j++)
      {
        Node columnNode = columnNodes.item(j);
        dr.set(columnNode.getNodeName(), columnNode.getTextContent());
      }
    }
  }
  
  public void parseResultSet(ResultSet input)  //ResultSet是以表格的形式存在，所以getMetaData就包括了数据的字段名称、类型以及数目等表格所必须具备的信息
    throws SQLException, DataException
  {
    clear();
    
    DataTable dt = new DataTable("DataTable");
    this.tables.add(dt);
    
    ResultSetMetaData rsmd = input.getMetaData();  
    
    int columnCount = rsmd.getColumnCount();
    for (int i = 0; i < columnCount; i++) {
      dt.columns.add(new DataColumn(rsmd.getColumnLabel(i + 1), rsmd.getColumnTypeName(i + 1)));
    }
    int i;
    /*for (; input.next(); i < columnCount) //代码改动
    {
      DataRow dr = dt.newRow();
      dt.rows.add(dr);
      
      i = 0; continue;
      if (dt.columns.get(i).getType().equalsIgnoreCase("CLOB"))
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
    }*/
  }
}
