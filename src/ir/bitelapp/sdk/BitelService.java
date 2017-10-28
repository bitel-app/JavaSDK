package ir.bitelapp.sdk;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sajad on 9/25/2017.
 */
public class BitelService {

    private BitelConfig config;

    public BitelService(BitelConfig config) {
        this.config = config;
    }

    public long call(BitelCall callReq) throws BitelException {
        try {
            JsonObject body = Json.object()
                    .add("sourcePhone", callReq.getFrom())
                    .add("destPhone", callReq.getTo())
                    .add("productId", callReq.getProductId())
                    .add("uuid", callReq.getUuid())
                    .add("code", callReq.getCode());
            URL obj = new URL(config.getBaseUrl() + "secretary/dcall");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("x-zamanak-api-key", config.getApiKey());
            con.setRequestProperty("x-zamanak-session-token", config.getToken());
            con.setRequestProperty("Content-Type", "Application/json");

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(body.toString());
            wr.flush();
            wr.close();

            if (con.getResponseCode() == 0)
                throw new BitelException(BitelExceptionCodes.NetworkError);

            if (con.getResponseCode() == 400 || con.getResponseCode() == 401 || con.getResponseCode() == 403) {
                throw new BitelException(BitelExceptionCodes.AuthError);
            }

            if (con.getResponseCode() == 500)
                throw new BitelException(BitelExceptionCodes.InternalServerError);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            if (config.isDebug()) {
                System.out.println("BitelService:");
                System.out.println("StatusCode:" + con.getResponseCode());
                System.out.println("RawResponse:" + response.toString());
            }

            JsonValue res = Json.parse(response.toString());
            JsonObject j = res.asObject();

            switch (j.get("code").asString()) {
                case "InvalidParam":
                    throw new BitelException(BitelExceptionCodes.InvalidParam);
                case "NotRegistered":
                    throw new BitelException(BitelExceptionCodes.NotRegistered);
                case "CodeAlreadySent":
                    throw new BitelException(BitelExceptionCodes.CodeAlreadySent);
                case "InternalServerError":
                    throw new BitelException(BitelExceptionCodes.InternalServerError);
            }
            return j.getLong("result", 0);
        } catch (IOException ex) {
            if (config.isDebug()) {
                System.out.println("BitelService:");
                System.out.println("exceptionMessage:" + ex.getMessage());
            }
            throw new BitelException(BitelExceptionCodes.NetworkError);
        }
    }
}
