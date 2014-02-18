package com.blogspot.toomuchcoding.xml.creating

import com.blogspot.toomuchcoding.xml.creation.TeamCreator
import com.blogspot.toomuchcoding.xml.xmlunit.XmlComparator
import org.custommonkey.xmlunit.XMLUnit
import spock.lang.Specification

class TeamCreatorSpec extends Specification {

    def setupSpec() {
        XmlComparator.setupXmlUnit()
    }

    def "should create an xml with a couple of FCBarcelona players"() {
        given:
            String desiredXml = '''
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
        when:
            String outputXml = new TeamCreator().createTeam()
        then:
           XMLUnit.compareXML(desiredXml, outputXml).similar()

    }
}
