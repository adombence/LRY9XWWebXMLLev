package domlry9xw;

import java.io.File;
import java.io.IOException;
import java.util.StringJoiner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQueryLRY9XW {

    private static final String KERESETT_VEGZETTSEG = "szakkozepiskola";

    public static void main(String[] args)
            throws ParserConfigurationException, SAXException, IOException {

        File xmlFile = new File("VendeglatasLRY9XW.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        document.getDocumentElement().normalize();

        // Queries
        querySzakacsok(document);
    }

    private static void querySzakacsok(Document document) {
        System.out.println("Szakkozepiskolai vegzettsegu szakacsok:");

        NodeList szakacsok = document.getElementsByTagName("szakacs");
        boolean vanTalalat = false;

        for (int i = 0; i < szakacsok.getLength(); i++) {
            Element szakacs = (Element) szakacsok.item(i);

            if (!containsText(
                    szakacs,
                    "vegzettseg",
                    KERESETT_VEGZETTSEG)) {
                continue;
            }

            vanTalalat = true;
            String etteremId = szakacs.getAttribute("e_sz");

            System.out.println();
            System.out.println("ID: " + szakacs.getAttribute("id"));
            System.out.println("Nev: " + firstText(szakacs, "nev"));
            System.out.println(
                    "Munkahely: " + findEtteremName(document, etteremId));
            System.out.println(
                    "Reszleg: " + firstText(szakacs, "reszleg"));
            System.out.println(
                    "Egyeb vegzettsegek: "
                            + otherQualifications(
                                    szakacs,
                                    KERESETT_VEGZETTSEG));
        }

        if (!vanTalalat) {
            System.out.println("Nincs talalat.");
        }
    }

    private static String findEtteremName(
            Document document,
            String etteremId) {
        NodeList ettermek = document.getElementsByTagName("etterem");

        for (int i = 0; i < ettermek.getLength(); i++) {
            Element etterem = (Element) ettermek.item(i);

            if (etteremId.equals(etterem.getAttribute("id"))) {
                return firstText(etterem, "nev");
            }
        }

        return "Ismeretlen etterem";
    }

    private static String otherQualifications(
            Element szakacs,
            String excludedQualification) {
        NodeList qualifications = szakacs.getElementsByTagName("vegzettseg");
        StringJoiner result = new StringJoiner(", ");

        for (int i = 0; i < qualifications.getLength(); i++) {
            String qualification = qualifications.item(i).getTextContent().trim();

            if (!excludedQualification.equalsIgnoreCase(qualification)) {
                result.add(qualification);
            }
        }

        return result.length() == 0 ? "nincs" : result.toString();
    }

    private static boolean containsText(
            Element parent,
            String tagName,
            String expected) {
        NodeList elements = parent.getElementsByTagName(tagName);

        for (int i = 0; i < elements.getLength(); i++) {
            String value = elements.item(i).getTextContent().trim();

            if (expected.equalsIgnoreCase(value)) {
                return true;
            }
        }

        return false;
    }

    private static String firstText(Element parent, String tagName) {
        NodeList elements = parent.getElementsByTagName(tagName);

        if (elements.getLength() == 0) {
            return "";
        }

        return elements.item(0).getTextContent().trim();
    }
}
