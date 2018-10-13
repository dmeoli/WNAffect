package edu.uniba.di.lacam.kdde.demo;

import edu.mit.jwi.item.POS;
import edu.uniba.di.lacam.kdde.WNAffect;
import edu.uniba.di.lacam.kdde.util.WNAffectConfiguration;

public class WNAffectDemo {

    static {
        WNAffectConfiguration.getInstance().setMemoryDB(false);
    }

    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        System.out.println(WNAffect.getInstance().getEmotion("angry", POS.ADJECTIVE));
        System.out.println(WNAffect.getInstance().getParent("anger", 5));
        System.out.println("\nDone in " + (System.currentTimeMillis() - t) + " msec.");
    }
}
