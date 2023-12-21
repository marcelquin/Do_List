package baseAPI.API.Sistema.Service;


import baseAPI.API.Sistema.Enum.AcaoBackup;
import baseAPI.API.Sistema.Enum.StatusLimitado;
import baseAPI.API.Sistema.Exceptions.EntityNotFoundException;
import baseAPI.API.Sistema.Exceptions.MethodArgsNullException;
import baseAPI.API.Sistema.Model.Atividade;
import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Records.AtividadeRecord;
import baseAPI.API.Sistema.Repository.AtividadeRepository;
import baseAPI.API.Sistema.Repository.BackupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


import static baseAPI.API.Sistema.Enum.Status.*;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    @Autowired
    private AtividadeRepository repository;
    @Autowired
    private BackupRepository backupRepository;

    public ResponseEntity<List<Atividade>> listar()
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

    public ResponseEntity<Atividade> buscaPorId(Long id)
    {
        try
        {
            if(repository.existsById(id))
            {
                Atividade atividade = repository.findById(id).get();
                return new ResponseEntity<>(atividade, HttpStatus.OK);
            }
            else
            {throw  new EntityNotFoundException("o "+id+" não corresponde a nenhum registro.");}

        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    //recort to campos string
    public ResponseEntity<AtividadeRecord> salvar(String nome, String descrisao)
    {
        try
        {
            if(nome != null && descrisao != null)
            {
                AtividadeRecord record = new AtividadeRecord(nome,descrisao);
                Atividade atividade = new Atividade(record);
                atividade.setDataCriacao(LocalDateTime.now());
                atividade.setStatus(AGUARDANDO);
                atividade.setFinalizada(false);
                repository.save(atividade);
                Backup backup = new Backup(record);
                backup.setAcaoBackup(AcaoBackup.ATIVIDADE_CRIADA);
                backup.setResponsavel(null);
                backup.setStatus(AGUARDANDO);
                backup.setDataCriacao(LocalDateTime.now());
                backup.setFinalizada(false);
                backupRepository.save(backup);
            }
        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    //recort to campos string
    public ResponseEntity<AtividadeRecord> alterarAtividade(Long id, String nome, String descrisao)
    {
        try {
            if(repository.existsById(id))
            {
               Atividade atividade = repository.findById(id).get();
               atividade.setNome(nome);
               atividade.setDescrisao(descrisao);
               atividade.setDataAlteracao(LocalDateTime.now());repository.save(atividade);
                Backup backup = new Backup();
                backup.setAcaoBackup(AcaoBackup.ATIVIDADE_ALTERACAO);
                backup.setNome(atividade.getNome());
                backup.setDescrisao(atividade.getDescrisao());
                backup.setStatus(atividade.getStatus());
                backup.setDataCriacao(atividade.getDataCriacao());
                backup.setDataAlteracao(atividade.getDataAlteracao());
            }
            else
            {throw  new EntityNotFoundException("o "+id+" não corresponde a nenhum registro.");}
        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<AtividadeRecord> alterarStatus(Long id, StatusLimitado status)
    {
        try {
            if(repository.existsById(id))
            {
                Atividade atividade = repository.findById(id).get();
                if(status != null)
                {
                   if(status == StatusLimitado.INICIO)
                    {
                        if(atividade.getStatus() != INICIO)
                        atividade.setStatus(INICIO);
                        atividade.setDataAlteracao(LocalDateTime.now());
                    }
                    if(status == StatusLimitado.AGUARDANDO_APROVACAO)
                    {
                        if(atividade.getStatus() != AGUARDANDO_APROVACAO)
                        atividade.setStatus(AGUARDANDO_APROVACAO);
                        atividade.setDataAlteracao(LocalDateTime.now());
                    }
                    repository.save(atividade);
                    Backup backup = new Backup();
                    backup.setAcaoBackup(AcaoBackup.ATIVIDADE_ALTERACAO);
                    backup.setNome(atividade.getNome());
                    backup.setDescrisao(atividade.getDescrisao());
                    backup.setStatus(atividade.getStatus());
                    backup.setDataCriacao(atividade.getDataCriacao());
                    backup.setDataAlteracao(atividade.getDataAlteracao());
                    backup.setFinalizada(false);
                }
                else
                {throw  new MethodArgsNullException("o campo deve ser preenxido"); }

            }
            else
            {throw  new EntityNotFoundException("o "+id+" não corresponde a nenhum registro.");}
        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<AtividadeRecord> FinalizarAtividade(Long id, Boolean finalizado)
    {
        try {
            if(repository.existsById(id))
            {
                Atividade atividade = repository.findById(id).get();
                if(finalizado == true)
                {
                    atividade.setDataAlteracao(LocalDateTime.now());
                    atividade.setFinalizada(Boolean.TRUE);
                    repository.save(atividade);
                    Backup backup = new Backup();
                    backup.setAcaoBackup(AcaoBackup.ATIVIDADE_FINALIZADA);
                    backup.setNome(atividade.getNome());
                    backup.setDescrisao(atividade.getDescrisao());
                    backup.setStatus(atividade.getStatus());
                    backup.setDataCriacao(atividade.getDataCriacao());
                    backup.setDataAlteracao(atividade.getDataAlteracao());
                    backup.setFinalizada(false);
                    backupRepository.save(backup);
                }
                else
                {
                  atividade.setStatus(AGUARDANDO);
                  atividade.setDataAlteracao(LocalDateTime.now());
                    Backup backup = new Backup();
                    backup.setAcaoBackup(AcaoBackup.ATIVIDADE_ALTERACAO);
                    backup.setNome(atividade.getNome());
                    backup.setDescrisao(atividade.getDescrisao());
                    backup.setStatus(atividade.getStatus());
                    backup.setDataCriacao(atividade.getDataCriacao());
                    backup.setDataAlteracao(atividade.getDataAlteracao());
                    backup.setFinalizada(false);
                    backupRepository.save(backup);
                }
            }
            else
            {throw  new EntityNotFoundException("o "+id+" não corresponde a nenhum registro.");}
        }catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<AtividadeRecord> delete(Long id)
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
            e.getMessage();
        }
        return null;
    }



}