package tetravex.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tetravex.core.Complexity;
import tetravex.data.entity.Comment;
import tetravex.server.webui.WebUI;

import java.util.Date;

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

        return "index";
    }




}
