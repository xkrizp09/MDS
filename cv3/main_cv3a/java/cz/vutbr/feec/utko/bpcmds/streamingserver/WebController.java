package cz.vutbr.feec.utko.bpcmds.streamingserver;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    // Anotace Autowired vytvoří závislost na objektu handler, který je využit v metodě byterange
    @Autowired
    private MyResourceHttpRequestHandler handler;
    // Deklarace objektu typu File s názvem MP4_FILE s cestou k souboru videa
    private final static File MP4_FILE = new File("D:\\MDS\\videa\\bbb_1080p.mp4");

    // metoda s anotací getMapping, která při dotazu na index vrací šablonu index.html v resources/templates/
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    // Vytvoření metody s anotací getMapping, která při dotazu na byterange vrací soubor videa.
    // Metoda za pomocí objektu HttpServetRequest podporuje byte-range dotazy.
    // Byte range dotazování umožňuje stahovat soubor "po kouscích" - nevrací tedy celý soubor, ale pouze požadovaný počet bajtů
    @GetMapping("/byterange")
    public void byterange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Vytvoření požadavku na soubor z proměnné String MP4_FILE
        request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, MP4_FILE);
        handler.handleRequest(request, response);
    }

    // Vytvoření metody s anotací getMapping, která při dotazu na file vrací celý soubor videa.
    // Anotace GetMapping obsahuje kromě cesty také typ souboru, který poskytuje.
    // Metoda, která nepodporuje byte-range dotazování.
    // Vrací celý soubor.
    @GetMapping(path = "/file", produces = "video/mp4")
    public FileSystemResource plain() {
        return new FileSystemResource(MP4_FILE);
    }
}
