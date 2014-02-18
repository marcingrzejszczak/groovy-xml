package com.blogspot.toomuchcoding.xml.update

import com.blogspot.toomuchcoding.xml.xmlunit.XmlComparator
import org.custommonkey.xmlunit.XMLUnit
import spock.lang.Specification

class TeamUpdatorSpec extends Specification {

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
        when:
            String outputXml = new TeamDomUpdator().updateXml(inputXml)
        then:
           XMLUnit.compareXML(desiredXml, outputXml).similar()

    }
}
