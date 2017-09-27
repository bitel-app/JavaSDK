package ir.bitelapp.sdk;

/**
 * Created by Sajad on 9/26/2017.
 */
public class BitelException extends Exception {
    public BitelException(BitelExceptionCodes code) {
        super(code.getMessage());
        this.code = code;
    }

    public BitelExceptionCodes getCode() {
        return code;
    }

    private BitelExceptionCodes code;


}

