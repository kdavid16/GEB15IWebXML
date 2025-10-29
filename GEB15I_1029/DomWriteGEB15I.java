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

public class DomWriteGEB15I {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document doc = builder.newDocument();
        
        Element root = doc.createElement("hallgatok");
        doc.appendChild(root);

        root.appendChild(createHallgato(doc, "01", "Pál", "Kiss", "Web Developer"));
        root.appendChild(createHallgato(doc, "02", "Piroska", "Vigh", "Java Programozo"));
        root.appendChild(createHallgato(doc, "03", "Ferenc", "Nagy", "associate professor"));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = transformerFactory.newTransformer();

        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);

        File myFile = new File("hallgato1GEB15I.xml");

        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(myFile);

        transf.transform(source, console);
        transf.transform(source, file);
    }

    private static Node createHallgato(Document doc, String id, String keresztnev, String vezeteknev, String foglalkozas) {
        Element hallgato = doc.createElement("hallgato");

        hallgato.setAttribute("id", id);
        hallgato.appendChild(createHallgatoElement(doc, "keresztnev", keresztnev));
        hallgato.appendChild(createHallgatoElement(doc, "vezeteknev", vezeteknev));
        hallgato.appendChild(createHallgatoElement(doc, "foglalkozas", foglalkozas));
        
        return hallgato;
    }

    private static Node createHallgatoElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}