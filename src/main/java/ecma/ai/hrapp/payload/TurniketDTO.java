package ecma.ai.hrapp.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class TurniketDTO {
    @NotNull
    private Integer companyId;
    @NotNull
    private UUID userId;
}
