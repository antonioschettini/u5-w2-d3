package antonioschettini.u5_w2_d2.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlogPost {
    private int id; // server deciso
    private String categoria;
    private String titolo;
    private String cover; // indirizzo url fisso
    private String contenuto;
    private int tempoLettura;

    //Costruttore senza id e cover
    public BlogPost(String categoria, String titolo, String contenuto, int tempoLettura) {
        this.categoria = categoria;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tempoLettura = tempoLettura;
    }
}
