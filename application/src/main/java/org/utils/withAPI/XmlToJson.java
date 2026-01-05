package org.utils.withAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * XmlToJson class converts XML strings into pretty-formatted JSON strings.
 * It uses the Jackson library for XML and JSON processing.
 */
public class XmlToJson {

    private static final ObjectMapper xmlMapper = new XmlMapper();
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * Converts an XML string to a pretty JSON string.
     * @param xml The XML string to convert
     * @return A formatted JSON string
     * @throws Exception If the conversion fails
     */
    public static String convertPretty(String xml) throws Exception {
        Object obj = xmlMapper.readValue(xml, Object.class);
        return jsonMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(obj);
    }

}
