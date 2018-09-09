package edu.uniba.di.lacam.kdde.util;

final public class WNAffectConfiguration {

    private boolean memoryDB;
    private boolean trace;

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

    public boolean useTrace() {
        return trace;
    }

    public void setTrace(boolean trace) {
        this.trace = trace;
    }
}
