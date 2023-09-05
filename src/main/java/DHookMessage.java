
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DHookMessage {
    private String content;
    private String thread_name;
    private String username;
    private String avatar_url;
    private List<Embed> embeds;

    public DHookMessage setContent(@NotNull String content) {
        this.content = content;
        return this;
    }

    public DHookMessage setThreadName(@NotNull String threadName) {
        this.thread_name = threadName;
        return this;
    }

    public Embed addEmbed(){
        Embed embed = new Embed();
        if(embeds == null)
            embeds = new ArrayList<>();
        embeds.add(embed);
        return embed;
    }

    class Embed {
        private String title;
        private String description;
        private String url;
        private int color;
        private List<Field> fields;
        private Author author;
        private Footer footer;
        private Timestamp timestamp;
        private Image image;
        private Thumbnail thumbnail;

        public Embed setTitle(@NotNull String title) {
            this.title = title;
            return this;
        }

        public Embed setDescription(@NotNull String description) {
            this.description = description;
            return this;
        }

        public Embed setUrl(@NotNull Url url) {
            this.url = url.getLink();
            return this;
        }

        public Embed setColor(int colorCode) {
            this.color = colorCode;
            return this;
        }

        public Embed addField(@NotNull String name, @NotNull String value, boolean inline) {
            Field field = new Field(name, value, inline);
            if(fields == null)
                fields = new ArrayList<>();
            fields.add(field);
            return this;
        }

        public Embed setTimestamp(@NotNull Timestamp timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Embed setImageUrl(@NotNull Url imageUrl) {
            image = new Image(imageUrl.getLink());
            return this;
        }

        public Embed setThumbnailUrl(@NotNull Url thumbnailUrl) {
            thumbnail = new Thumbnail(thumbnailUrl.getLink());
            return this;
        }

        public Embed setAuthorName(@NotNull String authorName) {
            if(author == null)
                author = new Author();
            author.setName(authorName);
            return this;
        }

        public Embed setAuthorUrl(@NotNull Url authorUrl) {
            if(author == null)
                author = new Author();
            author.setUrl(authorUrl);
            return this;
        }

        public Embed setAuthorIconUrl(@NotNull Url authorIconUrl) {
            if(author == null)
                author = new Author();
            author.setUrl(authorIconUrl);
            return this;
        }

        public Embed setFooterName(@NotNull String footerName) {
            if (footer == null) {
                footer = new Footer();
            }
            footer.setText(footerName);
            return this;
        }

        public Embed setFooterIconUrl(@NotNull Url icon_url) {
            if (footer == null) {
                footer = new Footer();
            }
            footer.setIcon_url(icon_url);
            return this;
        }

        public DHookMessage end(){
            return DHookMessage.this;
        }

        private JSONArray fieldToJson(){
            if(fields == null)
                return null;
            JSONArray fieldToJson = new JSONArray();
            for (Field field: fields) {
                fieldToJson.put(field.build());
            }
            return fieldToJson;
        }

        private JSONObject build() {
            return new JSONObject()
                    .put("title", title)
                    .put("description", description)
                    .put("author", author == null ? null : author.build())
                    .put("footer", footer == null ? null : footer.build())
                    .put("image", image == null ? null : image.build())
                    .put("thumbnail", thumbnail == null ? null : thumbnail.build())
                    .put("timestamp", timestamp == null ? null : DateTimeFormatter.ISO_INSTANT.format(timestamp.toInstant()))
                    .put("url", url)
                    .put("color", isNull(color))
                    .put("fields", fieldToJson());
        }
    }

    class Author{
        private String name;
        private String url;
        private String icon_url;

        public void setName(@NotNull String name) {
            this.name = name;
        }

        public void setUrl(@NotNull Url url) {
            this.url = url.getLink();
        }

        public void setIcon_url(@NotNull Url icon_url) {
            this.icon_url = icon_url.getLink();
        }

        private JSONObject build(){
            return new JSONObject()
                    .put("name", isNullString(name))
                    .put("url", isNullString(url))
                    .put("icon_url", isNull(icon_url));
        }

    }

    private class Image{
        private String url;

        public Image(@NotNull String url) {
            this.url = url;
        }

        public void setUrl(@NotNull Url url) {
            this.url = url.getLink();
        }

        private JSONObject build(){
            return new JSONObject()
                    .put("url", url);
        }
    }

    private class Thumbnail{
        private String url;

        public Thumbnail(@NotNull String url) {
            this.url = url;
        }

        public void setUrl(@NotNull Url url) {
            this.url = url.getLink();
        }

        private JSONObject build(){
            return new JSONObject()
                    .put("url", url);
        }

    }

    private class Footer{
        private String text;
        private String icon_url;

        public void setText(@NotNull String text) {
            this.text = text;
        }

        public void setIcon_url(@NotNull Url icon_url) {
            this.icon_url = icon_url.getLink();
        }

        private JSONObject build(){
            if(text == null)
                return null;
            return new JSONObject()
                    .put("text", text)
                    .put("icon_url", icon_url);
        }

    }

    private class Field {
        private String name;
        private String value;
        private boolean inline;

        public Field(@NotNull String name, @NotNull String value, boolean inline) {
            this.name = name;
            this.value = value;
            this.inline = inline;
        }

        private JSONObject build(){
            return new JSONObject()
                    .put("name", isNullString(name))
                    .put("value", isNullString(value))
                    .put("inline", isNullString(inline));

        }
    }

    public String build(@NotNull DHookClient dHookClient){
        return new JSONObject()
                .put("content", isNull(content))
                .put("thread_name", thread_name)
                .put("username", dHookClient.getDHookName())
                .put("avatar_url", dHookClient.getDHookIconUrl())
                .put("embeds", embedToJson())
                .toString();

    }

    private JSONArray embedToJson(){
        if(embeds == null)
            return null;
        JSONArray embedToJson = new JSONArray();
        for (Embed embed: embeds) {
            embedToJson.put(embed.build());
        }
        return embedToJson;
    }

    private Object isNullString(Object o){
        return o == null? JSONObject.NULL.toString():o;
    }

    private Object isNull(Object o){
        return o == null? JSONObject.NULL:o;
    }
}
