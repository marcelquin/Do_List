package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {


}
