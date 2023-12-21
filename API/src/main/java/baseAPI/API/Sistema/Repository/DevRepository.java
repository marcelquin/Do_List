package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.Atividade;
import baseAPI.API.Sistema.Model.Dev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevRepository extends JpaRepository<Dev, Long> {


}
