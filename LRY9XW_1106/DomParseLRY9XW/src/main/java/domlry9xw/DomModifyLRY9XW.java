package domlry9xw;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomModifyLRY9XW {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("VendeglatasLRY9XW.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            modifyVendeg(doc);
            modifyGyakornok(doc, 0);

        } catch (ParserConfigurationException ex) {
            System.err.println("Hiba a DocumentBuilder letrehozasakor: " + ex.getMessage());
            ex.printStackTrace();
        } catch (SAXException ex) {
            System.err.println("Hiba az XML fajl feldolgozasakor: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("Hiba az XML fajl beolvasasakor: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void modifyVendeg(Document doc) {
        try {
            NodeList nList = doc.getElementsByTagName("vendeg");

            for (int i = 0; i < nList.getLength(); i++) {
                Node vendeg = doc.getElementsByTagName("vendeg").item(i);

                if (vendeg.getNodeType() != Node.ELEMENT_NODE
                        || !"v1".equals(((Element) vendeg).getAttribute("id"))) {
                    continue;
                }

                NodeList list = vendeg.getChildNodes();

                for (int temp = 0; temp < list.getLength(); temp++) {
                    Node node = list.item(temp);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;

                        if ("kor".equals(eElement.getNodeName())) {
                            eElement.setTextContent("22");
                        }

                        if ("nev".equals(eElement.getNodeName())) {
                            eElement.setTextContent("Kiss Tamas");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Hiba a vendeg modositasakor: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void modifyGyakornok(Document doc, int index) {
        try {
            Node gyakornok = doc.getElementsByTagName("gyakornok").item(index);

            if (gyakornok == null) {
                throw new IllegalArgumentException(
                        "Nincs gyakornok ezen az indexen: " + index);
            }

            // Modify
            Element gyakornokElement = (Element) gyakornok;
            gyakornokElement.setAttribute("e_gy", "e3");

            // Delete
            NodeList rendelesek = doc.getElementsByTagName("rendeles");
            while (rendelesek.getLength() > 0) {
                Node rendeles = rendelesek.item(0);
                rendeles.getParentNode().removeChild(rendeles);
            }

            // Write
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            System.out.println("--- Modositott XML fajl ---");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (TransformerException ex) {
            System.err.println("Hiba az XML kiirasakor: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Hiba a gyakornok modositasakor: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
