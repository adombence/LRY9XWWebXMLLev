package domlry9xw;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

final class SecureDomSupport {

    private static final String DISALLOW_DOCTYPE =
            "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String EXTERNAL_GENERAL_ENTITIES =
            "http://xml.org/sax/features/external-general-entities";
    private static final String EXTERNAL_PARAMETER_ENTITIES =
            "http://xml.org/sax/features/external-parameter-entities";
    private static final String LOAD_EXTERNAL_DTD =
            "http://apache.org/xml/features/nonvalidating/load-external-dtd";
    private static final String INDENT_AMOUNT =
            "{http://xml.apache.org/xslt}indent-amount";

    private SecureDomSupport() {
    }

    static Document parse(Path input)
            throws ParserConfigurationException, IOException, SAXException {
        Objects.requireNonNull(input, "input");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature(DISALLOW_DOCTYPE, true);
        factory.setFeature(EXTERNAL_GENERAL_ENTITIES, false);
        factory.setFeature(EXTERNAL_PARAMETER_ENTITIES, false);
        factory.setFeature(LOAD_EXTERNAL_DTD, false);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ThrowingErrorHandler());
        Document document = builder.parse(input.toFile());
        document.getDocumentElement().normalize();
        return document;
    }

    static String toXml(Document document) throws TransformerException {
        Objects.requireNonNull(document, "document");
        removeWhitespaceTextNodes(document.getDocumentElement());

        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty(INDENT_AMOUNT, "2");

        StringWriter output = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(output));
        return output.toString();
    }

    private static void removeWhitespaceTextNodes(Node parent) {
        NodeList children = parent.getChildNodes();
        for (int index = children.getLength() - 1; index >= 0; index--) {
            Node child = children.item(index);
            if (child.getNodeType() == Node.TEXT_NODE
                    && child.getTextContent().isBlank()) {
                parent.removeChild(child);
            } else if (child.hasChildNodes()) {
                removeWhitespaceTextNodes(child);
            }
        }
    }

    static Path resolveInput(String[] args) {
        Objects.requireNonNull(args, "args");
        if (args.length > 1) {
            throw new IllegalArgumentException(
                    "Használat: <program> [XML-fájl]"
            );
        }
        if (args.length == 1) {
            return requireRegularFile(Path.of(args[0]));
        }

        List<Path> candidates = List.of(
                Path.of("VendeglatasLRY9XW.xml"),
                Path.of("LRY9XWXML.xml"),
                Path.of("LRY9XW_XML.xml"),
                Path.of("..", "LRY9XW_XML.xml")
        );
        return candidates.stream()
                .filter(Files::isRegularFile)
                .findFirst()
                .map(Path::toAbsolutePath)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nem található az XML. Add meg parancssori argumentumként."
                ));
    }

    private static Path requireRegularFile(Path input) {
        Path absolute = input.toAbsolutePath().normalize();
        if (!Files.isRegularFile(absolute)) {
            throw new IllegalArgumentException(
                    "Nem olvasható XML-fájl: " + absolute
            );
        }
        return absolute;
    }

    private static final class ThrowingErrorHandler implements ErrorHandler {

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void fatalError(SAXParseException exception)
                throws SAXException {
            throw exception;
        }
    }
}
