package domgeb15i1105;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomQueryGEB15I {

    public static void main(String[] args) {

        try {
           
            File inputFile = new File("GEB15Ihallgato.xml");

            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            
            doc.getDocumentElement().normalize();

            
            System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());
            System.out.println("------------------------");

            
            NodeList nList = doc.getElementsByTagName("hallgato");

            
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                
                System.out.println("\nAktuális elem: " + nNode.getNodeName());

                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String vezeteknev = eElement.getElementsByTagName("vezeteknev")
                                                .item(0)
                                                .getTextContent();
                    
                    System.out.println("vezeteknev: " + vezeteknev);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}