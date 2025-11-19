package xpathgeb15i;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class xPathModifyGEB15I {

    public static void main(String[] args) {
        try {
            // 1) XML fájl beolvasása
            File xmlFile = new File("studentGEB15I.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // 2) XPath létrehozása
            XPath xPath = XPathFactory.newInstance().newXPath();

            // 3) Az id='01' attribútummal rendelkező student keresése
            // Megjegyzés: Ez feltételezi, hogy a <student> tagen van id attribútum!
            String expression = "/class/student[@id='01']";
            Node studentNode = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);

            if (studentNode != null && studentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element studentElement = (Element) studentNode;

                // 4) A keresztnév megkeresése a struktúrában
                // Az XML szerkezete alapján: student -> id (tag) -> keresztnev
                Node idNode = studentElement.getElementsByTagName("id").item(0);
                
                if (idNode != null && idNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element idElement = (Element) idNode;
                    Node keresztnevNode = idElement.getElementsByTagName("keresztnev").item(0);

                    if (keresztnevNode != null) {
                        // Módosítás végrehajtása
                        System.out.println("Régi név: " + keresztnevNode.getTextContent());
                        keresztnevNode.setTextContent("MódosítottDávid"); // Az új név
                    }
                }

                // 5) Csak a módosított példány (node) kiírása a konzolra
                System.out.println("--- Módosított példány: ---");
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                
                // Formázott kimenet beállítása
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

                DOMSource source = new DOMSource(studentNode);
                StreamResult consoleResult = new StreamResult(System.out);
                transformer.transform(source, consoleResult);

            } else {
                System.out.println("Nem található 'student' elem id='01' attribútummal.");
                System.out.println("Ellenőrizd, hogy módosítottad-e az XML fájlt az attribútum hozzáadásához!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}