package xpathgeb15i;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

public class xPathModifyGEB15I1 {

    public static void main(String[] args) {
        try {
            // 1. XML beolvasása (az eredeti fájlból)
            File xmlFile = new File("orarendGEB15I.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // XPath objektum létrehozása
            XPath xPath = XPathFactory.newInstance().newXPath();

            System.out.println("=== MÓDOSÍTÁSOK VÉGREHAJTÁSA ===");

            // -------------------------------------------------------
            // 1. MÓDOSÍTÁS: Törölje az id="11"-es órát
            // -------------------------------------------------------
            String deleteExpr = "//ora[@id='11']";
            Node nodeToDelete = (Node) xPath.compile(deleteExpr).evaluate(doc, XPathConstants.NODE);

            if (nodeToDelete != null) {
                // A törléshez a szülő csomópontot kell kérni, és onnan eltávolítani a gyereket
                nodeToDelete.getParentNode().removeChild(nodeToDelete);
                System.out.println("1. SIKERES TÖRLÉS: Az id='11' óra eltávolítva.");
            } else {
                System.out.println("1. HIBA: Nem található az id='11' óra.");
            }

            // -------------------------------------------------------
            // 2. MÓDOSÍTÁS: Módosítsd az id="0" óra nevét "Rosszabb fizika"-ra
            // -------------------------------------------------------
            String modifySubjectExpr = "//ora[@id='0']/targy";
            Node subjectNode = (Node) xPath.compile(modifySubjectExpr).evaluate(doc, XPathConstants.NODE);

            if (subjectNode != null) {
                String regiNev = subjectNode.getTextContent();
                subjectNode.setTextContent("Rosszabb fizika");
                System.out.println("2. SIKERES MÓDOSÍTÁS (id='0'):");
                System.out.println("   Régi tárgy: " + regiNev);
                System.out.println("   Új tárgy:   Rosszabb fizika");
            } else {
                System.out.println("2. HIBA: Nem található az id='0' óra vagy annak tárgya.");
            }

            // -------------------------------------------------------
            // 3. MÓDOSÍTÁS: Módosítsd az id="8" óra tanárát "Dr. Szabó Norbert"-re
            // -------------------------------------------------------
            String modifyTeacherExpr = "//ora[@id='8']/oktato";
            Node teacherNode = (Node) xPath.compile(modifyTeacherExpr).evaluate(doc, XPathConstants.NODE);

            if (teacherNode != null) {
                String regiTanar = teacherNode.getTextContent();
                teacherNode.setTextContent("Dr. Szabó Norbert");
                System.out.println("3. SIKERES MÓDOSÍTÁS (id='8'):");
                System.out.println("   Régi oktató: " + regiTanar);
                System.out.println("   Új oktató:   Dr. Szabó Norbert");
            } else {
                System.out.println("3. HIBA: Nem található az id='8' óra vagy annak oktatója.");
            }

            // -------------------------------------------------------
            // MENTÉS ÉS KIÍRÁS
            // -------------------------------------------------------
            System.out.println("\n=== EREDMÉNY KIÍRÁSA ÉS MENTÉSE ===");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Szép formázás beállítása
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            // Opcionális: XML deklaráció elhagyása, ha nem szükséges
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            DOMSource source = new DOMSource(doc);

            // 1. Kiírás a konzolra ellenőrzésképpen
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            // 2. Mentés az új fájlba: orarendGEB15I2.xml
            File outputFile = new File("orarendGEB15I2.xml");
            StreamResult fileResult = new StreamResult(new FileOutputStream(outputFile));
            transformer.transform(source, fileResult);

            System.out.println("\n\nA módosított fájl mentve ide: " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}