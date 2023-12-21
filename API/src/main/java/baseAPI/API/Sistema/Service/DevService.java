package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.Enum.AcaoBackup;
import baseAPI.API.Sistema.Enum.Equipe;
import baseAPI.API.Sistema.Enum.Nivel;
import baseAPI.API.Sistema.Enum.Status;
import baseAPI.API.Sistema.Exceptions.EntityNotFoundException;
import baseAPI.API.Sistema.Exceptions.MethodArgsNullException;
import baseAPI.API.Sistema.Model.Atividade;
import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.BackupDev;
import baseAPI.API.Sistema.Model.Dev;
import baseAPI.API.Sistema.Records.AtividadeRecord;
import baseAPI.API.Sistema.Records.DevRecord;
import baseAPI.API.Sistema.Repository.AtividadeRepository;
import baseAPI.API.Sistema.Repository.BackupDevRepository;
import baseAPI.API.Sistema.Repository.BackupRepository;
import baseAPI.API.Sistema.Repository.DevRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
public class DevService {

    @Autowired
    DevRepository repository;
    @Autowired
    AtividadeRepository atividadeRepository;
    @Autowired
    BackupDevRepository backupDevRepository;
    @Autowired
    BackupRepository backupRepository;

    public ResponseEntity<List<Dev>> listar()
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

    public ResponseEntity<Dev> buscaPorId(Long id)
    {
        try
        {
            if(repository.existsById(id))
            {
                Dev dev = repository.findById(id).get();
                return new ResponseEntity<>(dev, HttpStatus.OK);
            }
            else
            {throw  new EntityNotFoundException("o "+id+" não corresponde a nenhum registro.");}

        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<DevRecord> NovoDev(String nome, Nivel nivel, Equipe equipe)
    {
        try{
            if(nome != null)
            {
                DevRecord record = new DevRecord(nome);
                Dev dev = new Dev(record);
                dev.setEquipe(equipe);
                dev.setNivel(nivel);
                repository.save(dev);
                BackupDev backupDev = new BackupDev(record);
                backupDev.setAcaoBackup(AcaoBackup.DEV_CRIADO);
                backupDev.setEquipe(equipe);
                backupDev.setNivel(nivel);
                backupDev.setTimeStamp(LocalDateTime.now());
                backupDevRepository.save(backupDev);
                return new ResponseEntity<>(CREATED);
            }
            else
            {
                throw new MethodArgsNullException("os dados devem ser preencidos");
            }
        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<DevRecord> AlterarDev(Long id, String nome, Nivel nivel, Equipe equipe)
    {
        try{
            if(repository.existsById(id))
            {
              Dev dev = repository.findById(id).get();
              if(nome != null)
              {
                  dev.setNome(nome);
                  dev.setEquipe(equipe);
                  dev.setNivel(nivel);
                  repository.save(dev);
                  BackupDev backupDev = new BackupDev();
                  backupDev.setNome(nome);
                  backupDev.setAcaoBackup(AcaoBackup.DEV_ALTERACAO);
                  backupDev.setEquipe(equipe);
                  backupDev.setNivel(nivel);
                  backupDev.setTimeStamp(LocalDateTime.now());
                  backupDevRepository.save(backupDev);
                  return new ResponseEntity<>(OK);
              }
              else
              {throw new MethodArgsNullException("os dados devem ser preencidos"); }
            }
            else
            {throw  new EntityNotFoundException("o "+id+" não corresponde a nenhum registro.");}


        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<DevRecord> AlterarEquipe(Long id, Equipe equipe)
    {
        try{
            if(repository.existsById(id))
            {
                Dev dev = repository.findById(id).get();
                dev.setEquipe(equipe);
                repository.save(dev);
                BackupDev backupDev = new BackupDev();
                backupDev.setAcaoBackup(AcaoBackup.DEV_ALTERACAO);
                backupDev.setNome(dev.getNome());
                backupDev.setNivel(dev.getNivel());
                backupDev.setEquipe(equipe);
                backupDev.setTimeStamp(LocalDateTime.now());
                backupDevRepository.save(backupDev);
                return new ResponseEntity<>(OK);
            }
            else
            {
                throw new MethodArgsNullException("os dados devem ser preencidos");
            }
        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<DevRecord> AlterarNivel(Long id, Nivel nivel)
    {
        try{
            if(repository.existsById(id))
            {
                Dev dev = repository.findById(id).get();
                dev.setNivel(nivel);
                repository.save(dev);
                BackupDev backupDev = new BackupDev();
                backupDev.setAcaoBackup(AcaoBackup.DEV_ALTERACAO);
                backupDev.setNome(dev.getNome());
                backupDev.setNivel(nivel);
                backupDev.setEquipe(dev.getEquipe());
                backupDev.setTimeStamp(LocalDateTime.now());
                backupDevRepository.save(backupDev);
                return new ResponseEntity<>(OK);
            }
            else
            {
                throw new MethodArgsNullException("os dados devem ser preencidos");
            }
        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<DevRecord> AtribuirAtividade(Long id, Long idAtividade)
    {
        try{
            if(repository.existsById(id))
            {
                Dev dev = repository.findById(id).get();
                if(atividadeRepository.existsById(idAtividade))
                {
                    Atividade atividade = atividadeRepository.findById(idAtividade).get();
                    atividade.setResponsavel(dev);
                    atividade.setStatus(Status.INICIO);
                    atividade.setFinalizada(false);
                    atividadeRepository.save(atividade);
                    Backup backup = new Backup();
                    backup.setAcaoBackup(AcaoBackup.DEV_ATRIBUIDO);
                    backup.setNome(atividade.getNome());
                    backup.setDescrisao(atividade.getDescrisao());
                    backup.setStatus(atividade.getStatus());
                    backup.setResponsavel(dev);
                    backup.setDataCriacao(atividade.getDataCriacao());
                    backup.setDataAlteracao(atividade.getDataAlteracao());
                    return new ResponseEntity<>(OK);
                }
                else
                {throw  new EntityNotFoundException("o "+id+" não corresponde a nenhum registro.");}
            }
            else
            {
                throw new MethodArgsNullException("os dados devem ser preencidos");
            }
        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<DevRecord> delete(Long id)
    {
        try{
            if(repository.existsById(id))
            {
                repository.deleteById(id);
                return new ResponseEntity<>(OK);
            }
            else
            {throw  new EntityNotFoundException("o "+id+" não corresponde a nenhum registro.");}
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }
}
