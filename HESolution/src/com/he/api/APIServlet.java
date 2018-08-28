package com.he.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import com.he.api.ParamUtility;

// http://localhost:8080/HESolution/APIServlet?action=SearchById&id=1&firstName=ABC&lastName=DEF&low=0&high=2

/**
 * Servlet implementation class APIServlet
 */
@WebServlet("/API")
public class APIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html"); 
        PrintWriter out = response.getWriter(); 


//        Path currentRelativePath = Paths.get("");
//        String s = currentRelativePath.toAbsolutePath().toString()+"\\data.xml";
//        System.out.println("Current Path :"+s);
//		File fXmlFile = new File("F:\\WORK\\JavaEclipse\\HESolution\\src\\com\\he\\api\\data.xml");

        String action = ParamUtility.getValue(request,"action", null);
        String firstName = ParamUtility.getValue(request,"firstName", null);
        String lastName = ParamUtility.getValue(request,"lastName", null);
        int id = Integer.parseInt(ParamUtility.getValue(request,"id", "0").trim());
        int low = Integer.parseInt(ParamUtility.getValue(request,"low", "0").trim());
        int high = Integer.parseInt(ParamUtility.getValue(request,"high", "0").trim());
        
		try {
			out.println(generateJSONData(action, id, firstName, lastName, low, high));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		out.close();
	}
	
	String generateJSONData(String action,int id,String firstName,String lastName, int low, int high) {
		try {

//	        Path currentRelativePath = Paths.get("");
//	        String s = currentRelativePath.toAbsolutePath().toString()+"/data.xml";
			
			URL path = APIServlet.class.getResource("data.xml");
			File fXmlFile = new File(path.getFile());
//			File fXmlFile = new File("F:\\WORK\\JavaEclipse\\HESolution\\src\\com\\he\\api\\data.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
	
			NodeList nList = doc.getElementsByTagName("user");
			
			StringBuffer returnData = new StringBuffer("");

			List<JSONObject> list = new ArrayList<JSONObject>();
			if(action==null) return "";
			if(action.equals("searchById")) {

				if(id >= Integer.valueOf(root.getAttribute("id_auto_increment"))) return "";
				for (int temp = 0; temp < nList.getLength(); temp++) {

	
					Node nNode = nList.item(temp);
							
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
						Element eElement = (Element) nNode;
						if(Integer.parseInt(eElement.getAttribute("id").trim()) == id) {	
//							if(returnData.toString().equals("")) {
//								returnData.append("[{");
//							}
//							else {
//								returnData.append("<br>},{");
//							}
//
//							returnData.append("<br>\"id\": " + eElement.getAttribute("id") + ",");
//							returnData.append("<br>\"firstName\": \""  + eElement.getAttribute("firstName")+ "\",");
//							returnData.append("<br>\"lastName\": \""  + eElement.getAttribute("lastName")+ "\"");
//	
							JSONObject json = new JSONObject();
							json.put("id", Integer.parseInt(eElement.getAttribute("id")));
							json.put("firstName", eElement.getAttribute("firstName"));
							json.put("lastName", eElement.getAttribute("lastName"));
							
							list.add(json);
							
						}
						
					}
				}
				
				returnData.append(list.toString());
				
			} else if(action.equals("searchByFirstName")) {
				for (int temp = 0; temp < nList.getLength(); temp++) {
	
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
						Element eElement = (Element) nNode;
						if(eElement.getAttribute("firstName").equals(firstName)) {	
							JSONObject json = new JSONObject();
							json.put("id", Integer.parseInt(eElement.getAttribute("id")));
							json.put("firstName", eElement.getAttribute("firstName"));
							json.put("lastName", eElement.getAttribute("lastName"));
							
							list.add(json);
						}
						
					}
				}
				
				returnData.append(list.toString());
				
			} else if(action.equals("searchByLastName")) {
				for (int temp = 0; temp < nList.getLength(); temp++) {
	
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
						Element eElement = (Element) nNode;
						if(eElement.getAttribute("lastName").equals(lastName)) {	
							JSONObject json = new JSONObject();
							json.put("id", Integer.parseInt(eElement.getAttribute("id")));
							json.put("firstName", eElement.getAttribute("firstName"));
							json.put("lastName", eElement.getAttribute("lastName"));
							
							list.add(json);
						}
					}
				}
				returnData.append(list.toString());
				
			} else if(action.equals("searchByIdRange")) {
				if(high >= Integer.valueOf(root.getAttribute("id_auto_increment")) && low<1) return "";
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
							
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
						Element eElement = (Element) nNode;
						if(Integer.parseInt(eElement.getAttribute("id").trim()) >=low && Integer.parseInt(eElement.getAttribute("id").trim()) <=high) {	
							JSONObject json = new JSONObject();
							json.put("firstName", eElement.getAttribute("firstName"));
							json.put("lastName", eElement.getAttribute("lastName"));
							json.put("id", Integer.parseInt(eElement.getAttribute("id")));
							
							list.add(json);
						}
					}
				}
				
				returnData.append(list.toString());
			} else if(action.equals("updateUser")) {
	
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						if(Integer.parseInt(eElement.getAttribute("id").trim()) ==id) {	
							eElement.setAttribute("firstName", firstName);
							eElement.setAttribute("lastName", lastName);
							
							TransformerFactory transformerFactory = TransformerFactory.newInstance();
					        Transformer transformer = transformerFactory.newTransformer();
					        DOMSource source = new DOMSource(doc);

//					        Path currentRelativePath = Paths.get("");
//					        String s = currentRelativePath.toAbsolutePath().toString()+"/data.xml";
		        			// StreamResult result = new StreamResult(new File("F:\\WORK\\JavaEclipse\\HESolution\\src\\com\\he\\api\\data.xml"));
					        StreamResult result = new StreamResult(new File(path.getFile()));
					        transformer.transform(source, result);
						}
					}
				}
				
			} else if(action.equals("insertUser")) {
				Element user = doc.createElement("user");
				root.appendChild(user);
				
				user.setAttribute("id", root.getAttribute("id_auto_increment"));
				user.setAttribute("firstName", firstName);
				user.setAttribute("lastName", lastName);

				root.setAttribute("id_auto_increment", String.valueOf(Integer.valueOf(root.getAttribute("id_auto_increment"))+1));

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
		        Transformer transformer = transformerFactory.newTransformer();
		        DOMSource source = new DOMSource(doc);
//		        Path currentRelativePath = Paths.get("");
//		        String s = currentRelativePath.toAbsolutePath().toString()+"/data.xml";
				StreamResult result = new StreamResult(new File(path.getFile()));
		        // StreamResult result = new StreamResult(new File("F:\\WORK\\JavaEclipse\\HESolution\\src\\com\\he\\api\\data.xml"));
		        transformer.transform(source, result);
		        
			} else {
				return "";
			}
			
			
			return returnData.toString();
		} catch (Exception e) {
			System.out.println("Something went wrong");
	    	e.printStackTrace();
	    }
		return "";
		
	}


}

