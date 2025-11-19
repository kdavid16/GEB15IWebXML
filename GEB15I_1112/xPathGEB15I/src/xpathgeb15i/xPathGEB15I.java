package xpathgeb15i;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.File;

public class xPathGEB15I {

    private static void printNodes(String title, NodeList nodes) {
        System.out.println("\n=== " + title + " ===");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                System.out.print(e.getNodeName());
                if (e.hasAttribute("id")) System.out.print(" [id=" + e.getAttribute("id") + "]");
                // ha van id/keresztnev stb. gyermek, próbáljuk kiírni
                NodeList k = e.getElementsByTagName("keresztnev");
                NodeList v = e.getElementsByTagName("vezeteknev");
                NodeList b = e.getElementsByTagName("becenev");
                NodeList kor = e.getElementsByTagName("kor");
                if (k.getLength() > 0) System.out.print(" keresztnev=" + k.item(0).getTextContent());
                if (v.getLength() > 0) System.out.print(" vezeteknev=" + v.item(0).getTextContent());
                if (b.getLength() > 0) System.out.print(" becenev=" + b.item(0).getTextContent());
                if (kor.getLength() > 0) System.out.print(" kor=" + kor.item(0).getTextContent());
                System.out.println();
            } else {
                System.out.println(n.getNodeName());
            }
        }
        if (nodes.getLength() == 0) System.out.println("(nincs találat)");
    }

    public static void main(String[] args) throws Exception {

        
        // 1) XML beolvasása DOM-mal
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File("studentGEB15I.xml"));
        doc.getDocumentElement().normalize(); // ajánlott normalizálás [web:39]

        // 2) XPath példány
        XPath xp = XPathFactory.newInstance().newXPath(); // JAXP XPath [web:49]

        // 1) Válassza ki az összes student elemet, amely a class gyermekei
        printNodes("1) /class/student",
                (NodeList) xp.compile("/class/student").evaluate(doc, XPathConstants.NODESET)); // abszolút útvonal [web:44]

        // 2) student elem id attribútummal, értéke "02"
        printNodes("2) //student[@id='02']",
                (NodeList) xp.compile("//student[@id='02']").evaluate(doc, XPathConstants.NODESET)); // attribútum-szűrés [web:44]

        // 3) az összes student elem bárhol a dokumentumban
        printNodes("3) //student",
                (NodeList) xp.compile("//student").evaluate(doc, XPathConstants.NODESET)); // leszármazott-keresés [web:44]

        // 4) a második student elem, amely a class gyermeke
        printNodes("4) (/class/student)[2]",
                (NodeList) xp.compile("(/class/student)[2]").evaluate(doc, XPathConstants.NODESET)); // pozíciós predikátum [web:44]

        // 5) az utolsó student elem a class alatt
        printNodes("5) (/class/student)[last()]",
                (NodeList) xp.compile("(/class/student)[last()]").evaluate(doc, XPathConstants.NODESET)); // last() függvény [web:44]

        // 6) az utolsó előtti student elem a class alatt
        printNodes("6) (/class/student)[last()-1]",
                (NodeList) xp.compile("(/class/student)[last()-1]").evaluate(doc, XPathConstants.NODESET)); // last()-1 minta [web:44]

        // 7) az első két student elem a class alatt
        printNodes("7) (/class/student)[position() <= 2]",
                (NodeList) xp.compile("(/class/student)[position() <= 2]").evaluate(doc, XPathConstants.NODESET)); // position() [web:44]

        // 8) a class root elem összes gyermeke
        printNodes("8) /class/*",
                (NodeList) xp.compile("/class/*").evaluate(doc, XPathConstants.NODESET)); // * wildcard gyermekekre [web:44]

        // 9) minden student, amelynek van legalább egy attribútuma
        printNodes("9) //student[@*]",
                (NodeList) xp.compile("//student[@*]").evaluate(doc, XPathConstants.NODESET)); // @* tetszőleges attribútum [web:44]

        // 10) a dokumentum összes eleme
        printNodes("10) //*",
                (NodeList) xp.compile("//*").evaluate(doc, XPathConstants.NODESET)); // minden elem bárhol [web:44]

        // 11) a class root elem összes student eleme, ahol a kor > 20
        printNodes("11) /class/student[id/kor > 20]",
                (NodeList) xp.compile("/class/student[id/kor > 20]").evaluate(doc, XPathConstants.NODESET)); // numerikus predikátum [web:44]

        // 12) az összes student elem összes keresztnev vagy vezeteknev csomópontja
        printNodes("12) //student/id/keresztnev | //student/id/vezeteknev",
                (NodeList) xp.compile("//student/id/keresztnev | //student/id/vezeteknev").evaluate(doc, XPathConstants.NODESET));
    }
}
