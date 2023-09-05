import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DHookClient {
    private Url dHookUrl;
    private Url dHookIconUrl;
    private String dHookName;

    public Url getdHookUrl() {
        return dHookUrl;
    }

    public Url getDHookIconUrl() {
        return dHookIconUrl;
    }

    public String getDHookName() {
        return dHookName;
    }

    public DHookClient(@NotNull Url dHookUrl) {
        this.dHookUrl = dHookUrl;
    }

    public DHookClient(@NotNull Url dHookUrl, @NotNull String name) {
        this.dHookUrl = dHookUrl;
        this.dHookName = name;
    }

    public DHookClient(@NotNull Url dHookUrl, @NotNull Url dHookIconUrl) {
        this.dHookUrl = dHookUrl;
        this.dHookIconUrl = dHookIconUrl;
    }

    public DHookClient(@NotNull Url dHookUrl, @NotNull Url dHookProfileImgUrl, @NotNull String dHookName) {
        this.dHookUrl = dHookUrl;
        this.dHookIconUrl = dHookProfileImgUrl;
        this.dHookName = dHookName;
    }

    public void sendMessage(@NotNull DHookMessage message){
        RequestBody requestBody = RequestBody.create(message.build(this), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(dHookUrl.getLink())
                .post(requestBody)
                .build();
        try (Response response = new OkHttpClient().newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Success!");
            } else {
                System.err.println("error :: code = " + response.code());
            }
        } catch (IOException ignored) {
            System.err.println("error :: code = 500");
        }
    }
}
