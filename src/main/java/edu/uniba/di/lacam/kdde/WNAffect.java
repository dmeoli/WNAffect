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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static edu.uniba.di.lacam.kdde.data.Emotion.ROOT;

final public class WNAffect {

    private static final Logger LOGGER = LoggerFactory.getLogger(WNAffect.class);

    private static final String WORDNET_FILE = "wn30.dict";

    private static IRAMDictionary dict;
    private static ConcurrentMap<String, ConcurrentMap<Integer, String>> emotionsParents;

    public WNAffect(IRAMDictionary dict) {
        WNAffect.dict = dict;
        if (WNAffectConfiguration.getInstance().useCache()) emotionsParents = new ConcurrentHashMap<>();
    }

    public WNAffect() {
        try {
            loadWordNet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (WNAffectConfiguration.getInstance().useCache()) emotionsParents = new ConcurrentHashMap<>();
    }

    private void loadWordNet() throws IOException {
        if (WNAffectConfiguration.getInstance().useMemoryDB()) {
            LOGGER.info("Loading WordNet into memory...");
            long t = System.currentTimeMillis();
            dict = new RAMDictionary(Objects.requireNonNull(
                    WNAffect.class.getClassLoader().getResource(WORDNET_FILE)), ILoadPolicy.IMMEDIATE_LOAD);
            dict.open();
            LOGGER.info("WordNet loaded into memory in {} sec.", (System.currentTimeMillis() - t) / 1000L);
        } else {
            dict = new RAMDictionary(Objects.requireNonNull(
                    WNAffect.class.getClassLoader().getResource(WORDNET_FILE)), ILoadPolicy.NO_LOAD);
            dict.open();
        }
    }

    public IRAMDictionary getDictionary() {
        return dict;
    }

    private List<IWordID> getSynsets(String lemma, POS pos) {
        IIndexWord indexWord = dict.getIndexWord(lemma, POS.getPartOfSpeech(pos.getTag()));
        return Objects.nonNull(indexWord) ? indexWord.getWordIDs() : Collections.emptyList();
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
        if (Objects.isNull(emotion) || emotion.equalsIgnoreCase(ROOT) || level < 0) return null;
        if (WNAffectConfiguration.getInstance().useCache()) {
            ConcurrentMap<Integer, String> emotionParent = emotionsParents.get(emotion);
            if (Objects.nonNull(emotionParent)) {
                String parent = emotionParent.get(level);
                if (Objects.nonNull(parent)) return parent;
            }
        }
        List<String> parents = new ArrayList<>();
        parents.add(emotion);
        String parent = Emotion.getInstance().getParent(emotion);
        while (Objects.nonNull(parent) && !parent.equalsIgnoreCase(ROOT)) {
            parents.add(parent);
            parent = Emotion.getInstance().getParent(parent);
        }
        parents.add(ROOT);
        parents = Lists.reverse(parents);
        parent = level >= parents.size() ? parents.get(parents.size() - 1) : parents.get(level);
        if (WNAffectConfiguration.getInstance().useCache()) {
            ConcurrentMap<Integer, String> emotionParent = emotionsParents.get(emotion);
            if (Objects.nonNull(emotionParent)) {
                emotionParent.put(level, parent);
                emotionsParents.put(emotion, emotionParent);
            } else emotionsParents.put(emotion, new ConcurrentHashMap<>(Collections.singletonMap(level, parent)));
        }
        return parent;
    }
}
