package org.utils.withoutAPI;

import java.util.*;
import java.util.regex.*;

/**
 * Utility class to convert JSON to formatted XML.
 */
public class JsonToXml {

    /**
     * Converts a JSON string into pretty-formatted XML.
     * @param jsonStr The JSON string to convert.
     * @param rootElement The name of the XML root element.
     * @return The formatted XML string.
     * @throws Exception If the JSON is empty or invalid.
     */
    public static String convertJsonToXmlPretty(String jsonStr, String rootElement) throws Exception {
        jsonStr = jsonStr.trim();
        if (jsonStr.isEmpty()) {
            throw new Exception("La chaîne JSON est vide");
        }

        Object jsonObject;
        if (jsonStr.startsWith("[")) {
            jsonObject = parseJsonArray(jsonStr);
            Map<String, Object> wrapper = new LinkedHashMap<>();
            wrapper.put("items", jsonObject);
            jsonObject = wrapper;
        } else if (jsonStr.startsWith("{")) {
            jsonObject = parseJsonObject(jsonStr);
        } else {
            throw new Exception("Format JSON invalide");
        }

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

        if (rootElement != null && !rootElement.isEmpty()) {
            xmlBuilder.append("<").append(rootElement).append(">\n");
            buildXml(jsonObject, xmlBuilder, 1);
            xmlBuilder.append("</").append(rootElement).append(">");
        } else {
            buildXml(jsonObject, xmlBuilder, 0);
        }

        return xmlBuilder.toString();
    }

