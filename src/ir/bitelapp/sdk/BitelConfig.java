package ir.bitelapp.sdk;

/**
 * Created by Sajad on 9/25/2017.
 */
public class BitelConfig {

    private String baseUrl;
    private String apiKey;
    private String token;

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    private boolean debug;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getToken() {
        return token;
    }

    public boolean isDebug() {
        return debug;
    }

    public BitelConfig(String baseUrl, String apiKey, String token) {
        this(baseUrl, apiKey, token, false);
    }

    public BitelConfig(String baseUrl, String apiKey, String token, boolean debug) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.token = token;
        this.debug = debug;
    }
}
