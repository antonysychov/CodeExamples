package Projects.Project3.XMLParsers.SAX;

import Projects.Project3.Classes.Comparators.WarPlanePriceComparator;
import Projects.Project3.Classes.WarPlane;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanesHandler extends DefaultHandler {

    static {
        PropertyConfigurator.configure("resources/log4j.properties");
    }

    private final static Logger logger = Logger.getLogger(PlanesHandler.class);

    private List<WarPlane> planes = new ArrayList<>();

    private enum DefinedTags {
        PLANE, ORIGIN, CHARS, TYPE, SPOTS_NUMBER, AMMUNITION,
        ROCKETS_NUMBER, HAS_RADAR, PARAMS, LENGTH, WIDTH, HEIGHT, PRICE
    }

    private DefinedTags currentTag;

    private WarPlane plane;
    private WarPlane.Characters chars;
    private WarPlane.Characters.Ammunition ammunition;
    private WarPlane.Parameters params;

    public List<WarPlane> getPlanes() {
        Collections.sort(planes, new WarPlanePriceComparator());
        return planes;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!uri.equals("www.planes.com")) return;
        logger.info("found element " + localName);
        switch (localName) {
            case "root":
                break;
            case "plane":
                currentTag = DefinedTags.PLANE;
                plane = new WarPlane();
                plane.setModel(attributes.getValue("model"));
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
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!uri.equals("www.planes.com")) return;
        switch (localName) {
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
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String s = new String(ch, start, length);
        if (s.trim().length() == 0) return;
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
    }
}


