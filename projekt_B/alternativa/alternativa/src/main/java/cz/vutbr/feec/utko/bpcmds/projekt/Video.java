package cz.vutbr.feec.utko.bpcmds.projekt;

public class Video {
    private String URL;
    private String name;

    public Video(String URL, String name) {
        this.URL = URL;
        this.name = name;
    }

    public Video() {

    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
