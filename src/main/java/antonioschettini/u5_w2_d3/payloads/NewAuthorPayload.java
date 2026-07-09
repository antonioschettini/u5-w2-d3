package antonioschettini.u5_w2_d3.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;


public record NewAuthorPayload(
        @NotBlank(message = "La nome è obbligatorio, non può neanche essere una stringa vuota")
        @Size(min = 2, max = 30, message = "Il nome deve avere un numero di caratteri compreso da 2 e 30")
        String nome,


        @NotBlank(message = "Il cognome è obbligatorio, non può neanche essere una stringa vuota")
        @Size(min = 2, max = 30, message = "Il cognome deve avere un numero di caratteri compreso da 2 e 30")
        String cognome,


        @NotBlank(message = "L'email è obbligatoria, non può neanche essere una stringa vuota")
        @Email(message = "Attenzione l'indirizzo inserito non è valido controlla che sia scritto correttamente")
        String email,

        @NotNull(message = "La data di nascita è obbligatoria")
        @Past(message = "La data di nascita deve essere nel passato")
        LocalDate dataDiNascita
) {

}
