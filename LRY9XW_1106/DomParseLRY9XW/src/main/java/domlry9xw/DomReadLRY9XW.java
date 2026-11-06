package domlry9xw;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class DomReadLRY9XW {

    private DomReadLRY9XW() {
    }

    public static void main(String[] args) throws Exception {
        Path input = SecureDomSupport.resolveInput(args);
        Document document = read(input);
        System.out.write(toBlockFormat(document).getBytes(StandardCharsets.UTF_8));
    }

    public static Document read(Path input)
            throws ParserConfigurationException, IOException, SAXException {
        return SecureDomSupport.parse(input);
    }

    public static String toBlockFormat(Document document) {
        Objects.requireNonNull(document, "document");
        StringBuilder output = new StringBuilder();
        appendElement(document.getDocumentElement(), 0, output);
        return output.toString();
    }

    private static void appendElement(
            Element element,
            int depth,
            StringBuilder output
    ) {
        List<Element> children = childElements(element);
        String indentation = "  ".repeat(depth);
        String attributes = formatAttributes(element.getAttributes());

        if (children.isEmpty()) {
            output.append(indentation)
                    .append(element.getTagName())
                    .append(attributes)
                    .append(": ")
                    .append(normalizeText(element.getTextContent()))
                    .append(System.lineSeparator());
            return;
        }

        output.append(indentation)
                .append(element.getTagName())
                .append(attributes)
                .append(System.lineSeparator())
                .append(indentation)
                .append('{')
                .append(System.lineSeparator());

        for (Element child : children) {
            appendElement(child, depth + 1, output);
        }

        output.append(indentation)
                .append('}')
                .append(System.lineSeparator());
    }

    private static List<Element> childElements(Element parent) {
        NodeList nodes = parent.getChildNodes();
        List<Element> elements = new ArrayList<>();
        for (int index = 0; index < nodes.getLength(); index++) {
            Node node = nodes.item(index);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                elements.add((Element) node);
            }
        }
        return elements;
    }

    private static String formatAttributes(NamedNodeMap attributes) {
        if (attributes.getLength() == 0) {
            return "";
        }

        List<String> formatted = new ArrayList<>();
        for (int index = 0; index < attributes.getLength(); index++) {
            Node attribute = attributes.item(index);
            formatted.add(
                    "@" + attribute.getNodeName()
                            + "=\"" + attribute.getNodeValue() + "\""
            );
        }
        formatted.sort(Comparator.naturalOrder());
        return " [" + String.join(", ", formatted) + "]";
    }

    private static String normalizeText(String value) {
        return value.strip().replaceAll("\\s+", " ");
    }
}
