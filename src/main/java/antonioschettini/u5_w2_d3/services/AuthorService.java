package antonioschettini.u5_w2_d3.services;

import antonioschettini.u5_w2_d3.entities.Author;
import antonioschettini.u5_w2_d3.exceptions.BadRequestException;
import antonioschettini.u5_w2_d3.exceptions.NotFoundException;
import antonioschettini.u5_w2_d3.payloads.NewAuthorPayload;
import antonioschettini.u5_w2_d3.repositories.AuthorRepository;
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
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final Cloudinary cloudinary;

    public AuthorService(AuthorRepository authorRepository, Cloudinary cloudinary) {
        this.authorRepository = authorRepository;
        this.cloudinary = cloudinary;
    }

    //nuovometodo per cambiare l'avatar
    public String caricaAvatar(int id, MultipartFile file) {
        try {
            // Spediamo il file a Cloudinary e salviamo la risposta in una mappa
            Map mappaRisposta = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            // Estraiamo l'URL della foto appena caricata
            String urlDellaFoto = (String) mappaRisposta.get("url");

            // Cerchiamo l'autore nel database usando il metodo che hai già scritto tu
            Author trovato = this.trovaPerId(id);

            // Sostituiamo il vecchio avatar con il nuovo link internet
            trovato.setAvatar(urlDellaFoto);

            // Salviamo l'autore aggiornato nel database
            this.authorRepository.save(trovato);

            // Restituiamo il link della foto (così possiamo vederlo su Postman)
            return urlDellaFoto;

        } catch (IOException e) {
            // Se qualcosa va storto durante il caricamento (es. file corrotto), lanciamo un errore
            throw new BadRequestException("Errore durante il caricamento del file dell'immagine!");
        }
    }

    //post
    public Author salvaAutore(NewAuthorPayload body) {
        Author nuovoAutore = new Author(
                body.nome(),
                body.cognome(),
                body.email(),
                body.dataDiNascita()
        );

        String urlAvatar = "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome();
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
        autoreTrovato.setNome(body.nome());
        autoreTrovato.setCognome(body.cognome());
        autoreTrovato.setEmail(body.email());
        autoreTrovato.setDataDiNascita(body.dataDiNascita());

        //cambio anche l'avatar con le nuove iniziali
        String urlAvatar = "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome();
        autoreTrovato.setAvatar(urlAvatar);

        return authorRepository.save(autoreTrovato);
    }

    //delete
    public void cancellaAutore(int id) {
        Author autoreTrovato = this.trovaPerId(id);
        authorRepository.delete(autoreTrovato);
    }
}
