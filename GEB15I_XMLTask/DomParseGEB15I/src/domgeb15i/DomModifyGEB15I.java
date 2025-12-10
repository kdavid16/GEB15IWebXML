package domgeb15i;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomModifyGEB15I {

    public static void main(String argv[]) {

        try {
            // XML fájl beolvasása
            File inputFile = new File("GEB15IXML.xml");
            
            // DocumentBuilderFactory és DocumentBuilder létrehozása az XML feldolgozásához
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            // XML fájl parse-olása Document objektummá a memóriába
            Document doc = docBuilder.parse(inputFile);

            // --------------------------------------------------------
            // 1. FELADAT: Cipők módosítása
            // --------------------------------------------------------
            // Az első 'Cipok' elem lekérdezése
            Node cipok = doc.getElementsByTagName("Cipok").item(0);
            // A 'Cipok' elem gyermekeinek lekérdezése
            NodeList list = cipok.getChildNodes();

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                // Csak az ELEMENT típusú node-okat dolgozzuk fel
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    // A megfelelő elemek tartalmának módosítása
                    if ("Marka".equals(eElement.getNodeName())) eElement.setTextContent("Nike");
                    if ("modell".equals(eElement.getNodeName())) eElement.setTextContent("Air Force Supreme");
                    if ("Szin".equals(eElement.getNodeName())) eElement.setTextContent("fehér");
                    if ("Meret".equals(eElement.getNodeName())) eElement.setTextContent("42");
                    if ("Keszlet".equals(eElement.getNodeName())) eElement.setTextContent("67");
                }
            }

            // --------------------------------------------------------
            // 2. FELADAT: Nyitvatartás módosítása (10:00-14:00)
            // --------------------------------------------------------
            // Megkeressük a 'nyitvatartas' elemet
            Node nyitvaTartasNode = doc.getElementsByTagName("nyitvatartas").item(0);
            
            // Ellenőrizzük, hogy a node létezik és ELEMENT típusú
            if (nyitvaTartasNode != null && nyitvaTartasNode.getNodeType() == Node.ELEMENT_NODE) {
                Element nyitvaElem = (Element) nyitvaTartasNode;
                
                // Beállítjuk a 'tol' és 'ig' értékeket
                nyitvaElem.getElementsByTagName("tol").item(0).setTextContent("10:00");
                nyitvaElem.getElementsByTagName("ig").item(0).setTextContent("14:00");
            }

            // --------------------------------------------------------
            // 3. FELADAT: Raktárvezető átnevezése (Lakatos Ali)
            // --------------------------------------------------------
            // Megkeressük a 'Raktar' elemet
            Node raktarNode = doc.getElementsByTagName("Raktar").item(0);
            
            // Ellenőrizzük, hogy a node létezik és ELEMENT típusú
            if (raktarNode != null && raktarNode.getNodeType() == Node.ELEMENT_NODE) {
                Element raktarElem = (Element) raktarNode;
                
                // A 'vezeto' tag tartalmának módosítása
                raktarElem.getElementsByTagName("vezeto").item(0).setTextContent("Lakatos Ali");
            }

            // --------------------------------------------------------
            // 4. FELADAT: Vásárló módosítása (vkod="V555" -> Tircs András)
            // --------------------------------------------------------
            // Az összes 'vasarlo' elem lekérdezése
            NodeList vasarloList = doc.getElementsByTagName("vasarlo");

            for (int i = 0; i < vasarloList.getLength(); i++) {
                Node node = vasarloList.item(i);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element vasarloElem = (Element) node;
                    
                    // Lekérjük a vkod attribútumot
                    String vkod = vasarloElem.getAttribute("vkod");
                    
                    // Ha az attribútum V555, akkor módosítjuk a nevet
                    if ("V555".equals(vkod)) {
                        vasarloElem.getElementsByTagName("nev").item(0).setTextContent("Tircs András");
                    }
                }
            }

            // --------------------------------------------------------
            // MENTÉS
            // --------------------------------------------------------
            // TransformerFactory és Transformer létrehozása a DOM-fa XML fájlba írásához
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // A forrás a módosított Document objektum
            DOMSource source = new DOMSource(doc);
            
            // A kimeneti fájl meghatározása
            StreamResult result = new StreamResult(new File("GEB15IXML2.xml"));
            
            transformer.transform(source, result);

            System.out.println("Minden módosítás sikeresen végrehajtva!");
            System.out.println("Eredmény mentve: GEB15IXML2.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}