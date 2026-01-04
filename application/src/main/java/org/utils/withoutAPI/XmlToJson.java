package org.utils.withoutAPI;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class XmlToJson {

    public static class JsonNode {
        private String name;
        private String value;
        private Map<String, String> attributes = new LinkedHashMap<>();
        private Map<String, List<JsonNode>> children = new LinkedHashMap<>();

        public JsonNode(String name) {
            this.name = name;
        }

        public String getName() { return name; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }

        public Map<String, String> getAttributes() { return attributes; }
        public Map<String, List<JsonNode>> getChildren() { return children; }

        public void addAttribute(String key, String value) {
            attributes.put(key, value);
        }

        public void addChild(String name, JsonNode child) {
            children.computeIfAbsent(name, k -> new ArrayList<>()).add(child);
        }
    }

    public static JsonNode xmlToObject(Element element) {
        JsonNode node = new JsonNode(element.getTagName());

        NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            Node attr = attrs.item(i);
            node.addAttribute(attr.getNodeName(), attr.getNodeValue());
        }

        NodeList children = element.getChildNodes();
        StringBuilder textContent = new StringBuilder();
        boolean hasElementChild = false;

        for (int i = 0; i < children.getLength(); i++) {
            Node n = children.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                hasElementChild = true;
                JsonNode child = xmlToObject((Element) n);
                node.addChild(child.getName(), child);
            } else if (n.getNodeType() == Node.TEXT_NODE) {
                String text = n.getTextContent();
                if (!text.trim().isEmpty()) {
                    textContent.append(text);
                }
            }
        }

        if (!hasElementChild) {
            String text = textContent.toString().trim();
            if (!text.isEmpty()) {
                node.setValue(text);
            }
        }

        return node;
    }

    private static String escapeJsonString(String value) {
        if (value == null) return "";

        StringBuilder sb = new StringBuilder();
        for (char c : value.toCharArray()) {
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b"); break;
                case '\f': sb.append("\\f"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }

    public static String toPrettyJSONString(JsonNode node) {
        StringBuilder sb = new StringBuilder();
        buildPrettyJson(node, sb, 0);
        return sb.toString();
    }

    private static void buildPrettyJson(JsonNode node, StringBuilder sb, int indent) {
        String indentStr = "  ".repeat(indent);
        String childIndent = "  ".repeat(indent + 1);

        sb.append(indentStr).append("{\n");

        boolean hasPrevious = false;

        if (!node.getAttributes().isEmpty()) {
            sb.append(childIndent).append("\"@attributes\": {\n");
            int i = 0;
            String attributeIndent = "  ".repeat(indent + 2);
            for (var entry : node.getAttributes().entrySet()) {
                sb.append(attributeIndent)
                        .append("\"")
                        .append(escapeJsonString(entry.getKey()))
                        .append("\": \"")
                        .append(escapeJsonString(entry.getValue()))
                        .append("\"");
                if (i++ < node.getAttributes().size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append(childIndent).append("}");
            hasPrevious = true;
        }

        if (node.getValue() != null) {
            if (hasPrevious) sb.append(",\n");
            sb.append(childIndent)
                    .append("\"#text\": \"")
                    .append(escapeJsonString(node.getValue()))
                    .append("\"");
            hasPrevious = true;
        }

        int i = 0;
        for (var entry : node.getChildren().entrySet()) {
            if (hasPrevious || i > 0) sb.append(",\n");
            sb.append(childIndent)
                    .append("\"")
                    .append(escapeJsonString(entry.getKey()))
                    .append("\": ");

            List<JsonNode> children = entry.getValue();
            if (children.size() == 1) {
                buildPrettyJson(children.get(0), sb, indent + 1);
            } else {
                sb.append("[\n");
                for (int j = 0; j < children.size(); j++) {
                    buildPrettyJson(children.get(j), sb, indent + 2);
                    if (j < children.size() - 1) sb.append(",\n");
                    else sb.append("\n");
                }
                sb.append(childIndent).append("]");
            }
            i++;
        }

        sb.append("\n").append(indentStr).append("}");
    }

}