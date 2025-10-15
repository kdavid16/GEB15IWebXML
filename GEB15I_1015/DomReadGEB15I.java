
package domGEB15I1015;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DomReadGEB15I {

    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            File xmlFile = new File("src/domGEB15I1015/orarendGEB15I.xml");
            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            System.out.println("Gyökér elem: " + document.getDocumentElement().getNodeName());
            System.out.println("------------------------------------------");

            NodeList oraList = document.getElementsByTagName("ora");

            for (int i = 0; i < oraList.getLength(); i++) {
                Node oraNode = oraList.item(i);

                if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElement = (Element) oraNode;

                    String id = oraElement.getAttribute("id");
                    String tipus = oraElement.getAttribute("tipus");

                    String targy = getElementTextContent(oraElement, "targy");
                    String oktato = getElementTextContent(oraElement, "oktato");
                    String szak = getElementTextContent(oraElement, "szak");
                    String helyszin = getElementTextContent(oraElement, "helyszin");

                    Node idopontNode = oraElement.getElementsByTagName("idopont").item(0);
                    String nap = "";
                    String tol = "";
                    String ig = "";

                    if (idopontNode != null && idopontNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element idopontElement = (Element) idopontNode;
                        nap = getElementTextContent(idopontElement, "nap");
                        tol = getElementTextContent(idopontElement, "tol");
                        ig = getElementTextContent(idopontElement, "ig");
                    }

                    System.out.println("Óra (ID: " + id + ", Típus: " + tipus + ")");
                    System.out.println("  Tárgy: " + targy);
                    System.out.println("  Időpont: " + nap + ", " + tol + "-" + ig);
                    System.out.println("  Helyszín: " + helyszin);
                    System.out.println("  Oktató: " + oktato);
                    System.out.println("  Szak: " + szak);
                    System.out.println(); 
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static String getElementTextContent(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}