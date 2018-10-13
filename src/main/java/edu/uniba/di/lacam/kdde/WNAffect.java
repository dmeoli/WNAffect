package edu.uniba.di.lacam.kdde;

import com.google.common.collect.Lists;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.uniba.di.lacam.kdde.data.Emotion;
import edu.uniba.di.lacam.kdde.data.Synset;
import edu.uniba.di.lacam.kdde.util.WNAffectConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final public class WNAffect {

    private static final Logger LOGGER = LoggerFactory.getLogger(WNAffect.class);

    private static final URL WORDNET = WNAffect.class.getClassLoader().getResource("wn30.dict");

    private static IRAMDictionary dict;

    private static WNAffect wnAffect = new WNAffect();

    private WNAffect() {
        try {
            loadWordNet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadWordNet() throws IOException {
        if (WNAffectConfiguration.getInstance().useMemoryDB()) {
            LOGGER.info("Loading WordNet into memory...");
            long t = System.currentTimeMillis();
            dict = new RAMDictionary(WORDNET, ILoadPolicy.IMMEDIATE_LOAD);
            dict.open();
            LOGGER.info("WordNet loaded into memory in {} sec.", (System.currentTimeMillis() - t) / 1000L);
        } else {
            dict = new RAMDictionary(WORDNET, ILoadPolicy.NO_LOAD);
            dict.open();
        }
    }

    public static WNAffect getInstance() {
        return wnAffect;
    }

    private List<IWordID> getSynsets(String lemma, POS pos) {
        IIndexWord indexWord = dict.getIndexWord(lemma, POS.getPartOfSpeech(pos.getTag()));
        return indexWord != null ? indexWord.getWordIDs() : Collections.emptyList();
    }

    public String getEmotion(String lemma, POS pos) {
        List<IWordID> synsets = getSynsets(lemma, pos);
        for (IWordID synset : synsets) {
            int offset = synset.getSynsetID().getOffset();
            if (Synset.getInstance().getOffsets(pos).contains(offset))
                return Synset.getInstance().getEmotion(pos, offset);
        }
        return null;
    }

    public String getParent(String emotion, int level) {
        if ((emotion == null) || level < 0) return null;
        List<String> parents = new ArrayList<>();
        parents.add(emotion);
        String parent = Emotion.getInstance().getParent(emotion);
        while (parent != null && !parent.equalsIgnoreCase("root")) {
            parents.add(parent);
            parent = Emotion.getInstance().getParent(parent);
        }
        parents.add("root");
        if (level >= parents.size()) return Lists.reverse(parents).get(parents.size() - 1);
        else return Lists.reverse(parents).get(level);
    }
}
