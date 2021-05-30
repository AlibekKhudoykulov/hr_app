package ecma.ai.hrapp.payload;

import ecma.ai.hrapp.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class TaskGetDto {
    @NotNull
    private String name;

    private String description;

    private Timestamp deadline;

    private String taskGiverName;

    @Email
    @NotNull
    private String taskGiverEmail;

    private String taskTakerName;
    @Email
    @NotNull
    private String taskTakerEmail; //vazifani oluvchini emaili

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;
}
