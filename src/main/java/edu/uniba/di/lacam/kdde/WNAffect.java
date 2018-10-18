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
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final public class WNAffect {

    private static final Logger LOGGER = LoggerFactory.getLogger(WNAffect.class);

    private static final URL WORDNET = WNAffect.class.getClassLoader().getResource("wn30.dict");

    private static IRAMDictionary dict;
    private static ConcurrentMap<String, List<String>> emotionsPath;

    private static WNAffect wnAffect = new WNAffect();

    private WNAffect() {
        try {
            loadWordNet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (WNAffectConfiguration.getInstance().useCache()) emotionsPath = new ConcurrentHashMap<>();
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
        if (emotion == null || emotion.equalsIgnoreCase("root") || level < 0) return null;
        if (WNAffectConfiguration.getInstance().useCache()) {
            List<String> path = emotionsPath.get(emotion);
            if (Objects.nonNull(path)) {
                if (level >= path.size()) return path.get(path.size() - 1);
                else return path.get(level);
            }
        }
        List<String> parents = new ArrayList<>();
        parents.add(emotion);
        String parent = Emotion.getInstance().getParent(emotion);
        while (parent != null && !parent.equalsIgnoreCase("root")) {
            parents.add(parent);
            parent = Emotion.getInstance().getParent(parent);
        }
        parents.add("root");
        parents = Lists.reverse(parents);
        if (WNAffectConfiguration.getInstance().useCache()) emotionsPath.put(emotion, parents);
        if (level >= parents.size()) return parents.get(parents.size() - 1);
        else return parents.get(level);
    }
}
