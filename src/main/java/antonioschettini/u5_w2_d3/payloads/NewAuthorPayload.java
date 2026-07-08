package antonioschettini.u5_w2_d2.payloads;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewAuthorPayload {
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataDiNascita;
}
