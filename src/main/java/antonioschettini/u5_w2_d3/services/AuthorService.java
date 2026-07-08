package antonioschettini.u5_w2_d3.services;

import antonioschettini.u5_w2_d3.entities.Author;
import antonioschettini.u5_w2_d3.exceptions.NotFoundException;
import antonioschettini.u5_w2_d3.payloads.NewAuthorPayload;
import antonioschettini.u5_w2_d3.repositories.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    //post
    public Author salvaAutore(NewAuthorPayload body) {
        Author nuovoAutore = new Author(
                body.getNome(),
                body.getCognome(),
                body.getEmail(),
                body.getDataDiNascita()
        );

        String urlAvatar = "https://ui-avatars.com/api/?name=" + body.getNome() + "+" + body.getCognome();
        nuovoAutore.setAvatar(urlAvatar);

        // salvo l'autore nella lista e lo ritorno

        return authorRepository.save(nuovoAutore);
    }


    //getbyid
    public Author trovaPerId(int id) {
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Autore con ID " + id + " non è stato trovato"));
    }

    //getall
    public Page<Author> trovaTutti(int numeroPagina, int quantitaElementi, String ordinaPer) {
        Pageable configurazionePaginazione = PageRequest.of(numeroPagina, quantitaElementi, Sort.by(ordinaPer));
        return authorRepository.findAll(configurazionePaginazione);
    }

    //Put
    public Author modificaAutore(int id, NewAuthorPayload body) {
        Author autoreTrovato = trovaPerId(id);
        autoreTrovato.setNome(body.getNome());
        autoreTrovato.setCognome(body.getCognome());
        autoreTrovato.setEmail(body.getEmail());
        autoreTrovato.setDataDiNascita(body.getDataDiNascita());

        //cambio anche l'avatar con le nuove iniziali
        String urlAvatar = "https://ui-avatars.com/api/?name=" + body.getNome() + "+" + body.getCognome();
        autoreTrovato.setAvatar(urlAvatar);

        return authorRepository.save(autoreTrovato);
    }

    //delete
    public void cancellaAutore(int id) {
        Author autoreTrovato = this.trovaPerId(id);
        authorRepository.delete(autoreTrovato);
    }
}
