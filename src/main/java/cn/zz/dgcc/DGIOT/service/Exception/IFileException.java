package cn.zz.dgcc.DGIOT.service.Exception;

/**
 * Created by: YYL
 * Date: 2020/6/3 8:41
 * ClassExplain :
 * ->
 */
public class IFileException extends RuntimeException{
    public IFileException() {
        super();
    }

    public IFileException(String message) {
        super(message);
    }

    public IFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public IFileException(Throwable cause) {
        super(cause);
    }

    public IFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
