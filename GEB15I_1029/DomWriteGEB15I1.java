package domgeb15i1029;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DomWriteGEB15I1 {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document doc = builder.newDocument();
        
        Element root = doc.createElement("KD_orarend");
        doc.appendChild(root);

        root.appendChild(createOra(doc, "0", "elmelet", "Elektrotechnika és elektronika", "Hétfő", "08:00", "10:00", "Előadó 4", "Szabó Nórbert István", "Mérnökinformatika BSc"));
        root.appendChild(createOra(doc, "1", "elmelet", "Web technológiák 1.", "Hétfő", "10:00", "12:00", "Előadó 5", "Dr. Agárdi Anita", "Mérnökinformatika BSc"));
        root.appendChild(createOra(doc, "2", "gyakorlat", "Web technológiák 1.", "Hétfő", "14:00", "16:00", "L101", "Dr. Agárdi Anita", "Mérnökinformatika BSc"));
        root.appendChild(createOra(doc, "3", "elmelet", "Mobil programozás alapok", "Hétfő", "16:00", "18:00", "L101", "Dr. Agárdi Anita", "Mérnökinformatika BSc"));
        root.appendChild(createOra(doc, "4", "gyakorlat", "Mobil programozás alapok", "Hétfő", "18:00", "20:00", "L101", "Dr. Agárdi Anita", "Mérnökinformatika BSc"));
        root.appendChild(createOra(doc, "5", "elmelet", "Windows rendszergazda", "Kedd", "08:00", "10:00", "L101", "Dr. Wagner György", "Mérnökinformatika BSc"));
        root.appendChild(createOra(doc, "6", "elmelet", "Mesterséges intelligencia alapok", "Kedd", "10:00", "12:00", "Előadó 32", "Kunné Dr. Tamás Judit", "Mérnökinformatika BSc"));
        root.appendChild(createOra(doc, "7", "gyakorlat", "Mesterséges intelligencia alapok", "Kedd", "12:00", "14:00", "Előadó 32", "Kunné Dr. Tamás Judit", "Mérnökinformatika BSc"));
        root.appendChild(createOra(doc, "8", "gyakorlat", "Elektrotechnika és elektronika", "Kedd", "14:00", "16:00", "A1/317", "Dr. Kozsely Gábor", "Mérnökinformatika BSc"));
        root.appendChild(createOra(doc, "9", "gyakorlat", "Windows rendszergazda", "Kedd", "16:00", "18:00", "L101", "Dr. Wagner György", "Mérnökinformatika BSc"));
        root.appendChild(createOra(doc, "10", "elmelet", "Webes adatkezelő környezetek", "Szerda", "08:00", "10:00", "A1/310", "Dr. Kovács László József", "Mérnökinformatika BSc"));
        root.appendChild(createOra(doc, "11", "gyakorlat", "Webes adatkezelő környezetek", "Szerda", "10:00", "12:00", "L1013", "Dr. Bednarik László", "Mérnökinformatika BSc"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = transformerFactory.newTransformer();

        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);

        File myFile = new File("orarend1GEB15I.xml");

        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(myFile);

        transf.transform(source, console);
        transf.transform(source, file);
    }

    private static Node createOra(Document doc, String id, String tipus, String targyStr, String napStr, String tolStr, String igStr, String helyszinStr, String oktatoStr, String szakStr) {
        
        Element ora = doc.createElement("ora");
        ora.setAttribute("id", id);
        ora.setAttribute("tipus", tipus);

        ora.appendChild(createSimpleElement(doc, "targy", targyStr));

        Element idopont = doc.createElement("idopont");
        idopont.appendChild(createSimpleElement(doc, "nap", napStr));
        idopont.appendChild(createSimpleElement(doc, "tol", tolStr));
        idopont.appendChild(createSimpleElement(doc, "ig", igStr));
        ora.appendChild(idopont);

        ora.appendChild(createSimpleElement(doc, "helyszin", helyszinStr));
        ora.appendChild(createSimpleElement(doc, "oktato", oktatoStr));
        ora.appendChild(createSimpleElement(doc, "szak", szakStr));
        
        return ora;
    }

    private static Node createSimpleElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}