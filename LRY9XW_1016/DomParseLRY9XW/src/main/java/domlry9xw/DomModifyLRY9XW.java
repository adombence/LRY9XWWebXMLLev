package domlry9xw;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Objects;

public final class DomModifyLRY9XW {

    public static final String MODIFIED_GUEST_NAME = "Módosított Vendég";
    public static final String MODIFIED_GUEST_AGE = "35";
    public static final String MODIFIED_RESTAURANT_REFERENCE = "e3";

    private DomModifyLRY9XW() {
    }

    public static void main(String[] args) throws Exception {
        Path input = SecureDomSupport.resolveInput(args);
        Document document = DomReadLRY9XW.read(input);
        ModificationResult result = modify(document);

        System.err.printf(
                "Módosítva: vendég %s, gyakornok %s; törölt rendelések: %d%n",
                result.guestId(),
                result.internId(),
                result.removedOrders()
        );
        System.out.write(
                SecureDomSupport.toXml(document).getBytes(StandardCharsets.UTF_8)
        );
    }

    public static ModificationResult modify(Document document) {
        Objects.requireNonNull(document, "document");

        Element guest = findByAttribute(document, "vendeg", "id", "v1");
        Element guestName = requireDirectChild(guest, "nev");
        Element guestAge = requireDirectChild(guest, "kor");
        String oldGuestName = guestName.getTextContent();
        String oldGuestAge = guestAge.getTextContent();
        guestName.setTextContent(MODIFIED_GUEST_NAME);
        guestAge.setTextContent(MODIFIED_GUEST_AGE);

        NodeList interns = document.getElementsByTagName("gyakornok");
        if (interns.getLength() == 0) {
            throw new IllegalStateException("Nem található gyakornok elem.");
        }
        Element intern = (Element) interns.item(0);
        if (!intern.hasAttribute("e_gy")) {
            throw new IllegalStateException(
                    "A gyakornok elemnek nincs e_gy attribútuma."
            );
        }
        String oldRestaurantReference = intern.getAttribute("e_gy");
        intern.setAttribute("e_gy", MODIFIED_RESTAURANT_REFERENCE);

        int removedOrders = removeAll(document, "rendeles");
        document.getDocumentElement().normalize();

        return new ModificationResult(
                guest.getAttribute("id"),
                oldGuestName,
                oldGuestAge,
                intern.getAttribute("id"),
                oldRestaurantReference,
                removedOrders
        );
    }

    private static Element findByAttribute(
            Document document,
            String tagName,
            String attributeName,
            String expectedValue
    ) {
        NodeList elements = document.getElementsByTagName(tagName);
        for (int index = 0; index < elements.getLength(); index++) {
            Element element = (Element) elements.item(index);
            if (expectedValue.equals(element.getAttribute(attributeName))) {
                return element;
            }
        }
        throw new IllegalStateException(
                "Nem található: " + tagName + "[@" + attributeName
                        + "='" + expectedValue + "']"
        );
    }

    private static Element requireDirectChild(Element parent, String tagName) {
        NodeList children = parent.getChildNodes();
        for (int index = 0; index < children.getLength(); index++) {
            Node child = children.item(index);
            if (child.getNodeType() == Node.ELEMENT_NODE
                    && tagName.equals(child.getNodeName())) {
                return (Element) child;
            }
        }
        throw new IllegalStateException(
                "Hiányzó " + tagName + " elem a(z) "
                        + parent.getTagName() + " alatt."
        );
    }

    private static int removeAll(Document document, String tagName) {
        NodeList elements = document.getElementsByTagName(tagName);
        int removed = 0;
        while (elements.getLength() > 0) {
            Node element = elements.item(0);
            element.getParentNode().removeChild(element);
            removed++;
        }
        return removed;
    }

    public record ModificationResult(
            String guestId,
            String oldGuestName,
            String oldGuestAge,
            String internId,
            String oldRestaurantReference,
            int removedOrders
    ) {
    }
}
