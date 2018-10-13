package edu.uniba.di.lacam.kdde.util;

final public class WNAffectConfiguration {

    private boolean memoryDB;

    private static final WNAffectConfiguration wnAffectConfiguration = new WNAffectConfiguration();

    public static WNAffectConfiguration getInstance(){
        return wnAffectConfiguration;
    }

    public boolean useMemoryDB() {
        return memoryDB;
    }

    public void setMemoryDB(boolean memoryDB) {
        this.memoryDB = memoryDB;
    }
}
