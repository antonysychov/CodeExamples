package Projects.Project3.HTMLConverter;

import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws TransformerException, IOException {
        String xmlPath = "WarPlane.xml";
        String xslPath = "WarPlane.xsl";
        String htmlPath = "WarPlane.html";

        XML_HTML_Converter.transform(xmlPath, xslPath, htmlPath);

    }
}
