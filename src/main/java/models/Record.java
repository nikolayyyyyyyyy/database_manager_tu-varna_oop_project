package models;

import java.util.HashSet;
import java.util.Set;

public class Record {
    private final Set<String> values;

    public Record(){
        this.values = new HashSet<>();
    }

    public Set<String> getValues(){
        return this.values;
    }
}
