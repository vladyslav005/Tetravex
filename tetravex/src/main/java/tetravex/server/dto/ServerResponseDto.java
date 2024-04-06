package tetravex.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tetravex.core.GameState;


@AllArgsConstructor
@Data
public class ServerResponseDto {
    private GameState gameState;
}
