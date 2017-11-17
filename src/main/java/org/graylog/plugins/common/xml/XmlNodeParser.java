package org.graylog.plugins.common.xml;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author Stepan Pogosyan
 * @since 11.11.2017.
 */
public class XmlNodeParser {

    public Node parseFromString(String xml) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return documentBuilder.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new RuntimeException("Unable to parse xml from string", e);
        }
    }

    public String parseFromNode(Node xml) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(xml), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (Exception e) {
            throw new RuntimeException("Unable to parse xml from dom node", e);
        }
    }

}
