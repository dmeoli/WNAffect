package edu.uniba.di.lacam.kdde.demo;

import edu.mit.jwi.item.POS;
import edu.uniba.di.lacam.kdde.WNAffect;

public class WNAffectDemo {

    public static void main(String[] args) {
        System.out.println(new WNAffect(false).getEmotion("angry", POS.ADJECTIVE));
    }
}
