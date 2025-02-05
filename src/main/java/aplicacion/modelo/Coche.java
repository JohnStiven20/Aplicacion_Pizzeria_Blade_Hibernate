package aplicacion.modelo;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Coche {

    private int id;
    private String matricula;
    private String marca;
    private String modelo;
    private Date date;

}
