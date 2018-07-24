package edu.uniba.di.lacam.kdde.demo;

import edu.uniba.di.lacam.kdde.WNAffect;
import edu.uniba.di.lacam.kdde.item.POS;

public class WNAffectDemo {

    public static void main(String[] args) {
        System.out.println(new WNAffect(false).getEmotion("angry", POS.ADJECTIVE));
    }
}
