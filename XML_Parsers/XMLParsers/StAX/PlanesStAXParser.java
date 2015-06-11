package Projects.Project3.XMLParsers.StAX;

import Projects.Project3.Classes.Comparators.WarPlanePriceComparator;
import Projects.Project3.Classes.WarPlane;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PlanesStAXParser {
    static {
        PropertyConfigurator.configure("resources/log4j.properties");
    }

    private final static Logger logger = Logger.getLogger(PlanesStAXParser.class);

    private static List<WarPlane> planes = new ArrayList<>();

    private enum DefinedTags {
        PLANE, ORIGIN, CHARS, TYPE, SPOTS_NUMBER, AMMUNITION,
        ROCKETS_NUMBER, HAS_RADAR, PARAMS, LENGTH, WIDTH, HEIGHT, PRICE
    }

    private static DefinedTags currentTag;

    private static WarPlane plane;
    private static WarPlane.Characters chars;
    private static WarPlane.Characters.Ammunition ammunition;
    private static WarPlane.Parameters params;

    public static List<WarPlane> getPlanes(String xmlPath) throws ParserConfigurationException, SAXException, IOException {

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(xmlPath));

            logger.info("Start parsing");
            repeat:
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        if (!startElement.getName().getNamespaceURI().equals("www.planes.com")) continue repeat;
                        String qName = startElement.getName().getLocalPart();
                        logger.info("found element " + qName);
                        switch (qName) {
                            case "root":
                                break;
                            case "plane":
                                currentTag = DefinedTags.PLANE;
                                plane = new WarPlane();
                                Iterator<Attribute> attributes = startElement.getAttributes();
                                String model = attributes.next().getValue();
                                plane.setModel(model);
                                break;
                            case "origin":
                                currentTag = DefinedTags.ORIGIN;
                                break;
                            case "chars":
                                currentTag = DefinedTags.CHARS;
                                chars = plane.new Characters();
                                break;
                            case "type":
                                currentTag = DefinedTags.TYPE;
                                break;
                            case "spotsNumber":
                                currentTag = DefinedTags.SPOTS_NUMBER;
                                break;
                            case "ammunition":
                                currentTag = DefinedTags.AMMUNITION;
                                ammunition = chars.new Ammunition();
                                break;
                            case "rocketsNumber":
                                currentTag = DefinedTags.ROCKETS_NUMBER;
                                break;
                            case "hasRadar":
                                currentTag = DefinedTags.HAS_RADAR;
                                break;
                            case "parameters":
                                currentTag = DefinedTags.PARAMS;
                                params = plane.new Parameters();
                                break;
                            case "length":
                                currentTag = DefinedTags.LENGTH;
                                break;
                            case "width":
                                currentTag = DefinedTags.WIDTH;
                                break;
                            case "height":
                                currentTag = DefinedTags.HEIGHT;
                                break;
                            case "price":
                                currentTag = DefinedTags.PRICE;
                                break;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        String s = event.asCharacters().toString();
                        if (s.trim().length() == 0) continue repeat;
                        logger.info("found Characters - " + "\"" + s + "\"");

                        switch (currentTag) {
                            case ORIGIN:
                                plane.setOrigin(s);
                                break;
                            case TYPE:
                                chars.setType(s);
                                break;
                            case SPOTS_NUMBER:
                                chars.setSpotsNumber(Integer.parseInt(s));
                                break;
                            case ROCKETS_NUMBER:
                                ammunition.setRocketsNumber(Integer.parseInt(s));
                                break;
                            case HAS_RADAR:
                                ammunition.setHasRadar(Boolean.parseBoolean(s));
                                break;
                            case LENGTH:
                                params.setLength(Float.parseFloat(s));
                                break;
                            case WIDTH:
                                params.setWidth(Float.parseFloat(s));
                                break;
                            case HEIGHT:
                                params.setHeight(Float.parseFloat(s));
                                break;
                            case PRICE:
                                plane.setPrice(Float.parseFloat(s));
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if (!endElement.getName().getNamespaceURI().equals("www.planes.com")) continue repeat;
                        qName = endElement.getName().getLocalPart();
                        switch (qName) {
                            case "plane":
                                planes.add(plane);
                                plane = null;
                                ammunition = null;
                                params = null;
                                chars = null;
                                break;
                            case "ammunition":
                                chars.setAmmunition(ammunition);
                                break;
                            case "chars":
                                plane.setChars(chars);
                                break;
                            case "parameters":
                                plane.setParameters(params);
                        }
                        break;
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        logger.info("Parsing completed");
        Collections.sort(planes, new WarPlanePriceComparator());
        return planes;
    }
}
