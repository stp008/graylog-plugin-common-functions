package org.graylog.plugins.common.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.function.Function;

/**
 * @author Stepan Pogosyan
 * @since 11.11.2017.
 */
public class XPathMatcher {

    public Node replaceAll(String expression, Node source, Function<String, String> function) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xPath.evaluate(expression, source, XPathConstants.NODESET);

            for(int i = 0; i < list.getLength(); i++) {
                Node matchedNode = list.item(i);
                matchedNode.setTextContent(function.apply(matchedNode.getTextContent()));
            }

            return source;
        } catch (Exception e) {
            throw new RuntimeException("Unable to evaluate XPath", e);
        }
    }

}
