package antonioschettini.u5_w2_d3.payloads;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorsPayload {
    private int status;
    private String messaggio;
    private LocalDateTime dataOra;
    private List<String> listaErrori;

    public ErrorsPayload(int status, String messaggio, LocalDateTime dataOra, List<String> listaErrori) {
        this.status = status;
        this.messaggio = messaggio;
        this.dataOra = dataOra;
        this.listaErrori = listaErrori;
    }

    public ErrorsPayload(int status, String messaggio, LocalDateTime dataOra) {
        this.status = status;
        this.messaggio = messaggio;
        this.dataOra = dataOra;
        this.listaErrori = null; // Rimarrà vuota se non serve
    }
}
