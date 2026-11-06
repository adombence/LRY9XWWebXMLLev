package domlry9xw;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlSchemaValidationTest {

    @Test
    void validHospitalityXmlPassesSchemaValidation() throws Exception {
        assertDoesNotThrow(() -> validate(
                resource("LRY9XW_XML.xml"),
                resource("LRY9XW_XMLSchema.xsd")
        ));
    }

    @Test
    void negativeAgeFailsWithAClearValidationError() throws Exception {
        SAXException exception = assertThrows(
                SAXException.class,
                () -> validate(
                        resource("invalid-negative-age.xml"),
                        resource("LRY9XW_XMLSchema.xsd")
                )
        );

        String message = exception.getMessage();
        assertTrue(
                message.contains("KorTipus")
                        || message.contains("nonNegativeInteger")
                        || message.contains("minInclusive"),
                () -> "Nem egyértelmű validációs hiba: " + message
        );
    }

    static void validate(Path xml, Path xsd) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(
                XMLConstants.W3C_XML_SCHEMA_NS_URI
        );
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        Schema schema = factory.newSchema(xsd.toFile());
        Validator validator = schema.newValidator();
        validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        validator.validate(new StreamSource(xml.toFile()));
    }

    private Path resource(String name) throws URISyntaxException {
        return Path.of(Objects.requireNonNull(
                getClass().getResource("/" + name),
                "Hiányzó teszterőforrás: " + name
        ).toURI());
    }
}
