package domgeb15i1105;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomModify1GEB15I {

    public static void main(String[] args) {
        try {
           
            File xmlFile = new File("orarendGEB15I.xml"); //
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            
            Node firstOraNode = doc.getElementsByTagName("ora").item(0);

            if (firstOraNode.getNodeType() == Node.ELEMENT_NODE) {
                Element firstOraElement = (Element) firstOraNode;

                
                Element oraado = doc.createElement("oraado");
                oraado.setTextContent("Dr. Radics Attila"); 
                firstOraElement.appendChild(oraado);
            }

            

            NodeList oraList = doc.getElementsByTagName("ora");

            for (int i = 0; i < oraList.getLength(); i++) {
                Node oraNode = oraList.item(i);
                if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElement = (Element) oraNode;

                    
                    String tipus = oraElement.getAttribute("tipus");

                    
                    if ("gyakorlat".equals(tipus)) {
                        oraElement.setAttribute("tipus", "elmelet");
                    }
                }
            }

            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);

            
            System.out.println("--- Fájl mentése (orarendModify1Neptunkod.xml) a VÉGSŐ állapottal... ---");
            StreamResult fileResult = new StreamResult(new File("orarendModify1GEB15I.xml"));
            transformer.transform(source, fileResult);
            System.out.println("Mentés kész.");


            
            System.out.println("\n--- Végső XML kiírása a konzolra ---");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}