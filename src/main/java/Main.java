import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        DHookMessage message1 = new DHookMessage()
                .setContent("동아리 신설 요청이 \n 거절되었어요.")
                .addEmbed()
                    .setColor(0x7E89AB)
                    .setTitle("타이틀")
                    .setUrl(new Url("https://discord.js.org/"))
                    .setDescription("설명")
                    .addField("","",false)
                    .end()
                .setThreadName("안녕");

        System.out.println(message1.build(new DHookClient(new Url("https://discord.com/api/webhooks/1100579827444097076/VsGKWusr8NCKrPNdS6Hop29f8gdGLk2vxfHYwO4RJlkfIKZgVxVrqZzZp6iEuFHD5Ugw"))));
    }
}
