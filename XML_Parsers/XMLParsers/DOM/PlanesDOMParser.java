package Projects.Project3.XMLParsers.DOM;

import Projects.Project3.Classes.Comparators.WarPlanePriceComparator;
import Projects.Project3.Classes.WarPlane;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanesDOMParser {

    static {
        PropertyConfigurator.configure("resources/log4j.properties");
    }

    private final static Logger logger = Logger.getLogger(PlanesDOMParser.class);


    public static List<WarPlane> getPlanes(String xmlPath) throws ParserConfigurationException, SAXException, IOException {
        List<WarPlane> planes = new ArrayList<WarPlane>();
        Document doc = null;
        DOMParser parser = new DOMParser();

        logger.info("Start parsing");
        parser.parse(new InputSource(new BufferedReader(new FileReader(xmlPath))));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(new InputSource(new BufferedReader(new FileReader(xmlPath))));

        Element root = doc.getDocumentElement();
        String nameSpace = "www.planes.com";
        NodeList nodeList = root.getElementsByTagNameNS("www.planes.com", "plane");
        for (int i = 0; i < nodeList.getLength(); i++) {

            Element plane = (Element) nodeList.item(i);
            WarPlane p = new WarPlane();
            String model = plane.getAttribute("model");
            p.setModel(model);
            logger.info("found plane : " + model);

            Element originElement = (Element) plane.getElementsByTagNameNS(nameSpace, "origin").item(0);
            String origin = (originElement.getFirstChild()).getNodeValue();
            p.setOrigin(origin);

            WarPlane.Characters chars = p.new Characters();

            Element typeElement = (Element) plane.getElementsByTagNameNS(nameSpace, "type").item(0);
            String type = (typeElement.getFirstChild()).getNodeValue();
            chars.setType(type);

            Element spotsNumberElement = (Element) plane.getElementsByTagNameNS(nameSpace, "spotsNumber").item(0);
            int spotsNumber = Integer.parseInt(spotsNumberElement.getFirstChild().getNodeValue());
            chars.setSpotsNumber(spotsNumber);

            WarPlane.Characters.Ammunition ammunition = null;

            ammunitionCheck:
            {
                if (plane.getElementsByTagNameNS(nameSpace, "ammunition").getLength() == 0) break ammunitionCheck;
                ammunition = chars.new Ammunition();

                Element rocketsNumberElement = (Element) plane.getElementsByTagNameNS(nameSpace, "rocketsNumber").item(0);
                int rocketsNumber = Integer.parseInt(rocketsNumberElement.getFirstChild().getNodeValue());
                ammunition.setRocketsNumber(rocketsNumber);

                Element hasRadarElement = (Element) plane.getElementsByTagNameNS(nameSpace, "hasRadar").item(0);
                boolean hasRadar = Boolean.parseBoolean(hasRadarElement.getFirstChild().getNodeValue());
                ammunition.setHasRadar(hasRadar);
            }

            chars.setAmmunition(ammunition);
            p.setChars(chars);

            WarPlane.Parameters parameters = p.new Parameters();
            Element lengthElement = (Element) plane.getElementsByTagNameNS(nameSpace, "length").item(0);
            Element widthElement = (Element) plane.getElementsByTagNameNS(nameSpace, "width").item(0);
            Element heightElement = (Element) plane.getElementsByTagNameNS(nameSpace, "height").item(0);
            float length = Float.parseFloat(lengthElement.getFirstChild().getNodeValue());
            float width = Float.parseFloat(widthElement.getFirstChild().getNodeValue());
            float height = Float.parseFloat(heightElement.getFirstChild().getNodeValue());
            parameters.setLength(length);
            parameters.setWidth(width);
            parameters.setHeight(height);
            p.setParameters(parameters);

            planes.add(p);
        }
        logger.info("Parsing completed");
        Collections.sort(planes, new WarPlanePriceComparator());
        return planes;
    }

}
