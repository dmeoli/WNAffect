package edu.uniba.di.lacam.kdde.data;

import com.google.common.collect.ImmutableMap;
import edu.mit.jwi.item.POS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final public class Synset {

    private static final InputStream SYNSET = Synset.class.getResourceAsStream(File.separator + "a-synsets-30.xml");

    private static final ImmutableMap<String, POS> posTags = ImmutableMap.of(
            "noun", POS.NOUN, "adj", POS.ADJECTIVE, "verb", POS.VERB, "adv", POS.ADVERB);

    private Map<POS, Map<Integer, String>> synsets;

    private static final Synset synset = new Synset();

    private Synset() {
        try {
            loadSynsets();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private synchronized void loadSynsets() throws ParserConfigurationException, IOException, SAXException {
        synsets = new HashMap<>();
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(SYNSET);
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
