package com.blogspot.toomuchcoding.xml._01_parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class TeamSaxParser {

    public void parseXml(String xmlPath) {
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            PlayerSAXHandler handler = new PlayerSAXHandler();
            parser.parse(ClassLoader.getSystemResourceAsStream(xmlPath), handler);
            printPlayers(handler);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void printPlayers(PlayerSAXHandler handler) {
        for ( PlayerSAXHandler.Player player : handler.players){
            System.out.println(player);
        }
    }

    class PlayerSAXHandler extends DefaultHandler {

        List<Player> players = new ArrayList<Player>();

        Player player = new Player();

        String content = null;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("player")) {
                player = new Player();
                player.position = attributes.getValue("position");
            }
        }

        @Override
        public void endElement(String uri, String localName,
                               String qName) throws SAXException {

            if (qName.equals("name")) {
                player.name = content;
            } else if (qName.equals("surname")) {
                player.surname = content;
            } else if (qName.equals("number")) {
                player.number = content;
            } else if (qName.equals("player")) {
                players.add(player);
            }

        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            content = String.copyValueOf(ch, start, length).trim();
        }


        class Player {

            String name;

            String surname;

            String position;

            String number;

            @Override
            public String toString() {
                return "Player{" +
                        "name='" + name + '\'' +
                        ", surname='" + surname + '\'' +
                        ", position='" + position + '\'' +
                        ", number='" + number + '\'' +
                        '}';
            }
        }
    }

}

