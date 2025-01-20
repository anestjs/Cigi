package main.java.com.cigiproject.model;

public enum Year {

    _1("_1"),
    _2("_2"),
    _3("_3");

    private final String displayName;

    Year(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
