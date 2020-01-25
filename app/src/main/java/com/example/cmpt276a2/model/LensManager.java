package com.example.cmpt276a2.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class to manage lenses.
 */

public class LensManager implements Iterable<Lens> {

    /*
        Singleton Support
     */
    private static LensManager instance;

    public static LensManager getInstance() {
        if(instance == null) {
            instance = new LensManager();
        }
        return instance;
    }

    private LensManager() {
        // Private to prevent anyone else from instantiating
    }

    ArrayList<Lens> lenses = new ArrayList<>();

    public List<Lens> getLenses() {
        return lenses;
    }

    public void add(Lens lens) {
        lenses.add(lens);
    }

    public void remove(Lens lens) {
        lenses.remove(lens);
    }

    @Override
    public Iterator<Lens> iterator() {
        return lenses.iterator();
    }
}


