package edu.uniba.di.lacam.kdde.demo;

import edu.mit.jwi.item.POS;
import edu.uniba.di.lacam.kdde.WNAffect;
import edu.uniba.di.lacam.kdde.util.WNAffectConfiguration;

public class WNAffectDemo {

    static {
        WNAffectConfiguration.getInstance().setMemoryDB(false);
        WNAffectConfiguration.getInstance().setTrace(true);
    }

    public static void main(String[] args) {
        System.out.println(WNAffect.getInstance().getEmotion("angry", POS.ADJECTIVE));
    }
}
