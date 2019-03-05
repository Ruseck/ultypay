package graph.custom.update.predicate.update;

import graph.Action;
import graph.Node;
import graph.custom.update.consumer.update.ConsumerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import uibot.Context;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public enum UpdateNodesFromXmlBuilder {
    INST;

    public HashMap<Long, Node<Update>> createNodes(File file, Context userContext) {
        HashMap<Long, Node<Update>> nodesMap = new HashMap<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Element root = document.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element node = (Element) nodes.item(i);
                    createNodes(node, nodesMap, userContext);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodesMap;
    }

    private void createNodes(Element element, HashMap<Long, Node<Update>> rooms, Context userContext) {
        Node<Update> node = new Node<>(Integer.parseInt(element.getAttribute("id")));
        rooms.put(node.getId(), node);
        ArrayList<Action<Update>> boxsList = new ArrayList<>();
        NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element boxs = (Element) nodes.item(i);
                Action<Update> actionBox = createBoxs(boxs, userContext);
                boxsList.add(actionBox);
            }
        }
        node.getActions().addAll(boxsList);
    }

    private Action<Update> createBoxs(Element element, Context context) {
        NodeList nodes = element.getChildNodes();
        Action<Update> actionBox = new Action<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element inBox = (Element) nodes.item(i);
                switch (inBox.getNodeName()) {
                    case "predicate":
                        actionBox.setPredicate(PredicateFactory.INST.create(attributesToMap(inBox.getAttributes()), context));
                        break;
                    case "action":
                        actionBox.getConsumerList().add(ConsumerFactory.INST.create(attributesToMap(inBox.getAttributes()), context));
                        break;
                }
            }
        }
        return actionBox;
    }

    private HashMap<String, String> attributesToMap(NamedNodeMap attr) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < attr.getLength(); i++) {
            org.w3c.dom.Node node = attr.item(i);
            map.put(node.getNodeName(), node.getNodeValue());
        }
        return map;
    }

}
