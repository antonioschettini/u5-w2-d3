package antonioschettini.u5_w2_d3.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {
    private List<String> errorsList;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(List<String> errorsList) {
        super("Ci sono errori di validazione dati inseriti");
        this.errorsList = errorsList;
    }
}
