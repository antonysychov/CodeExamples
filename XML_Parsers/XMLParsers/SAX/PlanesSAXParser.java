package Projects.Project3.XMLParsers.SAX;

import Projects.Project3.Classes.WarPlane;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlanesSAXParser {

    static {
        PropertyConfigurator.configure("resources/log4j.properties");
    }

    private final static Logger logger = Logger.getLogger(PlanesSAXParser.class);

    public static List<WarPlane> getPlanes(String xmlPath) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser parser = spf.newSAXParser();
        System.out.println(parser.isNamespaceAware());
        PlanesHandler planes = new PlanesHandler();
        logger.info("Start parsing");
        parser.parse(new File(xmlPath), planes);
        logger.info("Parsing completed");
        return planes.getPlanes();
    }
}
