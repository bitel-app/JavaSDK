package ir.bitelapp.sdk;

public enum BitelExceptionCodes {
    NetworkError(100),
    InternalServerError(101),
    AuthError(102),
    InvalidParam(103),
    NotRegistered(104),
    CodeAlreadySent(105),
    Unknown(1000);

    public int getCode() {
        return code;
    }

    private final int code;

    private BitelExceptionCodes(int code) {
        this.code = code;
    }

    public String getMessage() {
        switch (this) {
            case NetworkError:
                return "NetworkError";
            case InternalServerError:
                return "InternalServerError";
            case AuthError:
                return "AuthError";
            case InvalidParam:
                return "InvalidParam";
            case NotRegistered:
                return "NotRegistered";
            case CodeAlreadySent:
                return "CodeAlreadySent";
            default:
                return "Unknown";
        }
    }
}
