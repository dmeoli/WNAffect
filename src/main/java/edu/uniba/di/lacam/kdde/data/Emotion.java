package edu.uniba.di.lacam.kdde.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

final public class Emotion {

    private static final String EMOTION_FILENAME = "a-hierarchy";

    public static final String ROOT = "root";

    private static Map<String, String> emotions;

    private static final Emotion emotion = new Emotion();

    private Emotion() {
        try {
            loadEmotionsFromFile();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    synchronized private void loadEmotionsFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream objIn = new ObjectInputStream(
                Emotion.class.getClassLoader().getResourceAsStream(EMOTION_FILENAME));
        emotions = (Map<String, String>) objIn.readObject();
        objIn.close();
    }

    synchronized private void loadEmotionsFromXml() throws ParserConfigurationException, IOException, SAXException {
        emotions = new HashMap<>();
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                Emotion.class.getClassLoader().getResourceAsStream(EMOTION_FILENAME + ".xml"));
        doc.getDocumentElement().normalize();
        NodeList categories = doc.getElementsByTagName("categ");
        for (int i = 0; i < categories.getLength(); i++) {
            Element category = (Element) categories.item(i);
            String name = category.getAttribute("name");
            if (name.equals(ROOT)) emotions.put(ROOT, null);
            else emotions.put(name, category.getAttribute("isa"));
        }
    }

    public static Emotion getInstance() {
        return emotion;
    }

    public String getParent(String emotion) {
        return emotions.get(emotion);
    }
}
