package app;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/** XML handling for the XML verb. Deliberately vulnerable smoke target; see README.md. */
public final class Xml {
    private Xml() {
    }

    /** Parses a document submitted by the client and returns its root element name. */
    public static String parse(String xml) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            return document.getDocumentElement().getTagName();
        } catch (ParserConfigurationException | SAXException error) {
            throw new IOException("malformed document", error);
        }
    }
}
