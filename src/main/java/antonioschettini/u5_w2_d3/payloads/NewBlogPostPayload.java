package antonioschettini.u5_w2_d2.payloads;

import lombok.Data;

@Data
public class NewBlogPostPayload {
    private String categoria;
    private String titolo;
    private String contenuto;
    private int tempoDiLettura;
}
