package antonioschettini.u5_w2_d3.payloads;

import lombok.Data;

@Data
public class NewBlogPostPayload {
    private String categoria;
    private String titolo;
    private String contenuto;
    private int tempoDiLettura;
    private int authorId; // il'id dell'autore che ha scritto il post
}
