package antonioschettini.u5_w2_d3.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record NewBlogPostPayload(
        @NotBlank(message = "La categoria è obbligatoria e non può essere vuota")
        String categoria,

        @NotBlank(message = "Il titolo del post è obbligatorio")
        @Size(min = 2, max = 30, message = "il titolo deve avere un numero di caratteri compreso tra 2 e 30")
        String titolo,

        @NotBlank(message = "Il contenuto del blog non può essere vuoto")
        String contenuto,

        @Min(value = 1, message = "Il tempo di lettura deve essere almeno di 1 minuto")
        int tempoDiLettura,

        @Min(value = 1, message = "L'id dell'autore deve essere un numero valido per gli id salvati al db")
        int authorId // il'id dell'autore che ha scritto il post)
) {
}
