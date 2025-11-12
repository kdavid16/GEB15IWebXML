package xpathgeb15i;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;

public class xPathQueryGEB15I {

    public static void main(String[] args) {
        // XML fájl elérési útja (a projekt gyökeréből vagy abszolút útvonal)
        File xmlFile = new File("studentGEB15I.xml");

        try {
            // 1) DocumentBuilder létrehozása és XML beolvasása
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);           // nincs névtér a mintában
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(xmlFile);
            document.getDocumentElement().normalize(); // normalizálás a konzisztens DOM-ért [web:39]

            // 2) XPath példány
            XPath xPath = XPathFactory.newInstance().newXPath(); // XPathFactory + newInstance minta [web:49]

            // 3) Kifejezés: az összes student elem a gyökér alatt
            String exprStudents = "/class/student"; // strukturális kiválasztás [web:44]
            NodeList students = (NodeList) xPath.compile(exprStudents)
                    .evaluate(document, XPathConstants.NODESET);

            // 4) Iterálás és adatok kiírása a képeken látható mintához hasonlóan
            for (int i = 0; i < students.getLength(); i++) {
                Node node = students.item(i);
                System.out.println("\nAktuális elem: " + node.getNodeName()); // nodeName kiírás [web:39]

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element studentEl = (Element) node;

                    // Ha lenne attribútum a <student>-en (pl. id), így kérdezhető le:
                    String attrId = studentEl.getAttribute("id"); // getAttribute használat [web:42]
                    if (attrId != null && !attrId.isEmpty()) {
                        System.out.println("Hallgató ID: " + attrId); // attribútum kiírás [web:42]
                    }

                    // Al-elemek lekérése DOM-on, a mintaképek szerint:
                    System.out.println("Keresztnév: "
                            + studentEl.getElementsByTagName("keresztnev")
                                       .item(0).getTextContent()); // getElementsByTagName minta [web:48]

                    System.out.println("Vezetéknév: "
                            + studentEl.getElementsByTagName("vezeteknev")
                                       .item(0).getTextContent()); // DOM tag-lekérés [web:41]

                    System.out.println("Becenév: "
                            + studentEl.getElementsByTagName("becenev")
                                       .item(0).getTextContent()); // DOM + textContent [web:39]

                    System.out.println("Kor: "
                            + studentEl.getElementsByTagName("kor")
                                       .item(0).getTextContent()); // DOM + textContent [web:39]
                }
            }

            // 5) További XPath példák (ha kell szűrés):
            // Példa: csak azok a keresztnevek, ahol kor > 19
            String exprAge = "/class/student[id/kor > 19]/id/keresztnev"; // szűrés és kiválasztás [web:44]
            NodeList namesOver19 = (NodeList) xPath.compile(exprAge)
                    .evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < namesOver19.getLength(); i++) {
                System.out.println("20+ keresztnev: " +
                        namesOver19.item(i).getTextContent()); // evaluate + NODESET kiírás [web:44]
            }

        } catch (XPathExpressionException e) {
            e.printStackTrace(); // XPath hiba [web:49]
        } catch (Exception e) {
            e.printStackTrace(); // Parser/IO kivételek [web:39]
        }
    }
}