    /**
     * Recursively builds XML from a JSON object.
     * @param jsonObj The JSON object to convert.
     * @param xmlBuilder The StringBuilder holding the XML content.
     * @param indentLevel The indentation level for formatting.
     */
    private static void buildXml(Object jsonObj, StringBuilder xmlBuilder, int indentLevel) {
        if (jsonObj == null) {
            return;
        }

        String indent = "  ".repeat(indentLevel);

        if (jsonObj instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) jsonObj;

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = sanitizeXmlName(entry.getKey());
                Object value = entry.getValue();

                if (value == null) {
                    xmlBuilder.append(indent).append("<").append(key).append("/>\n");
                } else if (value instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Object> list = (List<Object>) value;
                    String itemName = getItemName(key);

                    for (Object item : list) {
                        xmlBuilder.append(indent).append("<").append(itemName).append(">\n");
                        buildXml(item, xmlBuilder, indentLevel + 1);
                        xmlBuilder.append(indent).append("</").append(itemName).append(">\n");
                    }
                } else if (value instanceof Map) {
                    xmlBuilder.append(indent).append("<").append(key).append(">\n");
                    buildXml(value, xmlBuilder, indentLevel + 1);
                    xmlBuilder.append(indent).append("</").append(key).append(">\n");
                } else {
                    String xmlValue = escapeXml(value.toString());
                    xmlBuilder.append(indent)
                            .append("<").append(key).append(">")
                            .append(xmlValue)
                            .append("</").append(key).append(">\n");
                }
            }
        } else if (jsonObj instanceof List) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) jsonObj;

            for (Object item : list) {
                xmlBuilder.append(indent).append("<item>\n");
                buildXml(item, xmlBuilder, indentLevel + 1);
                xmlBuilder.append(indent).append("</item>\n");
            }
        } else {
            String xmlValue = escapeXml(jsonObj.toString());
            xmlBuilder.append(indent).append("<value>").append(xmlValue).append("</value>\n");
        }
    }

    /**
     * Parses a JSON string representing an object into a Java Map.
     * @param jsonStr JSON string representing an object.
     * @return Map representing the JSON object.
     * @throws Exception If the JSON format is invalid.
     */
    private static Map<String, Object> parseJsonObject(String jsonStr) throws Exception {
        jsonStr = jsonStr.trim();
        if (!jsonStr.startsWith("{") || !jsonStr.endsWith("}")) {
            throw new Exception("Objet JSON invalide");
        }

        Map<String, Object> result = new LinkedHashMap<>();
        String content = jsonStr.substring(1, jsonStr.length() - 1).trim();

        if (content.isEmpty()) {
            return result;
        }

        int pos = 0;
        while (pos < content.length()) {
            int keyStart = pos;
            while (pos < content.length() && content.charAt(pos) != ':') {
                pos++;
            }
            if (pos >= content.length()) {
                throw new Exception("Format JSON invalide: clé non terminée");
            }

            String key = content.substring(keyStart, pos).trim();
            key = removeQuotes(key);

            pos++;

            int valueStart = pos;
            int braceCount = 0;
            int bracketCount = 0;
            boolean inString = false;
            char quoteChar = '\0';

            while (pos < content.length()) {
                char c = content.charAt(pos);

                if (!inString) {
                    if (c == '{') braceCount++;
                    else if (c == '}') braceCount--;
                    else if (c == '[') bracketCount++;
                    else if (c == ']') bracketCount--;
                    else if ((c == '"' || c == '\'') && (pos == 0 || content.charAt(pos-1) != '\\')) {
                        inString = true;
                        quoteChar = c;
                    } else if (c == ',' && braceCount == 0 && bracketCount == 0) {
                        break;
                    }
                } else if (c == quoteChar && content.charAt(pos-1) != '\\') {
                    inString = false;
                }

                pos++;
            }

            String valueStr = content.substring(valueStart, pos).trim();
            Object value = parseJsonValue(valueStr);
            result.put(key, value);

            if (pos < content.length() && content.charAt(pos) == ',') {
                pos++;
            }
        }

        return result;
    }

    /**
     * Parses a JSON string representing an array into a Java List.
     * @param jsonStr JSON string representing an array.
     * @return List representing the JSON array.
     * @throws Exception If the JSON format is invalid.
     */
    private static List<Object> parseJsonArray(String jsonStr) throws Exception {
        jsonStr = jsonStr.trim();
        if (!jsonStr.startsWith("[") || !jsonStr.endsWith("]")) {
            throw new Exception("Tableau JSON invalide");
        }

        List<Object> result = new ArrayList<>();
        String content = jsonStr.substring(1, jsonStr.length() - 1).trim();

        if (content.isEmpty()) {
            return result;
        }

        int pos = 0;
        while (pos < content.length()) {
            int valueStart = pos;
            int braceCount = 0;
            int bracketCount = 0;
            boolean inString = false;
            char quoteChar = '\0';

            while (pos < content.length()) {
                char c = content.charAt(pos);

                if (!inString) {
                    if (c == '{') braceCount++;
                    else if (c == '}') braceCount--;
                    else if (c == '[') bracketCount++;
                    else if (c == ']') bracketCount--;
                    else if ((c == '"' || c == '\'') && (pos == 0 || content.charAt(pos-1) != '\\')) {
                        inString = true;
                        quoteChar = c;
                    } else if (c == ',' && braceCount == 0 && bracketCount == 0) {
                        break;
                    }
                } else if (c == quoteChar && content.charAt(pos-1) != '\\') {
                    inString = false;
                }

                pos++;
            }

            String valueStr = content.substring(valueStart, pos).trim();
            if (!valueStr.isEmpty()) {
                result.add(parseJsonValue(valueStr));
            }

            if (pos < content.length() && content.charAt(pos) == ',') {
                pos++;
            }
        }

        return result;
    }

    /**
     * Parses a JSON value and converts it into a Java object.
     * @param valueStr The string representing the JSON value.
     * @return Java object corresponding to the JSON value (String, Number, Boolean, Map, or List).
     * @throws Exception If the JSON value is invalid.
     */
    private static Object parseJsonValue(String valueStr) throws Exception {
        valueStr = valueStr.trim();

        if (valueStr.isEmpty()) {
            return "";
        }

        if (valueStr.equals("null")) {
            return null;
        }

        if (valueStr.equals("true")) {
            return true;
        }
        if (valueStr.equals("false")) {
            return false;
        }

        if ((valueStr.startsWith("\"") && valueStr.endsWith("\"")) ||
                (valueStr.startsWith("'") && valueStr.endsWith("'"))) {
            String str = valueStr.substring(1, valueStr.length() - 1);

            return unescapeJsonString(str);
        }

        try {
            if (valueStr.contains(".") || valueStr.contains("e") || valueStr.contains("E")) {
                return Double.parseDouble(valueStr);
            } else {
                long longValue = Long.parseLong(valueStr);
                if (longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE) {
                    return (int) longValue;
                }
                return longValue;
            }
        } catch (NumberFormatException e) {

        }

        // Objet
        if (valueStr.startsWith("{")) {
            return parseJsonObject(valueStr);
        }

        // Tableau
        if (valueStr.startsWith("[")) {
            return parseJsonArray(valueStr);
        }

        throw new Exception("Valeur JSON invalide: " + valueStr);
    }

    /**
     * Removes quotes from a string.
     * @param str The string with quotes.
     * @return The string without quotes.
     */
    private static String removeQuotes(String str) {
        str = str.trim();
        if ((str.startsWith("\"") && str.endsWith("\"")) ||
                (str.startsWith("'") && str.endsWith("'"))) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    /**
     * Unescapes escaped characters in a JSON string.
     * @param str The escaped JSON string.
     * @return The unescaped string.
     */
    private static String unescapeJsonString(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '\\' && i + 1 < str.length()) {
                char next = str.charAt(i + 1);
                switch (next) {
                    case '"': result.append('"'); i++; break;
                    case '\'': result.append('\''); i++; break;
                    case '\\': result.append('\\'); i++; break;
                    case '/': result.append('/'); i++; break;
                    case 'b': result.append('\b'); i++; break;
                    case 'f': result.append('\f'); i++; break;
                    case 'n': result.append('\n'); i++; break;
                    case 'r': result.append('\r'); i++; break;
                    case 't': result.append('\t'); i++; break;
                    case 'u':
                        // Unicode escape
                        if (i + 5 < str.length()) {
                            String hex = str.substring(i + 2, i + 6);
                            try {
                                int codePoint = Integer.parseInt(hex, 16);
                                result.append((char) codePoint);
                                i += 5;
                            } catch (NumberFormatException e) {
                                result.append(c);
                            }
                        } else {
                            result.append(c);
                        }
                        break;
                    default:
                        result.append(c);
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * Escapes special characters for XML.
     * @param str The string to escape.
     * @return The string with XML-escaped characters.
     */
    private static String escapeXml(String str) {
        if (str == null) return "";

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '&': result.append("&amp;"); break;
                case '<': result.append("&lt;"); break;
                case '>': result.append("&gt;"); break;
                case '"': result.append("&quot;"); break;
                case '\'': result.append("&apos;"); break;
                default: result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * Sanitizes a name to be a valid XML element name.
     * @param name The name to sanitize.
     * @return A valid XML element name.
     */
    private static String sanitizeXmlName(String name) {
        if (name == null || name.isEmpty()) {
            return "element";
        }
        name = name.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");

        if (Character.isDigit(name.charAt(0))) {
            name = "x" + name;
        }
        return name;
    }

    /**
     * Generates a singular element name from a plural name.
     * @param pluralName The plural name.
     * @return Singular name for XML elements.
     */
    private static String getItemName(String pluralName) {
        if (pluralName == null || pluralName.isEmpty()) {
            return "item";
        }
        String singular = pluralName.toLowerCase();

        if (singular.endsWith("ies")) {
            return singular.substring(0, singular.length() - 3) + "y";
        } else if (singular.endsWith("s") && !singular.endsWith("ss") && singular.length() > 1) {
            return singular.substring(0, singular.length() - 1);
        } else if (singular.endsWith("es") && singular.length() > 2) {
            return singular.substring(0, singular.length() - 2);
        }

        return singular + "Item";
    }

}