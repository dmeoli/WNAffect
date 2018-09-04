package edu.uniba.di.lacam.kdde;

import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.uniba.di.lacam.kdde.data.Synset;
import edu.uniba.di.lacam.kdde.util.Log;
import edu.uniba.di.lacam.kdde.util.WNAffectConfiguration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class WNAffect {

    private static final String WORDNET_PATH = System.getProperty("user.dir") + File.separator + "dict";

    private static IRAMDictionary dict;

    private static WNAffect wnAffect = new WNAffect();

    private WNAffect() {
        try {
            if (WNAffectConfiguration.getInstance().useMemoryDB()) {
                Log.info("Loading WordNet into memory...");
                long t = System.currentTimeMillis();
                dict = new RAMDictionary(new URL("file", null, WORDNET_PATH), ILoadPolicy.IMMEDIATE_LOAD);
                dict.open();
                Log.info("WordNet loaded into memory in %d sec.", (System.currentTimeMillis()-t) / 1000L);
            } else {
                dict = new RAMDictionary(new URL("file", null, WORDNET_PATH), ILoadPolicy.NO_LOAD);
                dict.open();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
