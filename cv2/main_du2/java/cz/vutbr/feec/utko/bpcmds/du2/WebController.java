package cz.vutbr.feec.utko.bpcmds.du2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/alice")
    public String alice(Model model){
        model.addAttribute("name", "Alice");
        return "template";
    }

    @GetMapping("/bob")
    public String bob(Model model){
        model.addAttribute("name", "Bob");
        return "template";
    }
}
