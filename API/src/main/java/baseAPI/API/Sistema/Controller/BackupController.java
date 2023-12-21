package baseAPI.API.Sistema.Controller;

import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.BackupDev;
import baseAPI.API.Sistema.Service.BackupDevService;
import baseAPI.API.Sistema.Service.BackupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("backup")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "backup", description = "Manipula dados de lista da entidade Dev")
public class BackupController {

    @Autowired
    BackupService service;
    @Autowired
    BackupDevService service2;

    @Operation(summary = "Lista Registro da tabela Backup Atividade", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/listarBackupAtividades")
    public ResponseEntity<List<Backup>> listarBackupAtividades()
    { return service.listarBackupAtividades();}


    @Operation(summary = "Lista Registro da tabela Backup dev", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/listarBackupDev")
    public ResponseEntity<List<BackupDev>> listarBackupDev()
    { return service2.listarBackupDev();}
}
