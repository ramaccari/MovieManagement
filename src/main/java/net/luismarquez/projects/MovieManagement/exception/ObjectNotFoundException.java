package net.luismarquez.projects.MovieManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 3928436375934765827L;
	private final String objectNotFoundName;
 
    public ObjectNotFoundException(String objectNotFoundName) {
        this.objectNotFoundName = objectNotFoundName;
    }

    @Override
    public String getMessage() {

        String message = super.getMessage();

        if(message == null){
            message = "";
        }

        return message
                .concat("(object not found: ")
                .concat(this.objectNotFoundName).concat(")");
    }

    public String getObjectNotFoundName() {
        return objectNotFoundName;
    }
}
