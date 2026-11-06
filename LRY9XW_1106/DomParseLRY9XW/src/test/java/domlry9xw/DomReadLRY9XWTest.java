package domlry9xw;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomReadLRY9XWTest {

    @TempDir
    Path tempDir;

    @Test
    void readsAndPrintsTheWholeDocumentInBlockFormat() throws Exception {
        Document document = DomReadLRY9XW.read(resource("LRY9XW_XML.xml"));
        String output = DomReadLRY9XW.toBlockFormat(document);

        assertAll(
                () -> assertEquals(
                        "LRY9XW_Vendeglatas",
                        document.getDocumentElement().getTagName()
                ),
                () -> assertEquals(
                        3,
                        document.getElementsByTagName("etterem").getLength()
                ),
                () -> assertEquals(
                        3,
                        document.getElementsByTagName("foszakacs").getLength()
                ),
                () -> assertEquals(
                        4,
                        document.getElementsByTagName("szakacs").getLength()
                ),
                () -> assertEquals(
                        3,
                        document.getElementsByTagName("gyakornok").getLength()
                ),
                () -> assertEquals(
                        3,
                        document.getElementsByTagName("vendeg").getLength()
                ),
                () -> assertEquals(
                        5,
                        document.getElementsByTagName("rendeles").getLength()
                ),
                () -> assertTrue(output.startsWith("LRY9XW_Vendeglatas")),
                () -> assertTrue(output.contains("etterem [@id=\"e1\"]")),
                () -> assertTrue(output.contains("nev: Bükk Étterem")),
                () -> assertTrue(output.contains("vegzettseg: HACCP képzés")),
                () -> assertTrue(output.contains("muszak: hétvége")),
                () -> assertTrue(output.contains(
                        "rendeles [@etterem_ref=\"e1\", @id=\"r1\", "
                                + "@vendeg_ref=\"v1\"]"
                )),
                () -> assertTrue(output.contains(
                        "etel: Szarvasragu dödöllével"
                ))
        );
    }

    @Test
    void rejectsDoctypeAndExternalEntityInput() throws Exception {
        Path malicious = tempDir.resolve("external-entity.xml");
        Files.writeString(
                malicious,
                """
                        <?xml version="1.0"?>
                        <!DOCTYPE data [
                          <!ENTITY external SYSTEM "file:///etc/passwd">
                        ]>
                        <data>&external;</data>
                        """
        );

        assertThrows(
                SAXException.class,
                () -> DomReadLRY9XW.read(malicious)
        );
    }

    private Path resource(String name) throws URISyntaxException {
        return Path.of(Objects.requireNonNull(
                getClass().getResource("/" + name),
                "Hiányzó teszterőforrás: " + name
        ).toURI());
    }
}
