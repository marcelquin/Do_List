package baseAPI.API.Service;

import baseAPI.API.Sistema.Enum.AcaoBackup;
import baseAPI.API.Sistema.Enum.Status;
import baseAPI.API.Sistema.Model.Atividade;
import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Records.AtividadeRecord;
import baseAPI.API.Sistema.Repository.AtividadeRepository;
import baseAPI.API.Sistema.Repository.BackupRepository;
import baseAPI.API.Sistema.Service.AtividadeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static baseAPI.API.Sistema.Enum.Status.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtividadeServiceTest {

    @InjectMocks
    AtividadeService service;

    @Mock
    AtividadeRepository atividadeRepository;

    @Mock
    BackupRepository backupRepository;
    @Test
    void DeveSalvarComSucesso()
    {
        /*
            Salva atividade com dev null, salvando juntamente um registro de backup
         */
       // Dev dev = new Dev(1L, "dev", Equipe.BACKEND, Nivel.JUNIOR);
        AtividadeRecord record = new AtividadeRecord("teste","teste");
        Atividade atividade = new Atividade(record);
        atividade.setId(1L);
        atividade.setStatus(Status.AGUARDANDO);
        atividade.setResponsavel(null);
        atividade.setDataCriacao(LocalDateTime.now());
        atividade.setFinalizada(false);
        Backup backup = new Backup(record);
        backup.setAcaoBackup(AcaoBackup.ATIVIDADE_CRIADA);
        backup.setResponsavel(null);
        backup.setStatus(AGUARDANDO);
        backup.setDataCriacao(LocalDateTime.now());
        backup.setFinalizada(false);

        when(atividadeRepository.save(atividade)).thenReturn(atividade);
        when(backupRepository.save(backup)).thenReturn(backup);


        assertEquals(atividade, atividadeRepository.save(atividade));
        verifyNoMoreInteractions(atividadeRepository);
        assertEquals(backup, backupRepository.save(backup));
        verifyNoMoreInteractions(backupRepository);



    }

    @Test
    void DeveSalvarSemSucesso()
    {
        /*
            Tenta salva atividade com dev null juntamente um registro de backup,
            porem retornando o repository retorna nulo por alguma razão
         */
        // Dev dev = new Dev(1L, "dev", Equipe.BACKEND, Nivel.JUNIOR);
        AtividadeRecord record = new AtividadeRecord("teste","teste");
        Atividade atividade = new Atividade(record);


        when(atividadeRepository.save(null)).thenReturn(null);


        assertEquals(null, atividadeRepository.save(null));
        verifyNoMoreInteractions(atividadeRepository);
        verifyNoInteractions(backupRepository);



    }

    @Test
    void DeveAlterarStatusComSussesso()
    {
        Atividade atividade = new Atividade(1L, "test", "test",null,AGUARDANDO,LocalDateTime.now(),null,false);
        atividade.setStatus(INICIO);

        assertEquals(INICIO, atividade.getStatus());

    }

    @Test
    void DeveAlterarStatusSemSussesso()
    {
        Atividade atividade = new Atividade(1L, "test", "test",null,AGUARDANDO,LocalDateTime.now(),null,false);
        atividade.setStatus(null);

        assertNotEquals(INICIO, atividade.getStatus());

    }

    @Test
    void DeveAlterarFinalizarSemSussesso()
    {
        Atividade atividade = new Atividade(1L, "test", "test",null,AGUARDANDO,LocalDateTime.now(),null,false);
        atividade.setFinalizada(false);

        assertEquals(false, atividade.getFinalizada());

    }

    @Test
    void DeveBuscarEntidadePorId()
    {
        Atividade atividade = new Atividade(1L, "test", "test",null,AGUARDANDO,LocalDateTime.now(),null,false);
        //atividade.setStatus(null);
        when(atividadeRepository.existsById(1L)).thenReturn(true);

        assertEquals(true, atividadeRepository.existsById(1L));
    }



}
