package xpathgeb15i;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

public class xPathQueryGEB15I1 {

    public static void main(String[] args) {
        try {
            // 1. XML dokumentum betöltése
            File xmlFile = new File("orarendGEB15I.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // XPath objektum létrehozása
            XPath xPath = XPathFactory.newInstance().newXPath();

            System.out.println("=== LEKÉRDEZÉSEK ===");

            // -------------------------------------------------------
            // A) LEKÉRDEZÉS: Milyen órát tart "Dr. Bednarik László"?
            // -------------------------------------------------------
            String expr1 = "//ora[oktato='Dr. Bednarik László']/targy";
            String targy = (String) xPath.compile(expr1).evaluate(doc, XPathConstants.STRING);
            
            System.out.println("1. Milyen órát tart Dr. Bednarik László?");
            if (targy != null && !targy.isEmpty()) {
                System.out.println("   Válasz: " + targy);
            } else {
                System.out.println("   Válasz: Nem található ilyen oktató vagy tárgy.");
            }

            // -------------------------------------------------------
            // B) LEKÉRDEZÉS: Mikor van "Szoftvertechnológia" óra?
            // -------------------------------------------------------
            // Megjegyzés: Az XML-ben nincs "Szoftvertechnológia", így ez várhatóan nem ad találatot.
            String expr2 = "//ora[targy='Szoftvertechnológia']/idopont";
            Node idopontNode = (Node) xPath.compile(expr2).evaluate(doc, XPathConstants.NODE);

            System.out.println("2. Mikor van 'Szoftvertechnológia' óra?");
            if (idopontNode != null) {
                Element e = (Element) idopontNode;
                String nap = e.getElementsByTagName("nap").item(0).getTextContent();
                String tol = e.getElementsByTagName("tol").item(0).getTextContent();
                String ig = e.getElementsByTagName("ig").item(0).getTextContent();
                System.out.println("   Válasz: " + nap + " " + tol + "-" + ig);
            } else {
                System.out.println("   Válasz: Nincs ilyen tárgy az órarendben.");
            }

            // -------------------------------------------------------
            // C) LEKÉRDEZÉS: Van-e óra csütörtökön?
            // -------------------------------------------------------
            String expr3 = "//ora[idopont/nap='Csütörtök']";
            NodeList csutortokiOrak = (NodeList) xPath.compile(expr3).evaluate(doc, XPathConstants.NODESET);

            System.out.println("3. Van-e óra csütörtökön?");
            if (csutortokiOrak.getLength() > 0) {
                System.out.println("   Válasz: Igen, " + csutortokiOrak.getLength() + " óra van.");
            } else {
                System.out.println("   Válasz: Nincs óra csütörtökön.");
            }
            // -------------------------------------------------------
            // MENTÉS ÉS KIÍRÁS
            // -------------------------------------------------------
            System.out.println("\n=== TELJES MÓDOSÍTOTT XML KIÍRÁSA ÉS MENTÉSE ===");
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Formázott kimenet beállítása (szép tördelés)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            
            // 1. Kiírás konzolra
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            // 2. Mentés fájlba (orarendGEB15I1.xml)
            File outputFile = new File("orarendGEB15I1.xml");
            StreamResult fileResult = new StreamResult(new FileOutputStream(outputFile));
            transformer.transform(source, fileResult);
            
            System.out.println("\n\nFájl sikeresen mentve: " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}