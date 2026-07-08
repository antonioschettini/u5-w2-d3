package antonioschettini.u5_w2_d2.services;

import antonioschettini.u5_w2_d2.entities.Author;
import antonioschettini.u5_w2_d2.exceptions.NotFoundException;
import antonioschettini.u5_w2_d2.payloads.NewAuthorPayload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    private final List<Author> listaAutori = new ArrayList<>();
    private int idCorrente = 1;


    //post
    public Author salvaAutore(NewAuthorPayload body) {
        Author nuovoAutore = new Author(
                body.getNome(),
                body.getCognome(),
                body.getEmail(),
                body.getDataDiNascita()
        );
        //assegno id con ++ ed avatar
        nuovoAutore.setId(this.idCorrente);
        this.idCorrente++;

        String urlAvatar = "https://ui-avatars.com/api/?name=" + body.getNome() + "+" + body.getCognome();
        nuovoAutore.setAvatar(urlAvatar);

        // salvo l'autore nella lista e lo ritorno
        this.listaAutori.add(nuovoAutore);
        return nuovoAutore;
    }


    //getbyid
    public Author trovaPerId(int id) {
        return this.listaAutori.stream()
                .filter(author -> author.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Autore con id: " + id + " non è stato trovato"));
    }

    //getall
    public List<Author> trovatutti() {
        return this.listaAutori;
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

        return autoreTrovato;
    }

    //delete
    public void cancellaAutore(int id) {
        Author autoreTrovato = this.trovaPerId(id);
        this.listaAutori.remove(autoreTrovato);
    }
}
