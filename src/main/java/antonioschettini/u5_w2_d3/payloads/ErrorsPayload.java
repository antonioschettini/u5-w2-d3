package antonioschettini.u5_w2_d3.payloads;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorsPayload {
    private int status;
    private String messaggio;
    private LocalDateTime dataOra;

    public ErrorsPayload(String messaggio, LocalDateTime dataOra, int status) {
        this.status = status;
        this.messaggio = messaggio;
        this.dataOra = dataOra;
    }
}
