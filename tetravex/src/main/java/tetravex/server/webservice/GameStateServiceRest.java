package tetravex.server.webservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tetravex.server.dto.ClientRequestDto;
import tetravex.server.dto.ServerResponseDto;
import tetravex.server.webui.WebUI;

import java.util.List;

@RestController
@RequestMapping("/tetravex")
public class GameStateServiceRest {

    @Autowired
    private WebUI webUI;

    @PostMapping("/handler")
    public ServerResponseDto handle(@RequestBody List<ClientRequestDto> clientRequestDto) {

        System.out.print("Got request : ");
        System.out.println(clientRequestDto);

        webUI.swapTiles(clientRequestDto.get(0), clientRequestDto.get(1));

        return new ServerResponseDto(webUI.getGameState());
    }
}
