package infralearning.trainer.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity(name = "placas")
public class Domain {

    @Id
    private Integer id;

    @Column(name = "nome")
    private String layer1;

    @Column(name = "condicao")
    private String layer2;

    @Column(name = "is_mapped")
    private boolean isMapped;

    @Column(name = "is_valid")
    private boolean isValid;

}
