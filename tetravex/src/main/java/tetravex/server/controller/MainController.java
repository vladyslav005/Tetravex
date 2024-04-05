package tetravex.server.controller;


import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tetravex.consoleui.BoardDrawer;
import tetravex.core.Complexity;
import tetravex.core.Field;
import tetravex.core.Utils;
import tetravex.core.tile.Tile;
import tetravex.server.webui.WebUI;

import java.util.ArrayList;
import java.util.List;

@Controller
@Profile("dev")
@RequestMapping("/tetravex")
public class MainController {


    @Autowired
    private WebUI webUI;


    @GetMapping("/play")
    public String startGame(Model model) {
        webUI.newGame(Complexity.HARD, 3, 3);

        model.addAttribute("solved", webUI.getThymeleafAttributeSolved());
        model.addAttribute("shuffled", webUI.getThymeleafAttributeShuffled());

        return "index";
    }


}
