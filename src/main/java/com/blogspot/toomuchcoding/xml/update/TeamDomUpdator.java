package com.blogspot.toomuchcoding.xml.update;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class TeamDomUpdator {

    public String updateXml(String xml) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
            Element team = document.getDocumentElement();
            NodeList players = team.getElementsByTagName("player");
            for (int i = 0; i < players.getLength(); i++) {
                Element player = (Element) players.item(i);
	            Node surnameNode = getNodeByTagName("surname", player);
	            if(surnameNode == null){
		            continue;
	            }
	            if("Messi".equalsIgnoreCase(surnameNode.getTextContent())){
		            surnameNode.getFirstChild().setNodeValue("ChangedMessi");
	            } else if("Xavi".equalsIgnoreCase(surnameNode.getTextContent())){
		            player.getParentNode().removeChild(player);
		            i--;
	            } else if("Neymar".equalsIgnoreCase(surnameNode.getTextContent())){
		            player.setAttribute("position", "goalkeeper");
	            } else if("Iniesta".equalsIgnoreCase(surnameNode.getTextContent())){
		            player.removeChild(getNodeByTagName("number", player));
	            }
            }

	        Element marcin = document.createElement("player");
	        marcin.setAttribute("position", "forward");
	        marcin.appendChild(document.createElement("name")).appendChild(document.createTextNode("Marcin"));
	        marcin.appendChild(document.createElement("surname")).appendChild(document.createTextNode("Grzejszczak"));
	        team.getFirstChild().appendChild(marcin);
	        
	        OutputFormat format = new OutputFormat(document);
	        Writer out = new StringWriter();
	        XMLSerializer serializer = new XMLSerializer(out, format);
	        serializer.serialize(document);
	        return out.toString();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private Node getNodeByTagName(String tagName, Element player) {
        NodeList nodeList = player.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0);
        }
        return null;
    }
}
