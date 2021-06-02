package cn.afterturn.gen.core.db.exception;

/**
 * 代码生成器业务异常
 *
 * @author JueYue
 * @date 2014年12月21日
 */
public class GenerationRunTimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GenerationRunTimeException(String message) {
        super(message);
    }

    public GenerationRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
