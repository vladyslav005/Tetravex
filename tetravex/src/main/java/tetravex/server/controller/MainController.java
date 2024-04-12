package tetravex.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tetravex.core.Complexity;
import tetravex.server.webui.WebUI;


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
    public String newParametrizedGame(@RequestParam int width, @RequestParam int height, Model model) {
        webUI.newGame(Complexity.HARD, width, height);

        model.addAttribute("solved", webUI.getThymeleafAttributeSolved());
        model.addAttribute("shuffled", webUI.getThymeleafAttributeShuffled());

        return "main";
    }


}
