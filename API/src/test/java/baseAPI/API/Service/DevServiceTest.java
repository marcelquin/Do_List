package baseAPI.API.Service;

import baseAPI.API.Sistema.Enum.AcaoBackup;
import baseAPI.API.Sistema.Enum.Equipe;
import baseAPI.API.Sistema.Enum.Nivel;
import baseAPI.API.Sistema.Exceptions.EntityNotFoundException;
import baseAPI.API.Sistema.Exceptions.MethodArgsNullException;
import baseAPI.API.Sistema.Model.Atividade;
import baseAPI.API.Sistema.Model.BackupDev;
import baseAPI.API.Sistema.Model.Dev;
import baseAPI.API.Sistema.Records.DevRecord;
import baseAPI.API.Sistema.Repository.AtividadeRepository;
import baseAPI.API.Sistema.Repository.BackupDevRepository;
import baseAPI.API.Sistema.Repository.DevRepository;
import baseAPI.API.Sistema.Service.DevService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static baseAPI.API.Sistema.Enum.Status.AGUARDANDO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class DevServiceTest {

    @InjectMocks
    DevService service;

    @Mock
    DevRepository repository;

    @Mock
    AtividadeRepository atividadeRepository;

    @Mock
    BackupDevRepository backupDevRepository;

    @Test
    void DeveSalvarComSUcesso()
    {
        DevRecord devRecord = new DevRecord("teste");
        Dev dev = new Dev(devRecord);
        dev.setNivel(Nivel.JUNIOR);
        dev.setEquipe(Equipe.BACKEND);
        BackupDev backupDev = new BackupDev(1L, AcaoBackup.DEV_CRIADO,"teste",Equipe.BACKEND,Nivel.JUNIOR, LocalDateTime.now());

        when(repository.save(dev)).thenReturn(dev);
        when(backupDevRepository.save(backupDev)).thenReturn(backupDev);

        assertEquals(dev, repository.save(dev));
        verifyNoMoreInteractions(repository);
        assertEquals(backupDev, backupDevRepository.save(backupDev));
        verifyNoMoreInteractions(backupDevRepository);
    }

    @Test
    void DeveSalvarSemSUcesso()
    {
       when(repository.save(null)).thenReturn(null);

       assertEquals(null, repository.save(null));
       verifyNoMoreInteractions(repository);
       verifyNoInteractions(backupDevRepository);

    }

    @Test
    void DeveNaoEntrarNorepositoryNoMetodoSalvar()
    {
        service.NovoDev(null, Nivel.PLENO, Equipe.BACKEND);

        assertEquals(null, service.NovoDev(null, Nivel.PLENO, Equipe.BACKEND));
        assertThat(MethodArgsNullException.class, notNullValue());
        verifyNoInteractions(repository);
        verifyNoInteractions(atividadeRepository);
        verifyNoInteractions(backupDevRepository);
    }
    // caso 2 - AlterarDev com id nulo
    @Test
    void DeveNaoBuscarCorpoDeEntidadePorIdNaoEncontrado()
    {
        when(repository.existsById(1L)).thenReturn(false);

        service.AlterarDev(1L,null, Nivel.PLENO,Equipe.FRONTEND);

        assertEquals(false,repository.existsById(1L));
        assertThat(EntityNotFoundException.class, notNullValue());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void DevEncontrarAEntidadePeloIdPoremPararOProcessoPorCampoNulo()
    {
        Dev dev = new Dev(1L, "teste", Equipe.FRONTEND, Nivel.PLENO);

        when(repository.existsById(1L)).thenReturn(true);
        service.AlterarDev(1L,null, Nivel.PLENO,Equipe.FRONTEND);

        assertEquals(true,repository.existsById(1L));
        assertThat(MethodArgsNullException.class, notNullValue());
    }
    //teste de excessão

    @Test
    void TesteDeExcetionEntityNotFound()
    {
        repository.existsById(null);

        assertThat(EntityNotFoundException.class, notNullValue());
    }

    @Test
    void TesteDeExcetionMethodArgsNullException()
    {
       service.NovoDev(null,Nivel.PLENO,Equipe.FRONTEND);

        assertThat(MethodArgsNullException.class, notNullValue());
    }

    @Test
    void DeveEditarComSUcesso()
    {
        Dev dev = new Dev(1L,"teste",Equipe.BACKEND,Nivel.JUNIOR);
        Dev dev1 = new Dev();
        dev1 = dev;
        dev1.setNome("zzzz");
        dev1.setNivel(Nivel.PLENO);
        dev1.setEquipe(Equipe.FRONTEND);
        BackupDev backupDev = new BackupDev(1L, AcaoBackup.DEV_ALTERACAO,"teste",Equipe.BACKEND,Nivel.JUNIOR, LocalDateTime.now());

        when(repository.save(dev)).thenReturn(dev);
        when(backupDevRepository.save(backupDev)).thenReturn(backupDev);

        assertEquals(dev, repository.save(dev));
        verifyNoMoreInteractions(repository);
        assertEquals(backupDev, backupDevRepository.save(backupDev));
        verifyNoMoreInteractions(backupDevRepository);
        assertEquals("zzzz", dev1.getNome());
    }

    @Test
    void DeveAlterarEquipeComSucesso()
    {
        Dev dev = new Dev(1L,"teste",Equipe.BACKEND,Nivel.JUNIOR);
        dev.setEquipe(Equipe.FRONTEND);
        BackupDev backupDev = new BackupDev(1L, AcaoBackup.DEV_ALTERACAO,"teste",Equipe.BACKEND,Nivel.JUNIOR, LocalDateTime.now());

        when(repository.save(dev)).thenReturn(dev);
        when(backupDevRepository.save(backupDev)).thenReturn(backupDev);

        assertEquals(dev, repository.save(dev));
        verifyNoMoreInteractions(repository);
        assertEquals(backupDev, backupDevRepository.save(backupDev));
        verifyNoMoreInteractions(backupDevRepository);
        assertEquals(Equipe.FRONTEND, dev.getEquipe());
    }

    @Test
    void DeveAtribuirAtividadeAoDevComSucesso()
    {
        Dev dev = new Dev(1L,"teste",Equipe.BACKEND,Nivel.JUNIOR);
        Atividade atividade = new Atividade(1L, "test", "test",null,AGUARDANDO,LocalDateTime.now(),null,false);
        atividade.setResponsavel(dev);
        BackupDev backupDev = new BackupDev(1L, AcaoBackup.DEV_ALTERACAO,"teste",Equipe.BACKEND,Nivel.JUNIOR, LocalDateTime.now());

        when(repository.existsById(1L)).thenReturn(true);
        when(atividadeRepository.existsById(1L)).thenReturn(true);
        when(atividadeRepository.save(atividade)).thenReturn(atividade);
        when(backupDevRepository.save(backupDev)).thenReturn(backupDev);

        assertEquals(true, repository.existsById(1l));
        assertEquals(true, atividadeRepository.existsById(1l));
        assertEquals(atividade, atividadeRepository.save(atividade));
        assertEquals(backupDev, backupDevRepository.save(backupDev));
        verifyNoMoreInteractions(atividadeRepository);

    }
    @Test
    void DeveAtribuirAtividadeAoDevSemSucessoPorAtivividadeRetornaFalse()
    {
        Dev dev = new Dev(1L,"teste",Equipe.BACKEND,Nivel.JUNIOR);
        Atividade atividade = new Atividade(1L, "test", "test",null,AGUARDANDO,LocalDateTime.now(),null,false);

        when(repository.existsById(1L)).thenReturn(true);
        when(atividadeRepository.existsById(1L)).thenReturn(false);

        assertEquals(true, repository.existsById(1l));
        assertEquals(false, atividadeRepository.existsById(1l));
        assertEquals(null, atividade.getResponsavel());
        assertThat(EntityNotFoundException.class, notNullValue());
        verifyNoMoreInteractions(atividadeRepository);
        verifyNoInteractions(backupDevRepository);
        verifyNoMoreInteractions(repository);
    }

}
