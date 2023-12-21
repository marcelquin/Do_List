package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.BackupDev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupDevRepository extends JpaRepository<BackupDev, Long> {


}
