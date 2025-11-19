package geb15ijson;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class JSONValidationGEB15I {

    public static void main(String[] args) {
        // Fájlnevek definiálása
        String jsonFileName = "orarendGEB15I.json";
        String schemaFileName = "orarendJSONSchemaGEB15I.json";

        System.out.println("--- JSON Validáció Indítása ---");

        try (
            // Fájlok beolvasása Streamként
            InputStream jsonStream = new FileInputStream(jsonFileName);
            InputStream schemaStream = new FileInputStream(schemaFileName)
        ) {
            // 1. JSON fájl betöltése JSONObject-be
            JSONObject rawJson = new JSONObject(new JSONTokener(jsonStream));
            
            // 2. JSON Schema fájl betöltése JSONObject-be
            JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));

            // 3. Schema betöltése a validátorba
            Schema schema = SchemaLoader.load(rawSchema);

            // 4. Validáció futtatása
            try {
                schema.validate(rawJson);
                System.out.println("Eredmény: Validation Successful! (A JSON megfelel a sémának)");
            } catch (ValidationException e) {
                // Ha hiba van, részletesen kiírjuk
                System.out.println("Eredmény: Validation Failed!");
                System.out.println("Hibaüzenet: " + e.getMessage());
                
                // Ha több hiba is van, azokat is kilistázzuk
                e.getCausingExceptions().stream()
                        .map(ValidationException::getMessage)
                        .forEach(System.out::println);
            }

        } catch (FileNotFoundException e) {
            System.err.println("HIBA: Nem található valamelyik fájl!");
            System.err.println("Keresett fájlok: " + jsonFileName + ", " + schemaFileName);
            System.err.println("Ellenőrizd, hogy a projekt gyökérmappájában vannak-e!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}