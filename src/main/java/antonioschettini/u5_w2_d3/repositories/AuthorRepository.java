package antonioschettini.u5_w2_d3.repositories;

import antonioschettini.u5_w2_d3.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
