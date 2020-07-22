package cn.zz.dgcc.DGIOT.service.Exception;

/**
 * Created by: LT001
 * Date: 2020/4/23 16:00
 * ClassExplain :
 * ->
 */
public class ISqlException extends RuntimeException {
    public ISqlException() {
        super();
    }

    public ISqlException(String message) {
        super(message);
    }

    public ISqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public ISqlException(Throwable cause) {
        super(cause);
    }

    protected ISqlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
