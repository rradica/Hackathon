package ch.opendata.hack.energy.sources;

public abstract class Source implements SourceReader {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
