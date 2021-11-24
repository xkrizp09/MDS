package cz.vutbr.feec.utko.bpcmds.streamingserver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller /* Přidána anotace Controller určující, že jde o třídu controlleru
 Jedná se o variantu příbuznou k anotaci @RestController */
/* @RestController je kombinací @Controller a @ResponseBody, což umožňuje nedefinování Response body*/
public class WebController {

    /*
    Spring hledá název šablony podle textu v návratové hodnotě metody.
     */
    @GetMapping("/bob")
    public String bob() {
        return "bob";
    }

    /*
    Oproti předchozí metodě je zde navíc vytvořen objekt model, který z šablony dělá dynamickou stránku, která
    svůj obsah dokáže měnit v průběhu času.
    Neplést s JavaScriptem! Obsah je měněn na straně serveru a není zde žádné obnovování na straně klienta.
    Je tedy nutné obnovit stránku ručně.
     */
    @GetMapping("/alice")
    public String alice(Model model) {
        model.addAttribute("name", "Alice");
        return "alice";
    }

}
