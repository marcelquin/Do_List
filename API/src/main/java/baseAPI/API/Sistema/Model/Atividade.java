package baseAPI.API.Sistema.Model;

import baseAPI.API.Sistema.Enum.Status;
import baseAPI.API.Sistema.Records.AtividadeRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nome;

    private String descrisao;

    @ManyToOne
    @JoinColumn(name = "backup_dev_Id")
    private Dev responsavel;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "Data_Criacao")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataCriacao;

    @Column(name = "Data_Iniciio")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAlteracao;

    private Boolean finalizada;

    public Atividade(AtividadeRecord record) {
        this.nome = record.nome();
        this.descrisao = record.descrisao();
    }
}
