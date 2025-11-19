package geb15ijson;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReadGEB15I {

    public static void main(String[] args) {
        // Parser példányosítása
        JSONParser parser = new JSONParser();

        try {
            // 1. A JSON fájl beolvasása
            // Fontos: A fájlnak a projekt gyökerében kell lennie!
            Object obj = parser.parse(new FileReader("orarendGEB15I.json"));

            // 2. A teljes objektum konvertálása JSONObject-té
            JSONObject jsonObject = (JSONObject) obj;

            System.out.println(">>> Órarend adatok feldolgozása <<<\n");

            // 3. A gyökérelem lekérése (a korábbi feladat alapján: "GEB15I_orarend")
            // Ha a fájlneved más volt, ezt a kulcsot ellenőrizd a JSON-ben!
            JSONObject rootOrarend = (JSONObject) jsonObject.get("GEB15I_orarend");

            // 4. Az "ora" tömb lekérése
            JSONArray oraLista = (JSONArray) rootOrarend.get("ora");

            // 5. Iterálás a tömb elemein
            Iterator<JSONObject> iterator = oraLista.iterator();
            
            while (iterator.hasNext()) {
                JSONObject aktualisOra = iterator.next();

                // Beágyazott "idopont" objektum lekérése
                JSONObject idopont = (JSONObject) aktualisOra.get("idopont");

                // Adatok formázott kiírása blokk formátumban
                System.out.println("--------------------------------------------------");
                System.out.println("Tárgy:      " + aktualisOra.get("targy"));
                
                // Időpont összerakása (Nap + Tol - Ig)
                String idoString = idopont.get("nap") + ", " + idopont.get("tol") + ":00 - " + idopont.get("ig") + ":00";
                System.out.println("Időpont:    " + idoString);
                
                System.out.println("Helyszín:   " + aktualisOra.get("helyszin"));
                System.out.println("Oktató:     " + aktualisOra.get("oktato"));
                System.out.println("Szak:       " + aktualisOra.get("szak"));
            }
            
            System.out.println("--------------------------------------------------");

        } catch (IOException e) {
            System.err.println("Hiba: A fájl nem található vagy nem olvasható! Ellenőrizd a fájlnevet és az elérési utat.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Hiba: A JSON fájl formátuma nem megfelelő vagy sérült.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Váratlan hiba történt.");
            e.printStackTrace();
        }
    }
}