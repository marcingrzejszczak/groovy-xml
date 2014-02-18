package com.blogspot.toomuchcoding.xml.mapping

import com.blogspot.toomuchcoding.xml.xmlunit.XmlComparator
import groovy.xml.MarkupBuilder
import org.custommonkey.xmlunit.XMLUnit
import spock.lang.Specification

class MappingSpec extends Specification {

    def setupSpec() {
        XmlComparator.setupXmlUnit()
    }

    def "should map from one xml structure to another"() {
        given:
            String inputXml = '''
                                <team name="FCBarcelona">
                                    <players>
                                        <player position="forward">
                                            <name>Lionel</name>
                                            <surname>Messi</surname>
                                            <number>10</number>
                                            <valueInMln>100</valueInMln>
                                        </player>
                                        <player position="forward">
                                            <name>Neymar</name>
                                            <surname>Neymar</surname>
                                            <number>11</number>
                                            <valueInMln>70</valueInMln>
                                        </player>
                                        <player position="midfielder">
                                            <name>Xavi</name>
                                            <surname>Xavi</surname>
                                            <number>6</number>
                                            <valueInMln>50</valueInMln>
                                        </player>
                                        <player position="midfielder">
                                            <name>Andres</name>
                                            <surname>Iniesta</surname>
                                            <number>8</number>
                                            <valueInMln>60</valueInMln>
                                        </player>
                                    </players>
                                </team>
                                '''
         and:
            String desiredXml = '''
                                <FCBarcelona>
                                    <Squad>
                                        <SuperPlayer surname="Xavi" name="Xavi">
                                            <number>6</number>
                                            <position>midfielder</position>
                                        </SuperPlayer>
                                        <SuperPlayer surname="Iniesta" name="Andres">
                                            <number>8</number>
                                            <position>midfielder</position>
                                        </SuperPlayer>
                                        <SuperPlayer surname="Messi" name="Lionel">
                                            <number>10</number>
                                            <position>forward</position>
                                        </SuperPlayer>
                                        <SuperPlayer surname="Neymar" name="Neymar">
                                            <number>11</number>
                                            <position>forward</position>
                                        </SuperPlayer>
                                    </Squad>
                                    <Statistics>
                                        <Forwards count="2"/>
                                        <Midfielders count="2"/>
                                        <MaxNo>11</MaxNo>
                                        <MinNo>6</MinNo>
                                        <TotalValueInMln>280</TotalValueInMln>
                                    </Statistics>
                                </FCBarcelona>
                                '''
        and:
            def writer = new StringWriter()
            def xml = new MarkupBuilder(writer)
        and:
            def team = new XmlSlurper().parseText(inputXml)
        and:
            xml."${team.@name}" {
                Squad {
                    team.players.player.sort { it.number.toInteger() }.each { player ->
                        SuperPlayer(surname: player.surname, name: player.name) {
                            number(player.number)
                            position(player.@position)
                        }
                    }
                }
                Statistics {
                    Forwards(count: team.players.player.count { it.@position == "forward" })
                    Midfielders(count: team.players.player.count { it.@position == "midfielder" })
                    MaxNo(team.players.player.max { it.number.toInteger() }.number)
                    MinNo(team.players.player.min { it.number.toInteger() }.number)
                    TotalValueInMln(team.players.player.sum(0, { it.valueInMln.toLong() }))
                }
            }
        when:
            String outputXml = writer.toString()
        then:
           XMLUnit.compareXML(desiredXml, outputXml).similar()

    }
}
