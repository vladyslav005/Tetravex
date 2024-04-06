package tetravex.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class ClientRequestDto {
    private String board;
    private int y;
    private int x;
}
