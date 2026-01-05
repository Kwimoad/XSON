package org.utils.withAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * JsonToXml class converts JSON strings into pretty-formatted XML strings.
 * It uses the org.json library and supports a custom root element name.
 */
public class JsonToXml {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts a JSON string to XML with indentation and a root element name.
     * @param jsonString The JSON string to convert
     * @param rootName The name of the root element in the XML
     * @return A formatted XML string
     * @throws Exception If the conversion fails
     */
    public static String convertJsonToXmlPretty(String jsonString, String rootName) throws Exception {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String xml = XML.toString(jsonObject, rootName);
            return formatXml(xml);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la conversion JSON en XML : " + e.getMessage(), e);
        }
    }

    /**
     * Formats the XML string with indentation.
     * @param xml The XML string to format
     * @return Pretty-printed XML string
     * @throws Exception If formatting fails
     */
    private static String formatXml(String xml) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        StringWriter writer = new StringWriter();
        transformer.transform(new StreamSource(new StringReader("<root>" + xml + "</root>")), new StreamResult(writer));

        String result = writer.toString();
        return result.replaceFirst("<root>", "").replaceFirst("</root>$", "").trim();
    }

}
