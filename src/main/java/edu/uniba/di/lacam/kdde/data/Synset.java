package edu.uniba.di.lacam.kdde.data;

import com.google.common.collect.ImmutableMap;
import edu.mit.jwi.item.POS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final public class Synset {

    private static final String SYNSET_FILENAME = "a-synsets-30";

    private static final ImmutableMap<String, POS> posTags = ImmutableMap.of(
            "noun", POS.NOUN, "adj", POS.ADJECTIVE, "verb", POS.VERB, "adv", POS.ADVERB);

    private static Map<POS, Map<Integer, String>> synsets;

    private static final Synset synset = new Synset();

    private Synset() {
        try {
            loadSynsetsFromFile();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    synchronized private void loadSynsetsFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream objIn = new ObjectInputStream(
                Emotion.class.getClassLoader().getResourceAsStream(SYNSET_FILENAME));
        synsets = (Map<POS, Map<Integer, String>>) objIn.readObject();
        objIn.close();
    }

    synchronized private void loadSynsetsFromXml() throws ParserConfigurationException, IOException, SAXException {
        synsets = new HashMap<>();
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                Emotion.class.getClassLoader().getResourceAsStream(SYNSET_FILENAME + ".xml"));
        doc.getDocumentElement().normalize();
        posTags.keySet().forEach(pos -> {
            Map<Integer, String> synset = new HashMap<>();
            NodeList synList = ((Element) doc.getElementsByTagName(pos + "-syn-list").item(0))
                    .getElementsByTagName(pos + "-syn");
            for (int i = 0; i < synList.getLength(); i++) {
                Element syn = (Element) synList.item(i);
                if (!syn.getAttribute("categ").isEmpty())
                    synset.put(Integer.parseInt(syn.getAttribute("id").substring(2)), syn.getAttribute("categ"));
                else if (!syn.getAttribute("noun-id").isEmpty())
                    synset.put(Integer.parseInt(syn.getAttribute("id").substring(2)),
                            synsets.get(POS.NOUN).get(Integer.parseInt(syn.getAttribute("noun-id").substring(2))));
            }
            synsets.put(posTags.get(pos), synset);
        });
    }

    public static Synset getInstance() {
        return synset;
    }

    public String getEmotion(POS pos, int offset) {
        return synsets.get(pos).get(offset);
    }

    public Set<Integer> getOffsets(POS pos) {
        return synsets.get(pos).keySet();
    }
}
