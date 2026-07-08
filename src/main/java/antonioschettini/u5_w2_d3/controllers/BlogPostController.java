package antonioschettini.u5_w2_d2.controllers;

import antonioschettini.u5_w2_d2.entities.BlogPost;
import antonioschettini.u5_w2_d2.payloads.NewBlogPostPayload;
import antonioschettini.u5_w2_d2.services.BlogPostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogPosts") // setto l'indirizzo di base per tutti
public class BlogPostController {
    private final BlogPostService blogPostService;

    //costruttore per l'autowired
    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    // endpoint post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // risponde con codice 201 creato con successo
    public BlogPost creaPost(@RequestBody NewBlogPostPayload body) {
        return this.blogPostService.salvaBlogPost(body);
    }

    //endpoint getall
    @GetMapping
    public List<BlogPost> prendiTuttiIPost() {
        return this.blogPostService.trovaTutti();
    }

    //ednpoint getone
    @GetMapping("{id}")
    public BlogPost prendiPostSingolo(@PathVariable int id) {
        return this.blogPostService.trovaPerId(id);
    }

    //endpoint put
    @PutMapping("{id}")
    public BlogPost aggiornaPost(@PathVariable int id, @RequestBody NewBlogPostPayload body) {
        return this.blogPostService.modificaBlogPost(id, body);
    }

    //endpoint delete
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 nessun contenuto
    public void eliminaPost(@PathVariable int id) {
        this.blogPostService.cancellaBlogPost(id);
    }

}
