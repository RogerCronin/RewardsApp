import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentTypeHandler {
    final static HashMap<String, String> contentTypes = new HashMap<>() {{
        put("bmp", "image/bmp");
        put("css", "text/css");
        put("gif", "image/gif");
        put("html", "text/html");
        put("jpeg", "image/jpeg");
        put("jpg", "image/jpeg");
        put("js", "text/javascript");
        put("mjs", "text/javascript");
        put("json", "application/json");
        put("mp3", "audio/mpeg");
        put("mp4", "video/mp4");
        put("mpeg", "video/mpeg");
        put("otf", "font/otf");
        put("png", "image/png");
        put("svg", "image/svg+xml");
        put("txt", "text/plain");
        put("wav", "audio/wav");
        put("weba", "audio/webm");
        put("webm", "video/webm");
        put("webp", "image/webp");
        put("xml", "application/xml");
    }};
    final static Pattern regex = Pattern.compile("^.*\\.(.+)$");

    public static String getContentTypeHeader(String fileName) {
        Matcher m = regex.matcher(fileName);
        if(m.find() && contentTypes.containsKey(m.group(1))) {
            return contentTypes.get(m.group(1));
        }
        return "text/plain";
    }
}