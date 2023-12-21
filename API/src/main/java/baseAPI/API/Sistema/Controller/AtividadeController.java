package baseAPI.API.Sistema.Controller;

import baseAPI.API.Sistema.Enum.Status;
import baseAPI.API.Sistema.Enum.StatusLimitado;
import baseAPI.API.Sistema.Model.Atividade;
import baseAPI.API.Sistema.Records.AtividadeRecord;
import baseAPI.API.Sistema.Service.AtividadeService;
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
@RequestMapping("lista")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "lista", description = "Manipula dados de lista de atividades")
public class AtividadeController {

    @Autowired
    private final AtividadeService service;

    @Operation(summary = "Lista Atividades cadastradas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @GetMapping()
    public ResponseEntity<List<Atividade>> listar()
    {return service.listar();}

    @Operation(summary = "Busca Registro por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @GetMapping("/buscaAtividadePorId")
    public ResponseEntity<Atividade> buscaAtividadePorId(Long id)
    {return service.buscaPorId(id);}


    @Operation(summary = "Salva Novo registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salvo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @PostMapping()
    public ResponseEntity<AtividadeRecord> salvar(@RequestParam String nome,@RequestParam String descrisao)
    {
        return service.salvar(nome, descrisao);
    }

    @Operation(summary = "Altera dados da atividade", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @PutMapping("/alterarAtividade")
    public ResponseEntity<AtividadeRecord> alterarAtividade(@RequestParam Long id,@RequestParam String nome, @RequestParam String descrisao)
    { return  service.alterarAtividade(id, nome,descrisao);}

    @Operation(summary = "Altera Status da atividade para inicio ou aguardando aprovação", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @PutMapping("/alterarStatus")
    public ResponseEntity<AtividadeRecord> alterarStatus(@RequestParam Long id, @RequestParam StatusLimitado status)
    {return service.alterarStatus(id, status);}


    @Operation(summary = "Finaliza atividade confirmando sucesso ou retornando para aguardando", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @PutMapping("/FinalizarAtividade")
    public ResponseEntity<AtividadeRecord> FinalizarAtividade(@RequestParam Long id, @RequestParam Boolean finalizado)
    { return service.FinalizarAtividade(id, finalizado);}

    @Operation(summary = "Deleta Registro da tabela por id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @DeleteMapping()
    public void  deletarAtividade(@RequestParam Long id)
    { service.delete(id);  }


}
