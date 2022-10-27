package ewmmainservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HitDto {
    private long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
