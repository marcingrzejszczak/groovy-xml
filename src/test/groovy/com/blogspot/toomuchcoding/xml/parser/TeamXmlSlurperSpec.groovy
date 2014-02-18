package com.blogspot.toomuchcoding.xml.parser

import spock.lang.Specification

class TeamXmlSlurperSpec extends Specification {

    def "should parse and print barcelona in Groovy"() {
        when:
            def team = new XmlSlurper().parse(ClassLoader.getSystemResourceAsStream("barcelona_squad.xml"))
        then:
            team.players.player.each { println "Player name [$it.name], surname [$it.surname], position [${it.@position}], number [$it.number]" }
    }

}
