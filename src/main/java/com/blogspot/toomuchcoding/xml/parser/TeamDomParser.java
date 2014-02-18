package com.blogspot.toomuchcoding.xml.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class TeamDomParser {

    public void parseXml(String xmlPath) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(ClassLoader.getSystemResourceAsStream(xmlPath));
            Element team = document.getDocumentElement();
            System.out.println("Displaying squad for team " + team.getAttribute("name") + "\n");
            NodeList players = team.getElementsByTagName("player");
            for (int i = 0; i < players.getLength(); i++) {
                Element player = (Element) players.item(i);
                String playerString = "Name: " + getTextNode("name", player) + "\n" +
                        "Surname: " + getTextNode("surname", player) + "\n" +
                        "Number: " + getTextNode("number", player) + "\n" +
                        "Position: " + player.getAttribute("position") + "\n";
                System.out.println(playerString);
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private String getTextNode(String tagName, Element player) {
        NodeList name = player.getElementsByTagName(tagName);
        if (name.getLength() > 0) {
            return name.item(0).getTextContent();
        }
        return "";
    }
}
