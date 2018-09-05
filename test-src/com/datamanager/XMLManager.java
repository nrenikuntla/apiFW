package com.datamanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.IllegalNameException;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.testng.Assert;
//import com.testng.Assert;
import com.utilities.UtilityMethods;

public class XMLManager {
	SAXBuilder builder = new SAXBuilder();
	private String fileseperator=File.separator;
	File xFile;
	String fileeeeeePath;
	Document xmldoc;
	Element rootNode;
	private Logger log = Logger.getLogger("XMLManager");
	private Element root;

	/**
	 * Purpose- Constructor to pass Xml file path
	 * 
	 * @param sFileName
	 */
	public XMLManager(File xmlFilePath) {
		this.xFile = xmlFilePath;
	}
	
	
/*public String getElementText(String xpath){
		
		String text=null;
//		try {
//			xmldoc = builder.build(xFile);
//			Element s = xmldoc.getRootElement();
			Element child = null;

			String[] array = xpath.split("/");
			int length = array.length;
			if(length<=1){
				log.error("invalid xpath expression");
				Assert.fail("Invalid xpath expression");
			}
			if(length==2){
				if(root.getName().equals(array[0]))
				child=root;
			}
			else if(length==3)
			{
				child=root.getChild(array[2]);
			}
			else
			{
			for (int i = 1; i < length - 1; i++) {
				child = root.getChild(array[i]);
			}
			}

			text=child.getChildText(array[length - 1]);
			return text;
		
	}*/
public String getElementText(String xpath){
	
	String text=null;
//	try {
//		xmldoc = builder.build(xFile);
//		Element s = xmldoc.getRootElement();
		Element child = null;

		String[] array = xpath.split("/");
		int length = array.length;
		if(length<=1){
			log.error("invalid xpath expression");
			Assert.fail("Invalid xpath expression");
		}
		if(length==2)
		{
//			if(root.getName().equals(array[0]))
			child=root.getChild(array[1]);
		}
		else if(length==3)
		{
			child=root.getChild(array[2]);
		}
		else
		{
			child = root.getChild(array[length - 1]);
		}

		text=child.getText();
		return text;
	
}
public String getAttributeValue(String xpath, String attributeName){
		
		String text=null;
//		try {
//			xmldoc = builder.build(xFile);
//			Element s = xmldoc.getRootElement();
		Element child = null;

		String[] array = xpath.split("/");
		int length = array.length;
		if(length<=1){
			log.error("invalid xpath expression");
			Assert.fail("Invalid xpath expression");
		}
		if(length==2){
			if(root.getName().equals(array[0]))
			child=root;
		}
		else if(length==3)
		{
			child=root.getChild(array[2]);
		}
		else
		{
		for (int i = 1; i < length - 1; i++) {
			child = root.getChild(array[i]);
		}
		}
			text=child.getAttributeValue(attributeName).toString();
			return text;
		
	}
public List<String> getChildrenElementText(String xpath){
	
	List<String> childernText=null;
//	try {
//		xmldoc = builder.build(xFile);
//		Element s = xmldoc.getRootElement();
		Element child = null;

		String[] array = xpath.split("/");
		int length = array.length;
		for (int i = 1; i < length - 1; i++) {
			child = root.getChild(array[i]);
		}

		for(Element el:child.getChildren()){
			System.out.println(el.getTextTrim());
			childernText.add(el.getTextTrim());
		}
		return childernText;
	
}

		 public List<String> getChildrenElementsText(String xpath){
			List<String> childernText = new ArrayList<>();
				try{
					Element child = null;
					String[] array = xpath.split("/");
					int length = array.length;
					for (int i = 0, j=1; i < length - 1; i++,j++) {
						child = root.getChild(array[j]);
					}
					for(Element el:root.getChildren()){
						System.out.println(el.getTextTrim());
						childernText.add(el.getTextTrim());
					}
				}
				catch(Exception e)
		
				{
					
					log.error("Some exception occured while getting children text "+ e);
					System.out.println(e);
				}
		
					return childernText;
			   }
		 
	public XMLManager(String xmlRecords) {
		try
		{
				Document parseddoc = new SAXBuilder().build(new StringReader(xmlRecords.toString()));
			    root=parseddoc.getRootElement();
		}
		catch(JDOMException jdome){
			log.error("exception while converting string to xml object "+UtilityMethods.getStackTrace());
//			Assert.fail("exception while converting string to xml object "+jdome.getCause());
		}
		catch(IOException ioe){
			log.error("exception while converting string to xml object "+UtilityMethods.getStackTrace());
			Assert.fail("exception while converting string to xml object "+ioe.getMessage());
		}
		catch(Exception e){
			log.error("exception while converting string to xml object "+UtilityMethods.getStackTrace());
//			Assert.fail("exception while converting string to xml object "+e.getCause());
		}
	}

