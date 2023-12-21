package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.BackupDev;
import baseAPI.API.Sistema.Repository.BackupDevRepository;
import baseAPI.API.Sistema.Repository.BackupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackupDevService {

    @Autowired
    BackupDevRepository repository;

    public ResponseEntity<List<BackupDev>> listarBackupDev()
    {
        try
        {
            return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);

        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

}
