package domgeb15i;
import java.io.File;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomReadGEB15I {

    public static void main(String[] args) {
        // PrintWriter deklarálása a try blokk előtt, hogy a finally-ban lezárhassuk
        PrintWriter writer = null;

        try {
            // XML fájl beolvasása
            File xmlFile = new File("Cipobolt.xml");
            
            // Fájl író létrehozása a kimeneti .txt fájlhoz, UTF-8 kódolással
            writer = new PrintWriter("GEB15IXML.txt", "UTF-8");

            // XML dokumentum feldolgozásához szükséges factory és builder létrehozása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            // XML fájl parse-olása Document objektummá
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // A gyökér elem gyermekeinek lekérdezése
            NodeList rootChildren = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < rootChildren.getLength(); i++) {
                Node node = rootChildren.item(i);
                // Csak az ELEMENT típusú node-okat dolgozzuk fel
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    // Kiíratás konzolra ÉS fájlba is a segédmetódussal
                    writeToBoth("----------------------------------------", writer);
                    writeToBoth(node.getNodeName().toUpperCase(), writer);
                    writeToBoth("----------------------------------------", writer);
                    
                    // A writer-t is átadjuk a rekurzív függvénynek
                    printCleanData(node, "", writer);
                    
                    writeToBoth("", writer); // Üres sor
                }
            }
            
            System.out.println("A kiírás kész! Ellenőrizd a GEB15IXML.txt fájlt.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fontos: A PrintWriter-t mindig le kell zárni, hogy a tartalom biztosan kiíródjon a fájlba.
            if (writer != null) {
                writer.close();
            }
        }
    }

    // Módosítottuk a függvényt: most már várja a 'writer'-t is paraméterként
    /**
     * Rekurzívan bejárja a DOM-fát és kiírja az elemeket és attribútumokat.
     * @param node A feldolgozandó csomópont.
     * @param indent Behúzás a formázott kiíratáshoz.
     * @param writer A PrintWriter objektum a fájlba íráshoz.
     */
    private static void printCleanData(Node node, String indent, PrintWriter writer) {
        // Ha a csomópontnak vannak attribútumai, kiírjuk őket
        if (node.hasAttributes()) {
            NamedNodeMap attributes = node.getAttributes();
            for (int j = 0; j < attributes.getLength(); j++) {
                Node attr = attributes.item(j);
                writeToBoth(indent + attr.getNodeName() + ": " + attr.getNodeValue(), writer);
            }
        }

        // A csomópont gyermekeinek lekérdezése
        NodeList children = node.getChildNodes();
        
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);

            // Csak az ELEMENT típusú gyermekeket dolgozzuk fel
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                // Ha a gyermeknek vannak további elemei (nem csak szöveges tartalom)
                if (hasChildElements(child)) {
                    // Kiírjuk a gyermek nevét, majd rekurzívan folytatjuk a bejárást
                    writeToBoth(indent + child.getNodeName() + ":", writer);
                    // Továbbadjuk a writer-t a következő szintnek
                    printCleanData(child, indent + "  ", writer);
                } else {
                    // Ha a gyermeknek nincs további eleme, kiírjuk a nevét és a szöveges tartalmát
                    String text = child.getTextContent().trim();
                    writeToBoth(indent + child.getNodeName() + ": " + text, writer);
                }
            }
        }
    }
    
    /**
     * Ellenőrzi, hogy egy csomópontnak vannak-e ELEMENT típusú gyermekei.
     * @param node A vizsgálandó csomópont.
     * @return true, ha van ELEMENT típusú gyermeke, egyébként false.
     */
    private static boolean hasChildElements(Node node) {
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
                return true;
            }
        }
        return false;
    }

    // --- ÚJ SEGÉDMETÓDUS ---
    /**
     * Segédmetódus, amely egyszerre ír a konzolra és a megadott PrintWriter-be.
     * @param text A kiírandó szöveg.
     * @param writer A PrintWriter objektum a fájlba íráshoz.
     */
    private static void writeToBoth(String text, PrintWriter writer) {
        System.out.println(text); // Konzol
        if (writer != null) {
            writer.println(text); // Fájl
        }
    }
}