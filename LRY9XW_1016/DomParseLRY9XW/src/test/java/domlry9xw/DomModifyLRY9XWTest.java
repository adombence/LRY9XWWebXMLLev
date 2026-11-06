package domlry9xw;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DomModifyLRY9XWTest {

    @TempDir
    Path tempDir;

    @Test
    void performsAllFourDomOperationsWithoutChangingTheInputFile()
            throws Exception {
        Path source = resource("LRY9XW_XML.xml");
        Path input = tempDir.resolve("original.xml");
        Files.copy(source, input);
        byte[] originalBytes = Files.readAllBytes(input);

        Document document = DomReadLRY9XW.read(input);
        DomModifyLRY9XW.ModificationResult result =
                DomModifyLRY9XW.modify(document);

        Element guest = findById(document, "vendeg", "v1");
        Element firstIntern =
                (Element) document.getElementsByTagName("gyakornok").item(0);
        Element secondIntern =
                (Element) document.getElementsByTagName("gyakornok").item(1);

        assertAll(
                () -> assertEquals(
                        DomModifyLRY9XW.MODIFIED_GUEST_NAME,
                        directChildText(guest, "nev")
                ),
                () -> assertEquals(
                        DomModifyLRY9XW.MODIFIED_GUEST_AGE,
                        directChildText(guest, "kor")
                ),
                () -> assertEquals(
                        DomModifyLRY9XW.MODIFIED_RESTAURANT_REFERENCE,
                        firstIntern.getAttribute("e_gy")
                ),
                () -> assertEquals(
                        "e2",
                        secondIntern.getAttribute("e_gy"),
                        "Csak a kijelölt első gyakornok változzon."
                ),
                () -> assertEquals(
                        0,
                        document.getElementsByTagName("rendeles").getLength()
                ),
                () -> assertEquals("v1", result.guestId()),
                () -> assertEquals("gy1", result.internId()),
                () -> assertEquals(5, result.removedOrders()),
                () -> assertArrayEquals(
                        originalBytes,
                        Files.readAllBytes(input),
                        "Az eredeti XML nem írható felül."
                )
        );

        Path modified = tempDir.resolve("modified.xml");
        Files.writeString(
                modified,
                SecureDomSupport.toXml(document),
                StandardCharsets.UTF_8
        );

        assertAll(
                () -> assertDoesNotThrow(
                        () -> DomReadLRY9XW.read(modified)
                ),
                () -> assertDoesNotThrow(
                        () -> XmlSchemaValidationTest.validate(
                                modified,
                                resource("LRY9XW_XMLSchema.xsd")
                        )
                )
        );
    }

    private Element findById(Document document, String tagName, String id) {
        NodeList elements = document.getElementsByTagName(tagName);
        for (int index = 0; index < elements.getLength(); index++) {
            Element element = (Element) elements.item(index);
            if (id.equals(element.getAttribute("id"))) {
                return element;
            }
        }
        throw new AssertionError("Nem található " + tagName + " id=" + id);
    }

    private String directChildText(Element parent, String tagName) {
        NodeList children = parent.getChildNodes();
        for (int index = 0; index < children.getLength(); index++) {
            Node child = children.item(index);
            if (child.getNodeType() == Node.ELEMENT_NODE
                    && tagName.equals(child.getNodeName())) {
                return child.getTextContent();
            }
        }
        throw new AssertionError("Hiányzó közvetlen gyermek: " + tagName);
    }

    private Path resource(String name) throws URISyntaxException {
        return Path.of(Objects.requireNonNull(
                getClass().getResource("/" + name),
                "Hiányzó teszterőforrás: " + name
        ).toURI());
    }
}
