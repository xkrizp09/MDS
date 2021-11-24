package cz.vutbr.feec.utko.bpcmds.du1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@SpringBootApplication
// Definování podpory pro REST API
@RestController
public class Du1Application {

	public static void main(String[] args) {
		SpringApplication.run(Du1Application.class, args);
	}

	// Namapování adresy index k metody index
	@GetMapping("/index")
	public String index(@RequestParam(required = false) String jmeno) {
		// Vytvoření objektu typu ArrayList s názvem jmena
		ArrayList<String> jmena = new ArrayList<String>();
		// Přidání jmen do ArrayListu
		jmena.add("Bob");
		jmena.add("Alice");
		jmena.add("Eva");
		jmena.add("Alena");
		jmena.add("Marek");

		// Pokud je atribut s názvem jméno definován přidám ho do ArrayListu
		if(jmeno != null){
			jmena.add(jmeno);
		}

		// Do Stringu seznam_jmen přidám obsah ArrayListu s mezerami mezi jednotlivými položkami
		String seznam_jmen = String.join(" ", jmena);

		// Vrátím String seznam_jmen a tím ho vypíši na webové stránce
		return seznam_jmen;
	}
}
