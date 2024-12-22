package ace.charitan.email.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateEmailTestDto {
    private String email;
    private String subject;
    private String text;
}
