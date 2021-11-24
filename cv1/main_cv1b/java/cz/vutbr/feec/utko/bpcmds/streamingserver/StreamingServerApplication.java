package cz.vutbr.feec.utko.bpcmds.streamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam; //-- Přidáno - Objekt pro podporu parametrů v requestu --//
import org.springframework.web.bind.annotation.RestController; //-- Přidáno - Knihovna pro REST API--//

@SpringBootApplication
@RestController //-- Přidáno --//
public class StreamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingServerApplication.class, args);
	}

	// -- Přidáno  (START) -- //
	/*
	Metoda hello() vezme String parametr s názvem "name" a přidá ho ke slovu "Hello".
	Pokud je obsah parametru řetězec String s obsahem Alice, výsledný výstup metody bude: "Hello Alice!".

	Anotace @RestController říká springu, že tento kód je koncovým bodem, který by měl být zpřístupněn přes web.
	Anotace @GetMapping říká springu, aby použil metodu hello() k zodpovězení dotazů na adresu
	http://localhost:8080/hello
	Anotace @RequestParam říká springu, aby v žádosti očekával parametr, avšak pokud tam není, použije ve výchozím
	nastavení slovo "World".

	Převzato z oficiální dokumentace: https://spring.io/quickstart
	 */
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
	// -- Přidáno  (KONEC) -- //
}