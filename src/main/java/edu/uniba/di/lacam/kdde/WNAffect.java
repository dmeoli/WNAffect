package edu.uniba.di.lacam.kdde;

import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.uniba.di.lacam.kdde.data.Synset;
import edu.uniba.di.lacam.kdde.util.WNAffectConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

final public class WNAffect {

    private static final Logger LOGGER = LoggerFactory.getLogger(WNAffect.class);

    private static final String WORDNET = System.getProperty("user.dir") + File.separator + "dict";

    private static IRAMDictionary dict;

    private static WNAffect wnAffect = new WNAffect();

    private WNAffect() {
        try {
            loadWordNet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized private void loadWordNet() throws IOException {
        if (WNAffectConfiguration.getInstance().useMemoryDB()) {
            if (WNAffectConfiguration.getInstance().useTrace()) LOGGER.info("Loading WordNet into memory...");
            long t = System.currentTimeMillis();
            dict = new RAMDictionary(new URL("file", null, WORDNET), ILoadPolicy.IMMEDIATE_LOAD);
            dict.open();
            if (WNAffectConfiguration.getInstance().useTrace()) LOGGER.info("WordNet loaded into memory in {} sec.",
                    (System.currentTimeMillis() - t) / 1000L);
        } else {
            dict = new RAMDictionary(new URL("file", null, WORDNET), ILoadPolicy.NO_LOAD);
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
}
