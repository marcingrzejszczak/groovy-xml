package com.blogspot.toomuchcoding.xml.parser;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TeamDomFromStringParser {

    public void parseXmlFromString(String xml) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            Element team = document.getDocumentElement();
            System.out.println("Displaying squad for team " + team.getAttribute("name") + "\n");
            printPlayers(team);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void printPlayers(Element team) {
        NodeList players = team.getElementsByTagName("player");
        for (int i = 0; i < players.getLength(); i++) {
            Element player = (Element) players.item(i);
            String playerString = "Name: " + getTextNode("name", player) + "\n" +
                                  "Surname: " + getTextNode("surname", player) + "\n" +
                                  "Number: " + getTextNode("number", player) + "\n" +
                                  "Position: " + player.getAttribute("position") + "\n";
            System.out.println(playerString);
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
