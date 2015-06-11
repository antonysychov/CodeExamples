package Projects.Project3.HTMLConverter;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class XML_HTML_Converter {
    public static void transform(String dataXML, String inputXSL, String outputHTMLPath) throws TransformerException, IOException {
        TransformerFactory tFactory = TransformerFactory.newInstance();

        Source xslDoc = new StreamSource(inputXSL);
        Source xmlDoc = new StreamSource(dataXML);

        OutputStream htmlFile = new FileOutputStream(outputHTMLPath);
        Transformer transform = tFactory.newTransformer(xslDoc);
        transform.setURIResolver(new ClasspathResourceURIResolver());
        transform.transform(xmlDoc, new StreamResult(htmlFile));
    }
}

class ClasspathResourceURIResolver implements URIResolver {
    @Override
    public Source resolve(String href, String base) throws TransformerException {
        return new StreamSource(XML_HTML_Converter.class.getResourceAsStream(href));
    }
}