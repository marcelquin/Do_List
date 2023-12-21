package baseAPI.API.Sistema.Controller;

import baseAPI.API.Sistema.Enum.Equipe;
import baseAPI.API.Sistema.Enum.Nivel;
import baseAPI.API.Sistema.Model.Dev;
import baseAPI.API.Sistema.Records.DevRecord;
import baseAPI.API.Sistema.Service.DevService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dev")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "dev", description = "Manipula dados de lista da entidade Dev")
public class DevController {

    @Autowired
    DevService service;

    @Operation(summary = "Lista Registro da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping
    public ResponseEntity<List<Dev>> listar()
    { return service.listar();}

    @Operation(summary = "Busca Registro da tabela por Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("buscaDevPorId")
    public ResponseEntity<Dev> buscaDevPorId(@RequestParam Long id)
    { return service.buscaPorId(id);}


    @Operation(summary = "Salva Novo Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salvo com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping()
    public ResponseEntity<DevRecord> NovoDev(@RequestParam String nome,@RequestParam Nivel nivel,@RequestParam Equipe equipe)
    { return service.NovoDev(nome, nivel, equipe);}

    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/AlterarDev")
    public ResponseEntity<DevRecord> AlterarDev(@RequestParam Long id,@RequestParam String nome,@RequestParam Nivel nivel,@RequestParam Equipe equipe)
    { return service.AlterarDev(id, nome, nivel, equipe);}


    @Operation(summary = "Altera equipe de atação do dev", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/AlterarEquipe")
    public ResponseEntity<DevRecord> AlterarEquipe(@RequestParam Long id, @RequestParam Equipe equipe)
    { return service.AlterarEquipe(id, equipe);}

    @Operation(summary = "Promove ou rebaixa o dev de nivel", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/AlterarNivel")
    public ResponseEntity<DevRecord> AlterarNivel(@RequestParam Long id, @RequestParam Nivel nivel)
    { return service.AlterarNivel(id, nivel);}

    @Operation(summary = "Atribui atividade ao dev", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/AtribuirAtividade")
    public ResponseEntity<DevRecord> AtribuirAtividade(@RequestParam Long id,@RequestParam Long idAtividade)
    { return service.AtribuirAtividade(id, idAtividade);}

    @Operation(summary = "Deleta Registro na tabela", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @DeleteMapping()
    public ResponseEntity<DevRecord> deletarDev(@RequestParam Long id)
    {  return service.delete(id);}

}
