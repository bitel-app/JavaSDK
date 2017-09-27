package ir.bitelapp.sdk;

import java.util.UUID;


/**
 * Created by Sajad on 9/25/2017.
 */
public class BitelCall {

    private String from;
    private String to;
    private long productId;
    private String uuid;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public long getProductId() {
        return productId;
    }

    public String getUuid() {
        return uuid;
    }

    public BitelCall(String from, String to, long productId) {
        this.from = from;
        this.to = to;
        this.productId = productId;
        this.uuid = UUID.randomUUID().toString();
    }
}
