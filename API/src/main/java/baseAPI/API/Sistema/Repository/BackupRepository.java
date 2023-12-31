package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.Dev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupRepository extends JpaRepository<Backup, Long> {


}
