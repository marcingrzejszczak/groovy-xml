package com.blogspot.toomuchcoding.xml._05_update

import com.blogspot.toomuchcoding.xml.xmlunit.XmlComparator
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
import org.custommonkey.xmlunit.XMLUnit
import spock.lang.Specification

class TeamUpdatorXmlSlurperSpec extends Specification {

    def setupSpec() {
        XmlComparator.setupXmlUnit()
    }

    def "should update an xml with a couple of FCBarcelona players"() {
        given:
            String inputXml = '''
                                <team name="FCBarcelona">
                                    <players>
                                        <player position="forward">
                                            <name>Lionel</name>
                                            <surname>Messi</surname>
                                            <number>10</number>
                                        </player>
                                        <player position="forward">
                                            <name>Neymar</name>
                                            <surname>Neymar</surname>
                                            <number>11</number>
                                        </player>
                                        <player position="midfielder">
                                            <name>Xavi</name>
                                            <surname>Xavi</surname>
                                            <number>6</number>
                                        </player>
                                        <player position="midfielder">
                                            <name>Andres</name>
                                            <surname>Iniesta</surname>
                                            <number>8</number>
                                        </player>
                                    </players>
                                </team>
                                '''
        and:
            String desiredXml = '''
                                <team name="FCBarcelona">
                                    <players>
                                        <player position="forward">
                                            <name>Lionel</name>
                                            <surname>ChangedMessi</surname>
                                            <number>10</number>
                                        </player>
                                        <player position="goalkeeper">
                                            <name>Neymar</name>
                                            <surname>Neymar</surname>
                                            <number>11</number>
                                        </player>
                                        <player position="midfielder">
                                            <name>Andres</name>
                                            <surname>Iniesta</surname>
                                        </player>
                                        <player position="midfielder">
                                            <name>Marcin</name>
                                            <surname>Grzejszczak</surname>
                                        </player>
                                    </players>
                                </team>
                                '''
        and:
            def team = new XmlSlurper().parseText(inputXml)
        and:
            def players = team.players.player
            players.find { it.surname == "Messi" }.surname = "ChangedMessi"
            players.find { it.surname == "Neymar" }.@position = "goalkeeper"
            players.find { it.surname == "Iniesta" }.number.replaceNode {}
            players.find { it.surname == "Xavi" }.replaceNode {}
            team.players.appendNode {
                player(position: "midfielder") {
                    name("Marcin")
                    surname("Grzejszczak")
                }
            }
        when:
            String outputXml = XmlUtil.serialize(new StreamingMarkupBuilder().bindNode(team).toString())
        then:
            XMLUnit.compareXML(desiredXml, outputXml).similar()
    }

    def "should fail to find appended node using XmlSlurper"() {
        given:
            String inputXml = '''
                                <team name="FCBarcelona">
                                    <players>
                                        <player position="forward">
                                            <name>Lionel</name>
                                            <surname>Messi</surname>
                                            <number>10</number>
                                        </player>
                                        <player position="forward">
                                            <name>Neymar</name>
                                            <surname>Neymar</surname>
                                            <number>11</number>
                                        </player>
                                        <player position="midfielder">
                                            <name>Xavi</name>
                                            <surname>Xavi</surname>
                                            <number>6</number>
                                        </player>
                                        <player position="midfielder">
                                            <name>Andres</name>
                                            <surname>Iniesta</surname>
                                            <number>8</number>
                                        </player>
                                    </players>
                                </team>
                                '''
        and:
            def team = new XmlSlurper().parseText(inputXml)
        and:
            team.players.appendNode {
                player(position: "midfielder") {
                    name("Marcin")
                    surname("Grzejszczak")
                }
            }
        when:
            def marcin = team.players.player.find {it.name == "Marcin"}
        then:
            !marcin
    }

    def "should find appended node using XmlParser"() {
        given:
            String inputXml = '''
                                <team name="FCBarcelona">
                                    <players>
                                        <player position="forward">
                                            <name>Lionel</name>
                                            <surname>Messi</surname>
                                            <number>10</number>
                                        </player>
                                        <player position="forward">
                                            <name>Neymar</name>
                                            <surname>Neymar</surname>
                                            <number>11</number>
                                        </player>
                                        <player position="midfielder">
                                            <name>Xavi</name>
                                            <surname>Xavi</surname>
                                            <number>6</number>
                                        </player>
                                        <player position="midfielder">
                                            <name>Andres</name>
                                            <surname>Iniesta</surname>
                                            <number>8</number>
                                        </player>
                                    </players>
                                </team>
                                '''
        and:
            def team = new XmlParser().parseText(inputXml)
            def marcin = team.players[0]?.appendNode('player', [position:"midfielder"])
            marcin.appendNode('name', 'Marcin')
            marcin.appendNode('surname', 'Grzejszczak')
        when:
            def foundMarcin = team.players.player.find {it.name[0].value() == "Marcin"}
        then:
            foundMarcin != null
    }
}
