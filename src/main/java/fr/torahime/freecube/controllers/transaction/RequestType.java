package fr.torahime.freecube.controllers.transaction;

public enum RequestType {

    PLOT(60),
    CLAIM(60),
    FRIEND(60);

    private int lifetime;
    RequestType(int lifetime) {
        this.lifetime = lifetime;
    }

    public int getLifetime() {
        return lifetime;
    }
}
