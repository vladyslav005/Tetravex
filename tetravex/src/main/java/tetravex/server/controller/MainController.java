package tetravex.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tetravex.core.Complexity;
import tetravex.server.dto.ClientRequestDto;
import tetravex.server.dto.ServerResponseDto;
import tetravex.server.webui.WebUI;

import java.security.Principal;
import java.util.List;


@Controller
@Profile("dev")
@RequestMapping("/tetravex")
public class MainController {

    @Autowired
    private WebUI webUI;


    @GetMapping("/play")
    public String startNewGame(Model model) {
        webUI.newGame(Complexity.HARD, 3, 3);

        model.addAttribute("solved", webUI.getThymeleafAttributeSolved());
        model.addAttribute("shuffled", webUI.getThymeleafAttributeShuffled());

        return "main";
    }


    @GetMapping("/play/parameters")
    public String newParametrizedGame(@RequestParam int width, @RequestParam int height,
                                      @RequestParam String complexity, Model model) {

        webUI.newGame(Complexity.valueOf(complexity.toUpperCase()), width, height);

        model.addAttribute("solved", webUI.getThymeleafAttributeSolved());
        model.addAttribute("shuffled", webUI.getThymeleafAttributeShuffled());

        return "main";
    }

    @ResponseBody
    @PostMapping("/handler")
    public ServerResponseDto handle(@RequestBody List<ClientRequestDto> clientRequestDto) {
        webUI.swapTiles(clientRequestDto.get(0), clientRequestDto.get(1));
        return new ServerResponseDto(webUI.getGameState());
    }

}
