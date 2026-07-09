package antonioschettini.u5_w2_d3.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "blog_posts")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // server deciso

    private String categoria;
    private String titolo;
    private String cover; // indirizzo url fisso
    private String contenuto;
    private int tempoLettura;

    @ManyToOne// per cancellare autore ed a cascata tutti i suoi posts.
    @JoinColumn(name = "author_id")
    private Author autore;

    //Costruttore senza id e cover
    public BlogPost(String categoria, String titolo, String contenuto, int tempoLettura) {
        this.categoria = categoria;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tempoLettura = tempoLettura;
    }
}
