package antonioschettini.u5_w2_d3.controllers;

import antonioschettini.u5_w2_d3.entities.BlogPost;
import antonioschettini.u5_w2_d3.exceptions.BadRequestException;
import antonioschettini.u5_w2_d3.payloads.NewBlogPostPayload;
import antonioschettini.u5_w2_d3.services.BlogPostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public BlogPost creaPost(@RequestBody @Valid NewBlogPostPayload body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errorList = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new BadRequestException(errorList);
        }
        return this.blogPostService.salvaBlogPost(body);
    }

    //post per modifica cover
    @PostMapping("/{id}/cover")
    public String uploadCover(@PathVariable int id, @RequestParam("cover") MultipartFile file) {
        return this.blogPostService.caricaCover(id, file);
    }

    //endpoint getall
    @GetMapping
    public Page<BlogPost> prendiTuttiIPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.blogPostService.trovaTutti(page, size, sortBy);
    }

    //ednpoint getone
    @GetMapping("{id}")
    public BlogPost prendiPostSingolo(@PathVariable int id) {
        return this.blogPostService.trovaPerId(id);
    }

    //endpoint put
    @PutMapping("{id}")
    public BlogPost aggiornaPost(@PathVariable int id, @RequestBody @Valid NewBlogPostPayload body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errorList = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new BadRequestException(errorList);
        }
        return this.blogPostService.modificaBlogPost(id, body);
    }

    //endpoint delete
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 nessun contenuto
    public void eliminaPost(@PathVariable int id) {
        this.blogPostService.cancellaBlogPost(id);
    }

}
