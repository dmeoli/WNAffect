package edu.uniba.di.lacam.kdde;

import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWordID;
import edu.uniba.di.lacam.kdde.item.POS;
import edu.uniba.di.lacam.kdde.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class WNAffect {

    private static IRAMDictionary dict;

    private static final String WORDNET_PATH = System.getProperty("user.dir") + File.separator + "dict";

    public WNAffect(IRAMDictionary dict) {
        WNAffect.dict = dict;
    }

    public WNAffect(boolean memoryDB) {
        try {
            if (memoryDB) {
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

    private List<IWordID> getSynsets(String lemma, POS pos) {
        IIndexWord indexWord = dict.getIndexWord(lemma, edu.mit.jwi.item.POS.getPartOfSpeech(pos.getTag()));
        return indexWord != null ? indexWord.getWordIDs() : Collections.emptyList();
    }

    public String getEmotion(String lemma, POS pos) {
        List<IWordID> synsets = getSynsets(lemma, pos);
        for (IWordID synset : synsets) {
            int offset = synset.getSynsetID().getOffset();
            if (edu.uniba.di.lacam.kdde.data.Synset.getInstance().getOffsets(pos).contains(offset))
                return edu.uniba.di.lacam.kdde.data.Synset.getInstance().getEmotion(pos, offset);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new WNAffect(false).getEmotion("angry", POS.ADJECTIVE));
    }
}
