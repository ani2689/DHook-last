import org.jetbrains.annotations.NotNull;

public class Url {
    private String link;

    public Url(@NotNull String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(@NotNull String link) {
        this.link = link;
    }

}
