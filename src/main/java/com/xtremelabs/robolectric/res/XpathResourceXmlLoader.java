package com.xtremelabs.robolectric.res;

import java.io.File;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class XpathResourceXmlLoader extends XmlLoader {
    private String expression;

    public XpathResourceXmlLoader(ResourceExtractor resourceExtractor, String expression) {
        super(resourceExtractor);
        this.expression = expression;
    }

    @Override protected void processResourceXml(File xmlFile, Document document, boolean isSystem) throws Exception {
        XPathExpression stringsXPath = XPathFactory.newInstance().newXPath().compile(expression);
        NodeList nodes = (NodeList) stringsXPath.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            String name = node.getAttributes().getNamedItem("name").getNodeValue();
            processNode(node, name, isSystem);
        }
    }

    protected abstract void processNode(Node node, String name, boolean isSystem) throws XPathExpressionException;
}
