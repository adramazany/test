package bip.test.openimaj.old;

/**
 * Created by ramezani on 11/4/2019.
 */
public class Person {
    String id;
    byte[] face;

    public String getId() {
        return id;
    }

    public Person setId(String id) {
        this.id = id;
        return this;
    }

    public byte[] getFace() {
        return face;
    }

    public Person setFace(byte[] face) {
        this.face = face;
        return this;
    }
}