	/**
	 * Purpose - To get value of the child of first node in the given Xpath
	 * 
	 * @param xmlFilePath
	 *            - Xml file location
	 * @param path
	 *            - Xpath Expression
	 * @return String - Value of the first child
	 */

	public String readXmlContent(String path) {
		String value = null;
		try {
			xmldoc = builder.build(xFile);
			rootNode = xmldoc.getRootElement();

			String[] array = path.split("/");
			int last_element = array.length;

			Element s = rootNode;
			for (int i = 1; i < last_element; i++)
				s = s.getChild(array[i]);
			value = s.getText();
		} catch (JDOMException | IOException e) {
			log.error("Exception while reading XML content from file - "
					+ xFile + e.getMessage()
					+ UtilityMethods.getStackTrace());
			Assert.fail("Exception while reading XML content from file - "
					+ xFile + e.getMessage());
		}catch (NullPointerException e) {
			log.error("Unable to get Text using  "+ path +" from file"
					+ xFile + e.getMessage());
			Assert.fail("Unable to get Text using  "+ path +" from file"
					+ xFile + e.getMessage());
		}
		return value;
	}

	/**
	 * Purpose - To add Content to the Xml File
	 * 
	 * @param keypath
	 *            - Xpath Expression
	 * @param value
	 *            - Value for the node
	 * @param xmlFilePath
	 *            - Xml file location
	 */
	public void addXmlContent(String keyPath, String value) {
		try {
			xmldoc = builder.build(xFile);
			rootNode = xmldoc.getRootElement();

			String[] array = keyPath.split("/");
			int last_element = array.length;

			Element new_node = new Element(array[(last_element - 1)])
					.setText(value);

			Element s = rootNode;
			for (int i = 1; i < last_element - 1; i++) {
				s = s.getChild(array[i]);
			}
			s.addContent(new_node);
			updateFile(xmldoc, xFile);
		} catch (JDOMException | IOException e) {
			log.error("Exception while writing XML content to file - "
					+ xFile + e.getMessage()
					+ UtilityMethods.getStackTrace());
			Assert.fail("Exception while writing XML content to file - "
					+ xFile + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}catch (NullPointerException e) {
			log.error("Unable to get Text using  "+ keyPath +" from file"
					+ xFile + e.getMessage()
					+ UtilityMethods.getStackTrace());
			Assert.fail("Unable to get Text using  "+ keyPath +" from file"
					+ xFile + e.getMessage());
		}catch (IllegalNameException e) {
			log.error(keyPath +" will accept only alphanumeric and blackslash to write XML content from file"
					+ xFile + e.getMessage());
			Assert.fail(keyPath +" will accept alphanumeric and blackslash to write XML content from file"
					+ xFile + e.getMessage());
		}
	}

	/**
	 * Purpose - To remove Content from Xml File
	 * 
	 * @param keypath
	 *            - Xpath Expression
	 * @param xmlFilePath
	 *            - Xml file location
	 */
	public void removeXmlContent(String keyPath) {
		try {
			// xFile = new File(xmlFilePath);
			xmldoc = builder.build(xFile);
			rootNode = xmldoc.getRootElement();
			String[] array = keyPath.split("/");
			int last_element = array.length;
			Element s = rootNode;
			for (int i = 1; i < last_element - 1; i++) {
				s = s.getChild(array[i]);
			}
			s.removeChild(array[(last_element - 1)]);
			updateFile(xmldoc, xFile);
		} catch (JDOMException | IOException e) {
			log.error("Exception while removing XML content from file - "
					+ xFile + e.getMessage()
					+ UtilityMethods.getStackTrace());
			Assert.fail("Exception while removing XML content from file - "
					+ xFile + e.getMessage());
		}catch (NullPointerException e) {
			log.error("Unable to remove Text using  "+ keyPath +" from file"
					+ xFile + e.getMessage()
					+ UtilityMethods.getStackTrace());
			Assert.fail("Unable to remove Text using  "+ keyPath +" from file"
					+ xFile + e.getMessage());
		}
	}

	/**
	 * Purpose - To Update existing node value
	 * 
	 * @param keypath
	 *            - Xpath Expression
	 * @param newvalue
	 *            - new Value for the node
	 * @param xmlFilePath
	 *            - Xml file location
	 */
	public void updateXmlContent(String keyPath, String newvalue) {
		try {
			xmldoc = builder.build(xFile);
			rootNode = xmldoc.getRootElement();

			String[] array = keyPath.split("/");
			int last_element = array.length;

			Element s = rootNode;
			for (int i = 1; i < last_element; i++) {
				s = s.getChild(array[i]);
			}
			s.setText(newvalue);
			updateFile(xmldoc, xFile);
		} catch (JDOMException | IOException e) {
			log.error("XML  file - " + xFile
					+ " not found or no permissions to update the file"
					+ e.getMessage() + UtilityMethods.getStackTrace());
			Assert.fail("XML  file - " + xFile
					+ " not found or no permissions to update the file"
					+ e.getMessage());
		}catch (NullPointerException e) {
			log.error("Unable to update Text using  "+ keyPath +" from file"
					+ e.getMessage()
					+ UtilityMethods.getStackTrace());
			Assert.fail("Unable to update Text using  "+ keyPath +" from file"
					+ e.getMessage());
		}
	}

	/**
	 * Purpose - This method will update the xml file
	 * 
	 * @param xmlDoc
	 * @param xFile - Xml file path
	 */
	public void updateFile(Document xmlDoc, File xFile) {
		try {
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(xmlDoc, new FileWriter(xFile));
		} catch (IOException e) {
			log.error("XML  file - " + xFile
					+ " not found or no permissions to update the file"
					+ e.getMessage() + UtilityMethods.getStackTrace());
			Assert.fail("XML  file - " + xFile
					+ " not found or no permissions to update the file"
					+ e.getMessage());
		}
	}

	/**
	 * Purpose - This method will return elements of the given xpath
	 * 
	 * @param xpath - Xpath Expression
	 * @return - children for the matched Xpath
	 */
	public List<Element> getNodes(String xpath) {
		
		List<Element> nodes=null;
		try {
			xmldoc = builder.build(xFile);
			Element s = xmldoc.getRootElement();
			Element child = null;

			String[] array = xpath.split("/");
			int length = array.length;
			for (int i = 1; i < length - 1; i++) {
				child = s.getChild(array[i]);
			}

			nodes=child.getChildren(array[length - 1]);
		} catch (JDOMException | IOException e) {
			log.error("XML  file - " + xFile
					+ " not found or no permissions to read the file"
					+ e.getMessage() + UtilityMethods.getStackTrace());
			Assert.fail("XML  file - " + xFile
					+ " not found or no permissions to read the file"
					+ e.getMessage() + UtilityMethods.getStackTrace());
		}catch (NullPointerException e) {
			log.error("XML  file - " + xFile
					+ " not found or no permissions to read the file"
					+ e.getMessage());
			Assert.fail("XML  file - " + xFile
					+ " not found or no permissions to read the file"
					+ e.getMessage());
		}
		return nodes;
	}

	/**
	 * Purpose - This method will read values of children for the given element and returns as a list
	 * 
	 * @param Element - element that has children
	 * @return - List of Children values 
	 */
	public List<String> getChildrenText(Element element) {
		List<String> children = new ArrayList<String>();
		try {
			for (Element child : element.getChildren()) {
				children.add(child.getText());
			}
			
		} catch (NullPointerException e) {
			log.error("Exception Occur while reading children values for the element :" + element
					+ UtilityMethods.getStackTrace());
			Assert.fail("Exception Occur while reading children values for the element :"+ element);
		}
		return children;
	}

	/**
	 * Purpose - This method will identify all the matching nodes for the given xpath expression and read values of children for the matched nodes 
	 * 
	 * @param xpath - Xpath expression for of the Children node
	 * @return Array with values of children's of the node for the matched Xpath
	 */

	public String[][] getChildrenData(String xpath) {
		String[][] array = null;
		try {
			List<Element> nodes = getNodes(xpath);
			int nodeCount = nodes.size();
			int childrenCount = nodes.get(0).getChildren().size();
			array = new String[nodeCount][childrenCount];
			int i = 0;
			for (Element ele : nodes) {

				List<String> test = getChildrenText(ele);
				for (int j = 0; j < test.size(); j++) {
					array[i][j] = test.get(j);
				}
				i++;
			}
		} catch (Exception e) {
			log.error("Unable to get Children Text using  " + xpath + " "
					+ UtilityMethods.getStackTrace());
			Assert.fail("Unable to get Children Text using  " + xpath);
		}
		return array;
	}
	
	//...
	 public void readXML() throws ParserConfigurationException, XPathExpressionException{
	try {
//	    String xml = null;
//	    File fXmlFile = new File(System.getProperty("user.dir")+fileseperator+"Automation Reports"+fileseperator+"LatestResults"+fileseperator+"Response.xml");
//		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//		Document doc = (Document) dBuilder.parse(fXmlFile);
//		doc.getRootElement().getTextNormalize();
//
//		System.out.println("Root element :" + doc.getRootElement().getName());
	    
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
}
	 public void getAllUserNames() {
	        try {
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();
//	            File file = new File("Response.xml");
//	            if (file.exists()) {
	                org.w3c.dom.Document doc = db.parse(System.getProperty("user.dir")+fileseperator+"Automation Reports"+fileseperator+"LatestResults"+fileseperator+"Response.xml");
	                org.w3c.dom.Element docEle = doc.getDocumentElement();
	 
	             // Create XPathFactory for creating XPath Object
	                XPathFactory xPathFactory = XPathFactory.newInstance();

	                // Create XPath object from XPathFactory
	                XPath xpath = xPathFactory.newXPath();

	                // Compile the XPath expression for getting all brands
	                XPathExpression xPathExpr = xpath.compile("/xs:documentation");
	             
	                // XPath text example : executing xpath expression in java
	                Object result = xPathExpr.evaluate(docEle, XPathConstants.NODESET);
	                printXpathResult(result);
	                System.out.println(result.toString());

	       //javarevisited.blogspot.com/2012/12/create-and-evaluate-xpath-java-example-tutorial-program.html#ixzz3xqzXYoRU

	                
	                // Print root element of the document
//	                System.out.println("Root element of the document: " + docEle.getNodeName());
	                System.out.println("Text content" + doc.getLastChild());
	                System.out.println("Text content" + docEle.getElementsByTagName("xs:annotation/xs:documentation").toString());
//	                NodeList studentList = docEle.getElementsByTagName("student");
//	 
//	                // Print total student elements in document
//	                System.out
//	                        .println("Total students: " + studentList.getLength());
//	 
//	                if (studentList != null && studentList.getLength() > 0) {
//	                    for (int i = 0; i < studentList.getLength(); i++) {
//	 
//	                        Node node = studentList.item(i);
//	 
//	                        if (node.getNodeType() == Node.ELEMENT_NODE) {
//	 
//	                            System.out
//	                                    .println("=====================");
//	 
//	                            Element e = (Element) node;
//	                            NodeList nodeList = e.getElementsByTagName("name");
//	                            System.out.println("Name: "
//	                                    + nodeList.item(0).getChildNodes().item(0)
//	                                            .getNodeValue());
//	 
//	                            nodeList = e.getElementsByTagName("grade");
//	                            System.out.println("Grade: "
//	                                    + nodeList.item(0).getChildNodes().item(0)
//	                                            .getNodeValue());
//	 
//	                            nodeList = e.getElementsByTagName("age");
//	                            System.out.println("Age: "
//	                                    + nodeList.item(0).getChildNodes().item(0)
//	                                            .getNodeValue());
//	                        }
//	                    }
//	                } else {
//	                    System.exit(1);
//	                }
//	            }
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	 public void printXpathResult(Object result){
	        NodeList nodes = (NodeList) result;
	        for (int i = 0; i < nodes.getLength(); i++) {
	            System.out.println(nodes.item(i).getNodeValue());
	        }
	    }


	 public XMLManager(String str,String str2) {
		 
			fileeeeeePath=str2;
		}
	   public void initObjects(String expression,QName returnType) throws XPathExpressionException{
		 
		 	      try {
		 
		 	    	 Document     xmlDocument = (Document) DocumentBuilderFactory.
		 
		 	        newInstance().newDocumentBuilder().parse(fileeeeeePath);
		 
		 	    	XPath    xPath =  XPathFactory.newInstance().
		 
		 	        newXPath();
		 	    	XPathExpression xPathExpression =xPath.compile(expression);
		 	    			
		 	    				       Object str4=xPathExpression.evaluate(xmlDocument, returnType);
		 	    				       
		 
		 	      } catch (IOException ex) {
		 
		 	        ex.printStackTrace();
		 
		 	      } catch (SAXException ex) {
		 
		        ex.printStackTrace();
		 
		 	      } catch (ParserConfigurationException ex) {
		 
		 	        ex.printStackTrace();
		 
		 	      }
		 
		 	  }
}