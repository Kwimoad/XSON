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

public class JsonToXml {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convertit une chaîne JSON en XML avec indentation et nom de racine
     * @param jsonString Chaîne JSON à convertir
     * @param rootName Nom de l'élément racine pour le XML
     * @return XML indenté
     * @throws Exception Si la conversion échoue
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
