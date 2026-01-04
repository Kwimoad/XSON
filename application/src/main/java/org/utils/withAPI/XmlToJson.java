package org.utils.withAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlToJson {

    private static final ObjectMapper xmlMapper = new XmlMapper();
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static String convertPretty(String xml) throws Exception {
        Object obj = xmlMapper.readValue(xml, Object.class);
        return jsonMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(obj);
    }

}
