package geb15ijson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONWriteGEB15I {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try {
            // 1. BEOLVASÁS (orarendGEB15I.json)
            System.out.println("Fájl beolvasása...");
            Object obj = parser.parse(new FileReader("orarendGEB15I.json"));
            JSONObject jsonObject = (JSONObject) obj;

            // 2. FELDOLGOZÁS ÉS KONZOLRA ÍRÁS
            System.out.println("\n>>> Órarend megjelenítése konzolon (Blokk formátum) <<<");
            
            // A gyökérelem lekérése (neptun kód alapján)
            JSONObject rootOrarend = (JSONObject) jsonObject.get("GEB15I_orarend");
            
            // Az órák tömbjének lekérése
            JSONArray oraLista = (JSONArray) rootOrarend.get("ora");
            
            Iterator<JSONObject> iterator = oraLista.iterator();
            while (iterator.hasNext()) {
                JSONObject aktualisOra = iterator.next();
                JSONObject idopont = (JSONObject) aktualisOra.get("idopont");

                System.out.println("--------------------------------------------------");
                System.out.println("Tárgy:      " + aktualisOra.get("targy"));
                System.out.println("Időpont:    " + idopont.get("nap") + ", " + idopont.get("tol") + "-" + idopont.get("ig") + " óra");
                System.out.println("Helyszín:   " + aktualisOra.get("helyszin"));
                System.out.println("Oktató:     " + aktualisOra.get("oktato"));
                System.out.println("Szak:       " + aktualisOra.get("szak"));
            }
            System.out.println("--------------------------------------------------");

            // 3. KIÍRÁS ÚJ FÁJLBA (orarendGEB15I1.json)
            // Itt a beolvasott (és esetleg módosított) objektumot mentjük el
            try (FileWriter file = new FileWriter("orarendGEB15I1.json")) {
                
                // A toJSONString() metódus alakítja vissza szöveggé az objektumot
                file.write(jsonObject.toJSONString());
                
                System.out.println("\nSikeres fájlba írás!"); 
                System.out.println("Az új fájl neve: orarendGEB15I1.json");
                
            } catch (IOException e) {
                System.err.println("Hiba történt a fájl írásakor!");
                e.printStackTrace();
            }

        } catch (IOException | ParseException e) {
            System.err.println("Hiba történt a beolvasás során!");
            e.printStackTrace();
        }
    }
}