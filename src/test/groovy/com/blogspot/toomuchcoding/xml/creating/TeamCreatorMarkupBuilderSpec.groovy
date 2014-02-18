package com.blogspot.toomuchcoding.xml.creating

import com.blogspot.toomuchcoding.xml.xmlunit.XmlComparator
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.tools.xml.DomToGroovy
import org.custommonkey.xmlunit.XMLUnit
import org.w3c.dom.Document
import spock.lang.Specification

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class TeamCreatorMarkupBuilderSpec extends Specification {

    def setupSpec() {
        XmlComparator.setupXmlUnit()
    }
    
    def "should create an xml with a couple of FCBarcelona players"() {
        given:
            String desiredXml = '''<?xml version="1.0" encoding="UTF-8"?>
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
                                                <surname/>
                                                <number>6</number>
                                            </player>
                                            <player position="midfielder">
                                                <name>Andres</name>
                                                <surname>Iniesta</surname>
                                                <number>8</number>
                                            </player>
                                        </players>
                                    </team>'''
        and:
            def writer = new StringWriter()
            def xml = new MarkupBuilder(writer)
        and:
            xml.team(name: "FCBarcelona") {
                players {
                    player(position: "forward"){
                        name("Lionel")
                        surname("Messi")
                        number(10)
                    }
                    player(position: "forward"){
                        name("Neymar")
                        surname("Neymar")
                        number(11)
                    }
                    player(position: "midfielder"){
                        name("Xavi")
                        surname()
                        number(6)
                    }
                    player(position: "midfielder"){
                        name("Andres")
                        surname("Iniesta")
                        number(8)
                    }
                }
            }
        when:
            String actualXml = writer.toString()
        then:
            XMLUnit.compareXML(desiredXml, actualXml).similar()
    }

    def "should print markup builder syntax to produce the given xml"() {
        given:
            String barcelonaSquad = """
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
                                                <surname/>
                                                <number>6</number>
                                            </player>
                                            <player position="midfielder">
                                                <name>Andrés</name>
                                                <surname>Iniesta</surname>
                                                <number>8</number>
                                            </player>
                                        </players>
                                    </team>
                                    """
        and:
            String desiredMarkup =
                """
                team(name:'FCBarcelona') {
                  players() {
                    player(position:'forward') {
                      name('Lionel')
                      surname('Messi')
                      number('10')
                    }
                    player(position:'forward') {
                      name('Neymar')
                      surname('Neymar')
                      number('11')
                    }
                    player(position:'midfielder') {
                      name('Xavi')
                      surname()
                      number('6')
                    }
                    player(position:'midfielder') {
                      name('Andrés')
                      surname('Iniesta')
                      number('8')
                    }
                  }
                }
                """
        and:
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            ByteArrayInputStream inputStream = new ByteArrayInputStream(barcelonaSquad.bytes)
            Document document = builder.parse(inputStream)
            StringWriter output = new StringWriter()
            DomToGroovy converter = new DomToGroovy(new PrintWriter(output))
            converter.print(document)
        when:
            String outputMarkup = output.toString()
        then:
            outputMarkup.replaceAll(" ","").trim() == desiredMarkup.toString().replaceAll(" ","").trim()
    }


    def "should create a html document"() {
        given:
            String website = '''
                                <HTML>
                                    <HEAD>
                                        <TITLE>A cool website</TITLE>
                                    </HEAD>
                                    <BODY>
                                        <HR/>
                                        <P>
                                            <A HREF="http://toomuchcoding.blogspot.com">BLOG</A>
                                            <H1>WGUG</H1>
                                            <H2>This is a medium header of groovy presentation</H2>
                                            <SPAN>
                                                And here we have a link <a href="mailto:mail@mail.com">mail@mail.com</a>
                                            </SPAN>
                                        </P>
                                        <P>
                                            <B>One paragraph</B>
                                        </P>
                                        <BR/>
                                        <B>
                                            <I>Another paragraph.</I>
                                        </B>
                                        <HR/>
                                    </BODY>
                                </HTML>
                             '''
        and:
            def writer = new StringWriter()
            def xml = new MarkupBuilder(writer)
        when:
            xml.HTML {
                HEAD {
                    TITLE('A cool website')
                }
                BODY {
                    HR()
                    P {
                        A(HREF:'http://toomuchcoding.blogspot.com', 'BLOG')
                        H1('WGUG')
                        H2('This is a medium header of groovy presentation')
                        SPAN {
                            mkp.yield('And here we have a link')
                            a(href:'mailto:mail@mail.com', 'mail@mail.com')
                        }
                    }
                    P {
                        B('One paragraph')
                    }
                    BR()
                    B {
                        I('Another paragraph.')
                    }
                    HR()
                }
            }
        then:
            XMLUnit.compareXML(website, writer.toString()).similar()
    }
}
