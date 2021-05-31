package ecma.ai.hrapp.payload;

import ecma.ai.hrapp.entity.enums.TurniketType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class TurniketHistoryDto {
    @NotNull
    private UUID turniketNumber;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private TurniketType type;
}
