package domgeb15i1105;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

public class DomQuery1GEB15I {

    public static void main(String[] args) {
        try {
           
            File xmlFile = new File("orarendGEB15I.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            
            System.out.println("--- 1. Feladat: Kurzusnevek ---");
            List<String> kurzusok = new ArrayList<>();
            NodeList targyList = doc.getElementsByTagName("targy");

            for (int i = 0; i < targyList.getLength(); i++) {
                kurzusok.add(targyList.item(i).getTextContent());
            }
            System.out.println("Kurzusnév: " + kurzusok);

            
            System.out.println("\n--- 2. Feladat: Első óra kiírása ---");
            Node firstOra = doc.getElementsByTagName("ora").item(0);

            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); 

            DOMSource source = new DOMSource(firstOra);

            
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            
            StreamResult fileResult = new StreamResult(new File("elsoOraGEB15I.xml"));
            transformer.transform(source, fileResult);
            System.out.println("\n(Az első óra mentve az 'elsoOraGEB15I.xml' fájlba.)");

            
            System.out.println("\n--- 3. Feladat: Oktatók nevei (egyedi) ---");
            List<String> oktatok = new ArrayList<>();
            NodeList oktatoList = doc.getElementsByTagName("oktato");

            for (int i = 0; i < oktatoList.getLength(); i++) {
                String oktatoNeve = oktatoList.item(i).getTextContent();
                
                if (!oktatok.contains(oktatoNeve)) {
                    oktatok.add(oktatoNeve);
                }
            }
            System.out.println("Oktatók: " + oktatok);

            
            System.out.println("\n--- 4. Feladat: Összetett lekérdezés (Dr. Agárdi Anita órái) ---");
            NodeList oraList = doc.getElementsByTagName("ora");

            for (int i = 0; i < oraList.getLength(); i++) {
                Node oraNode = oraList.item(i);
                if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElement = (Element) oraNode;

                   
                    String oktato = oraElement.getElementsByTagName("oktato").item(0).getTextContent();

                    
                    if ("Dr. Agárdi Anita".equals(oktato)) {
                        String targy = oraElement.getElementsByTagName("targy").item(0).getTextContent();
                        String nap = oraElement.getElementsByTagName("nap").item(0).getTextContent();
                        String tol = oraElement.getElementsByTagName("tol").item(0).getTextContent();
                        String tipus = oraElement.getAttribute("tipus");

                        System.out.println("  -> " + targy + " (" + tipus + ") - " + nap + " " + tol);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}