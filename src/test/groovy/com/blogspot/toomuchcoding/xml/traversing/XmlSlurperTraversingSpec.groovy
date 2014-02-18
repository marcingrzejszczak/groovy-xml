package com.blogspot.toomuchcoding.xml.traversing

import spock.lang.Specification

class XmlSlurperTraversingSpec extends Specification {

    def "should return surnames of all players followed by '_BARCELONA' suffix who are forwards and have number greater than 10"() {
        given:
            def team = new XmlSlurper().parse(ClassLoader.getSystemResourceAsStream("barcelona_squad.xml"))
        when:
            List<String> playerNames = team.players.player.findAll({ it.@position == "forward" && it.number.toInteger() > 10 }).inject([], { playerNames, player -> playerNames << "${player.surname}_BARCELONA" })
        then:
            playerNames == ["Neymar_BARCELONA", "Tello_BARCELONA", "Cuenca_BARCELONA"]

    }

}
