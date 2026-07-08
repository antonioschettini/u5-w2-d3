package antonioschettini.u5_w2_d2.services;

import antonioschettini.u5_w2_d2.entities.BlogPost;
import antonioschettini.u5_w2_d2.exceptions.NotFoundException;
import antonioschettini.u5_w2_d2.payloads.NewBlogPostPayload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogPostService {
    private final List<BlogPost> listaPost = new ArrayList<>();
    private int idCorrente = 1;

    //Metodo per salvare un nuovo post
    //Post
    public BlogPost salvaBlogPost(NewBlogPostPayload body) {
        BlogPost nuovoPost = new BlogPost(
                body.getCategoria(),
                body.getTitolo(),
                body.getContenuto(),
                body.getTempoDiLettura()
        );
        //Genero l'id in automatico con un ++
        nuovoPost.setId(this.idCorrente);
        this.idCorrente++;

        //Setto l'url per la cover
        nuovoPost.setCover("https://picsum.photos/200/300");

        this.listaPost.add(nuovoPost);
        return nuovoPost;
    }

    //Getall
    public List<BlogPost> trovaTutti() {
        return this.listaPost;
    }

    //Getbyid
    public BlogPost trovaPerId(int id) {
        return this.listaPost.stream()
                .filter(blogPost -> blogPost.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Blogpost con id: " + id + " non è stato trovato"));
    }

    //Put
    public BlogPost modificaBlogPost(int id, NewBlogPostPayload body) {
        BlogPost postTrovato = this.trovaPerId(id);
        // Setto il post recuperato con il medoto findy by id con il get dal payload/body
        postTrovato.setCategoria(body.getCategoria());
        postTrovato.setTitolo(body.getTitolo());
        postTrovato.setContenuto(body.getContenuto());
        postTrovato.setTempoLettura(body.getTempoDiLettura());
        return postTrovato;
    }

    //Delete
    public void cancellaBlogPost(int id) {
        BlogPost postTrovato = this.trovaPerId(id);
        // se esiste lo rimuoviamo dalla lista
        this.listaPost.remove(postTrovato);
    }

}
