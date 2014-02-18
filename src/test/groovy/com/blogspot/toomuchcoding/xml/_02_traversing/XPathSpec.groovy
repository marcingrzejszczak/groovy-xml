package com.blogspot.toomuchcoding.xml._02_traversing

import org.w3c.dom.Element
import spock.lang.Specification
import org.w3c.dom.NodeList
import org.w3c.dom.Node

class XPathSpec extends Specification {

    def "should return surnames of all players followed by '_BARCELONA' suffix who are forwards and have number greater than 10"() {
        given:
            NodeList players = new XPathSearch().findByXPath("barcelona_squad.xml", "//player[@position=\"forward\" and number > 10]")
        when:
            List<String> playerNames = getConvertedSurnamesOfPlayers(players)
        then:
            playerNames == ["Neymar_BARCELONA", "Tello_BARCELONA", "Cuenca_BARCELONA"]

    }

    private List<String> getConvertedSurnamesOfPlayers(NodeList players) {
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < players.getLength(); i++) {
            Node player = players.item(i);
            NodeList childNodes = player.getChildNodes();
            for (int j = 0; j != childNodes.getLength(); ++j) {
                Node child = childNodes.item(j);
                if (child.getNodeName().equals("surname")) {
                    names.add(child.getFirstChild().getNodeValue() + "_BARCELONA");
                }
            }
        }
        return names;
    }
}
