package Projects.Project3.XMLParsers.SAX;

import Projects.Project3.Classes.WarPlane;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        List<WarPlane> planes = PlanesSAXParser.getPlanes("C:\\Users\\Антон\\IdeaProjects\\Epam\\src\\Projects\\Project3\\XMLFiles\\WarPlane.xml");
        for (WarPlane plane : planes) {
            System.out.println(plane);
        }
    }
}
