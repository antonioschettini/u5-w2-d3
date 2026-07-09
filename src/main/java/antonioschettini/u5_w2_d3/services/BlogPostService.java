package antonioschettini.u5_w2_d3.services;

import antonioschettini.u5_w2_d3.entities.Author;
import antonioschettini.u5_w2_d3.entities.BlogPost;
import antonioschettini.u5_w2_d3.exceptions.NotFoundException;
import antonioschettini.u5_w2_d3.payloads.NewBlogPostPayload;
import antonioschettini.u5_w2_d3.repositories.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;
    private final AuthorService authorService;

    public BlogPostService(BlogPostRepository blogPostRepository, AuthorService authorService) {
        this.blogPostRepository = blogPostRepository;
        this.authorService = authorService; // serve per cercare se l'autore esiste davvero
    }

    //Metodo per salvare un nuovo post
    //Post
    public BlogPost salvaBlogPost(NewBlogPostPayload body) {
        //cerchiamo l'autore per verificare se esiste a db
        Author autore = authorService.trovaPerId(body.getAuthorId());
        BlogPost nuovoPost = new BlogPost(
                body.getCategoria(),
                body.getTitolo(),
                body.getContenuto(),
                body.getTempoDiLettura()
        );

        //Setto l'url per la cover
        nuovoPost.setCover("https://picsum.photos/200/300");

        nuovoPost.setAutore(autore);
        return blogPostRepository.save(nuovoPost);
    }

    //Getall
    public Page<BlogPost> trovaTutti(int numeroPagina, int quantitaElementi, String ordinaPer) {
        Pageable configurazionePaginazione = PageRequest.of(numeroPagina, quantitaElementi, Sort.by(ordinaPer));
        return blogPostRepository.findAll(configurazionePaginazione);
    }

    //Getbyid
    public BlogPost trovaPerId(int id) {
        return blogPostRepository.findById(id).orElseThrow(() -> new NotFoundException("Post con id: " + id + " non è stato trovato a db!"));
    }

    //Put
    public BlogPost modificaBlogPost(int id, NewBlogPostPayload body) {
        BlogPost postTrovato = this.trovaPerId(id);
        Author nuovoAutore = authorService.trovaPerId(body.getAuthorId());
        // Setto il post recuperato con il medoto findy by id con il get dal payload/body
        postTrovato.setCategoria(body.getCategoria());
        postTrovato.setTitolo(body.getTitolo());
        postTrovato.setContenuto(body.getContenuto());
        postTrovato.setTempoLettura(body.getTempoDiLettura());
        postTrovato.setAutore(nuovoAutore);
        return blogPostRepository.save(postTrovato);
    }

    //Delete
    public void cancellaBlogPost(int id) {
        BlogPost postTrovato = this.trovaPerId(id);
        // se esiste lo rimuoviamo dalla lista
        System.out.println(postTrovato);
        blogPostRepository.delete(postTrovato);
    }

}
