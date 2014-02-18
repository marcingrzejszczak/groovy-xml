package com.blogspot.toomuchcoding.xml._02_traversing;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class XPathSearch {

    public NodeList findByXPath(String xmlPath, String xpathExpression) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(ClassLoader.getSystemResourceAsStream(xmlPath));
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(xpathExpression);
            return (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
