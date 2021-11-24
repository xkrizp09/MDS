package cz.vutbr.feec.utko.bpcmds.streamingserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
public class WebController {

    /*
	Metoda hello obsahuje anotaci pro mapování metod GET a POST.
	Metoda POST předává svůj obsah do Stringu name.
	String name je poté vypsán pomocí return na webové stránce.

	Naslouchá na adrese:
	http://localhost:8080/hello a čeká na metodu post
	 */
    @RequestMapping(value="hello", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String hello(@RequestParam String name){
        return "Ahoj, " + name + "!";
    }

    /*
    Vytvoření metody pro odpověď na dotaz adresy /form (localhost:8080/form).
    Pouze vrátí HTML kód, který obsahuje vstupní textbox pro napsání jména a tlačítko pro odeslání.
    Při kliknutí na tlačítko je z parametru action zavolána adresa /hello (localhost:8080/hello)
    a pomocí REST API a metody POST je předán obsah textboxu.
     */
    @GetMapping("form")
    @ResponseBody
    public String helloForm() {
        String html = "<html>" +
                "<body>" +
                "<form method='post' action='hello'>" +
                "<input type='text' name='name'/>" +
                "<input type='submit' value='Pozdrav!'/>" +
                "</form>" +
                "</body>" +
                "</html>";
        return html;
    }
}
