package xpathlry9xw;

import java.lang.annotation.ElementType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xPathLRY9XW {
	public static void main(String[] args) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = dBuilder.parse("studentLRY9XW.xml");

			XPath xPath = XPathFactory.newInstance().newXPath();

			String neptunCode = "/class/student[@id = '02']";

			NodeList neptunCodeList = (NodeList) xPath.compile(neptunCode).evaluate(document,
					XPathConstants.NODESET);

			for (int i = 0; i < neptunCodeList.getLength(); i++) {
				Node node = neptunCodeList.item(i);

				System.out.println("\nAtkualis elem: " + node.getNodeName());

				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
					Element element = (Element) node;

					System.out.println("Student ID: " + element.getAttribute("id"));
					System.out.println("Firstname: " + element.getElementsByTagName("keresztnev")
							.item(0).getTextContent());
					System.out.println("Lastname: " + element.getElementsByTagName("vezeteknev")
							.item(0).getTextContent());
					System.out.println("Nickname: " + element.getElementsByTagName("becenev")
							.item(0).getTextContent());
					System.out.println("Age: "
							+ element.getElementsByTagName("kor").item(0).getTextContent());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
