package domgeb15i;
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
            // XML fájl beolvasása
            File xmlFile = new File("GEB15IXML.xml");
            
            // DocumentBuilderFactory és DocumentBuilder létrehozása az XML feldolgozásához
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            
            // XML fájl parse-olása Document objektummá és normalizálása
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // ---------------------------------------------------------
            // 1. LEKÉRDEZÉS: Mikor van nyitva az üzlet?
            // ---------------------------------------------------------
            System.out.println("\n1. Mikor van nyitva az üzlet?");
            
            // Megkeressük a 'nyitvatartas' elemeket (feltételezzük, hogy 1 van)
            NodeList nyitvaList = doc.getElementsByTagName("nyitvatartas");
            
            // Ellenőrizzük, hogy találtunk-e legalább egy 'nyitvatartas' elemet
            if (nyitvaList.getLength() > 0) {
                Element nyitvaElem = (Element) nyitvaList.item(0);
                
                String tol = nyitvaElem.getElementsByTagName("tol").item(0).getTextContent();
                String ig = nyitvaElem.getElementsByTagName("ig").item(0).getTextContent();
                
                System.out.println("   Nyitvatartás: " + tol + " - " + ig);
            }

            // ---------------------------------------------------------
            // 2. LEKÉRDEZÉS: Milyen cipő van jelenleg?
            // ---------------------------------------------------------
            System.out.println("\n2. Milyen cipő van jelenleg?");
            
            // Az összes 'Cipok' elem lekérdezése
            NodeList cipoList = doc.getElementsByTagName("Cipok");
            
            // Végigiterálunk az összes 'Cipok' elemen
            for (int i = 0; i < cipoList.getLength(); i++) {
                Node node = cipoList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    
                    String marka = elem.getElementsByTagName("Marka").item(0).getTextContent();
                    String modell = elem.getElementsByTagName("modell").item(0).getTextContent();
                    String ar = elem.getElementsByTagName("ar").item(0).getTextContent();
                    
                    System.out.println("---------------------------");
                    System.out.println("   Márka: " + marka);
                    System.out.println("   Modell: " + modell);
                    System.out.println("   Ár: " + ar + " Ft");
                }
            }

            // ---------------------------------------------------------
            // 3. LEKÉRDEZÉS: Ki a raktár vezető?
            // ---------------------------------------------------------
            System.out.println("\n3. Ki a raktár vezető?");
            
            // Az összes 'Raktar' elem lekérdezése
            NodeList raktarList = doc.getElementsByTagName("Raktar");
            
            // Feltételezzük, hogy az első raktár vezetőjére vagyunk kíváncsiak
            if (raktarList.getLength() > 0) {
                Element raktarElem = (Element) raktarList.item(0);
                String vezeto = raktarElem.getElementsByTagName("vezeto").item(0).getTextContent();
                
                System.out.println("   Raktárvezető: " + vezeto);
            }

            // ---------------------------------------------------------
            // 4. LEKÉRDEZÉS: Van-e Faragó René nevű vásárló?
            // ---------------------------------------------------------
            System.out.println("\n4. Van-e Faragó René nevű vásárló?");
            
            String keresettNev = "Faragó René";
            boolean megvan = false;
            
            // Lekérjük az összes vásárlót
            NodeList vasarloList = doc.getElementsByTagName("vasarlo");
            
            // Végigiterálunk az összes 'vasarlo' elemen
            for (int i = 0; i < vasarloList.getLength(); i++) {
                Node node = vasarloList.item(i);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    
                    // Kiolvassuk a nevet
                    String aktualisNev = elem.getElementsByTagName("nev").item(0).getTextContent();
                    
                    // Összehasonlítjuk
                    if (aktualisNev.equals(keresettNev)) {
                        megvan = true;
                        // Ha megvan, kiléphetünk a ciklusból (break), nem kell tovább keresni
                        break; 
                    }
                }
            }
            
            // Az eredmény kiíratása a 'megvan' változó értéke alapján
            if (megvan) {
                System.out.println("   Igen, van ilyen nevű vásárló!");
            } else {
                System.out.println("   Nincs " + keresettNev + " nevű vásárló az adatbázisban.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}