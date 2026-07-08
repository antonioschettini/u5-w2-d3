package antonioschettini.u5_w2_d3.controllers;

import antonioschettini.u5_w2_d3.entities.Author;
import antonioschettini.u5_w2_d3.payloads.NewAuthorPayload;
import antonioschettini.u5_w2_d3.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    public final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAutore(@RequestBody NewAuthorPayload body) {
        return this.authorService.salvaAutore(body);
    }

    //getall
    @GetMapping
    public Page<Author> prendiTuttiGliAutori(
            // inserisco i valori di default
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.authorService.trovaTutti(page, size, sortBy);
    }

    //getbyid
    @GetMapping("{id}")
    public Author prendiAutoreSingolo(@PathVariable int id) {
        return this.authorService.trovaPerId(id);
    }

    //Put
    @PutMapping("{id}")
    public Author aggiornaAutore(@PathVariable int id, @RequestBody NewAuthorPayload body) {
        return this.authorService.modificaAutore(id, body);
    }

    //delete
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaAutore(@PathVariable int id) {
        this.authorService.cancellaAutore(id);
    }
}
