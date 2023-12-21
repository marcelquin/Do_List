package baseAPI.API.Sistema.Model;

import baseAPI.API.Sistema.Enum.AcaoBackup;
import baseAPI.API.Sistema.Enum.Equipe;
import baseAPI.API.Sistema.Enum.Nivel;
import baseAPI.API.Sistema.Enum.Status;
import baseAPI.API.Sistema.Records.AtividadeRecord;
import baseAPI.API.Sistema.Records.DevRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class BackupDev {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AcaoBackup acaoBackup;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Equipe equipe;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;

    public BackupDev(DevRecord record) {
        this.nome = record.nome();
    }
}
