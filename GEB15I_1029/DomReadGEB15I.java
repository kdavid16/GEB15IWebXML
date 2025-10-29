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

public class DomReadGEB15I {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("hallgatoGEB15I.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());
            System.out.println("---------------------------------");

            NodeList nList = doc.getElementsByTagName("hallgato");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                System.out.println("\nAktuális elem: " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    
                    Element element = (Element) nNode;

                    
                    String hid = element.getAttribute("id");

                    
                    String kname = element.getElementsByTagName("keresztnev").item(0).getTextContent();
                    String vname = element.getElementsByTagName("vezeteknev").item(0).getTextContent();
                    String fname = element.getElementsByTagName("foglalkozas").item(0).getTextContent();

                    
                    System.out.println("Hallgató id: " + hid);
                    System.out.println("Keresztnév: " + kname);
                    System.out.println("Vezetéknév: " + vname);
                    System.out.println("Foglalkozás: " + fname);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}