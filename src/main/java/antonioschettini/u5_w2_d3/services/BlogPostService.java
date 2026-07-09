package antonioschettini.u5_w2_d3.services;

import antonioschettini.u5_w2_d3.entities.Author;
import antonioschettini.u5_w2_d3.entities.BlogPost;
import antonioschettini.u5_w2_d3.exceptions.BadRequestException;
import antonioschettini.u5_w2_d3.exceptions.NotFoundException;
import antonioschettini.u5_w2_d3.payloads.NewBlogPostPayload;
import antonioschettini.u5_w2_d3.repositories.BlogPostRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;
    private final AuthorService authorService;
    private final Cloudinary cloudinary;

    public BlogPostService(BlogPostRepository blogPostRepository, AuthorService authorService, Cloudinary cloudinary) {
        this.blogPostRepository = blogPostRepository;
        this.authorService = authorService; // serve per cercare se l'autore esiste davvero
        this.cloudinary = cloudinary;
    }

    //Nuovo metodo per caricare la copertina
    public String caricaCover(int id, MultipartFile file) {
        try {
            Map mappaRisposta = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String urlDellaFoto = (String) mappaRisposta.get("url");
            BlogPost trovato = this.trovaPerId(id);
            trovato.setCover(urlDellaFoto);
            this.blogPostRepository.save(trovato);
            return urlDellaFoto;
        } catch (IOException ex) {
            throw new BadRequestException("Errore durante il caricamento della copertina");
        }
    }

    //Metodo per salvare un nuovo post
    //Post
    public BlogPost salvaBlogPost(NewBlogPostPayload body) {
        //cerchiamo l'autore per verificare se esiste a db
        Author autore = authorService.trovaPerId(body.authorId());
        BlogPost nuovoPost = new BlogPost(
                body.categoria(),
                body.titolo(),
                body.contenuto(),
                body.tempoDiLettura()
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
        Author nuovoAutore = authorService.trovaPerId(body.authorId());
        // Setto il post recuperato con il medoto findy by id con il get dal payload/body
        postTrovato.setCategoria(body.categoria());
        postTrovato.setTitolo(body.titolo());
        postTrovato.setContenuto(body.contenuto());
        postTrovato.setTempoLettura(body.tempoDiLettura());
        postTrovato.setAutore(nuovoAutore);
        return blogPostRepository.save(postTrovato);
    }

    //Delete
    public void cancellaBlogPost(int id) {
        BlogPost postTrovato = this.trovaPerId(id);
//        // se esiste lo rimuoviamo dalla lista
//        System.out.println(postTrovato);
        blogPostRepository.delete(postTrovato);
    }

}
