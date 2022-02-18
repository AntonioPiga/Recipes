package cinema.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class BadReqException extends RuntimeException {

    String cause;
    String error;

    public BadReqException(String cause) {
        super(cause);
        this.cause = cause;
    }

}