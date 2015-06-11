package Projects.Project3.XSDValidator;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XSDValidator {

    static {
        PropertyConfigurator.configure("resources/log4j.properties");
    }

    private final static Logger logger = Logger.getLogger(XSDValidator.class);

    public static void main(String[] args) {
        String xmlPath = "C:\\Users\\Антон\\IdeaProjects\\Epam\\src\\Projects\\Project3\\XMLFiles\\WarPlane.xml";
        String xsdPath = "C:\\Users\\Антон\\IdeaProjects\\Epam\\src\\Projects\\Project3\\XMLFiles\\WarPlane.xsd";
        boolean isValid = XSDValidator.validateXMLSchema(xsdPath, xmlPath);

        if (isValid) {
            System.out.println(xmlPath + "\033[31m" + " is valid against " + "\033[0m" + xsdPath);
        } else {
            System.out.println(xmlPath + "\033[31m" + " is not valid against " + "\033[0m" + xsdPath);
        }
    }

    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder parser = dbf.newDocumentBuilder();
            Document document = parser.parse(new File(xmlPath));

            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = factory.newSchema(new File(xsdPath));

            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage());
            return false;
        } catch (SAXException e1) {
            logger.info("SAX Exception: " + e1.getMessage());
            return false;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return true;
    }
}