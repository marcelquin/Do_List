package baseAPI.API.Sistema.Model;

import baseAPI.API.Sistema.Enum.Equipe;
import baseAPI.API.Sistema.Enum.Nivel;
import baseAPI.API.Sistema.Records.DevRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Dev {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Equipe equipe;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    public Dev(DevRecord record) {
        this.nome = record.nome();
    }
}
