package aho.model;

import java.util.Comparator;

public class AreaComparator implements Comparator<Area> {
    @Override
    public int compare(Area o1, Area o2) {
        return o1.getId() - o2.getId();
    }
}
