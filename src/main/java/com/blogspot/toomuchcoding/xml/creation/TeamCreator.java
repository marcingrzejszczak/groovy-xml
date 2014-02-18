package com.blogspot.toomuchcoding.xml.creation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * <team name="FCBarcelona">
 * <players>
 * <player position="forward">
 * <name>Lionel</name>
 * <surname>Messi</surname>
 * <number>10</number>
 * </player>
 * <player position="forward">
 * <name>Neymar</name>
 * <surname>Neymar</surname>
 * <number>11</number>
 * </player>
 * <player position="midfielder">
 * <name>Xavi</name>
 * <surname/>
 * <number>6</number>
 * </player>
 * <player position="midfielder">
 * <name>Andrés</name>
 * <surname>Iniesta</surname>
 * <number>8</number>
 * </player>
 * </players>
 * </team>
 */

public class TeamCreator {

    public String createTeam() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element team = document.createElement("team");
            team.setAttribute("name", "FCBarcelona");
            document.appendChild(team);

            Element players = document.createElement("players");
            team.appendChild(players);

            Element messi = document.createElement("player");
            messi.setAttribute("position", "forward");
            messi.appendChild(document.createElement("name")).appendChild(document.createTextNode("Lionel"));
            messi.appendChild(document.createElement("surname")).appendChild(document.createTextNode("Messi"));
            messi.appendChild(document.createElement("number")).appendChild(document.createTextNode("10"));
            players.appendChild(messi);

            Element neymar = document.createElement("player");
            neymar.setAttribute("position", "forward");
            neymar.appendChild(document.createElement("name")).appendChild(document.createTextNode("Neymar"));
            neymar.appendChild(document.createElement("surname")).appendChild(document.createTextNode("Neymar"));
            neymar.appendChild(document.createElement("number")).appendChild(document.createTextNode("11"));
            players.appendChild(neymar);

            Element xavi = document.createElement("player");
            xavi.setAttribute("position", "midfielder");
            xavi.appendChild(document.createElement("name")).appendChild(document.createTextNode("Xavi"));
            xavi.appendChild(document.createElement("surname").appendChild(document.createTextNode("")));
            xavi.appendChild(document.createElement("number")).appendChild(document.createTextNode("6"));
            players.appendChild(xavi);

            Element iniesta = document.createElement("player");
            iniesta.setAttribute("position", "midfielder");
            iniesta.appendChild(document.createElement("name")).appendChild(document.createTextNode("Andres"));
            iniesta.appendChild(document.createElement("surname")).appendChild(document.createTextNode("Iniesta"));
            iniesta.appendChild(document.createElement("number")).appendChild(document.createTextNode("8"));
            players.appendChild(iniesta);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);
            DOMSource source = new DOMSource(document);
            transformer.transform(source, streamResult);
            return stringWriter.toString();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
