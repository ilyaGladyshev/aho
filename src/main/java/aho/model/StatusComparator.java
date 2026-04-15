package aho.model;

import java.util.Comparator;

public class StatusComparator implements Comparator<Status> {
    @Override
    public int compare(Status o1, Status o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
