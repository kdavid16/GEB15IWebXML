package domgeb15i1029;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomReadGEB15I1 {

    public static void main(String[] args) {
        File xmlFile = new File("orarendGEB15I.xml");
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("XML fa 'blokk' formátumban (GEB15I - orarend):");
            System.out.println("---------------------------------------------");
            printNodeTree(doc.getDocumentElement(), 0);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void printNodeTree(Node node, int indent) {
        if (node.getNodeType() == Node.TEXT_NODE && node.getNodeValue().trim().isEmpty()) {
            return;
        }
        String indentStr = "";
        for (int i = 0; i < indent; i++) {
            indentStr += "  ";
        }
        System.out.print(indentStr);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            System.out.print("<" + element.getNodeName());
            if (element.hasAttributes()) {
                org.w3c.dom.NamedNodeMap attributes = element.getAttributes();
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node attr = attributes.item(j);
                    System.out.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
                }
            }
            System.out.println(">");
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            System.out.println(node.getNodeValue().trim());
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            printNodeTree(childNodes.item(i), indent + 1);
        }
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(indentStr + "</" + node.getNodeName() + ">");
        }
    }
}