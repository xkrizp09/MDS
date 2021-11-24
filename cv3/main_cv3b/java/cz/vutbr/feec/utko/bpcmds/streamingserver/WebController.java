package cz.vutbr.feec.utko.bpcmds.streamingserver;

import java.io.Console;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.thymeleaf.util.StringUtils;

@Controller
public class WebController {

    // Anotace Autowired vytvoří závislost na objektu handler, který je využit v metodě byterange
    @Autowired
    private MyResourceHttpRequestHandler handler;

    // Deklarace objektu typu File s názvem MP4_FILE s cestou k souboru videa
    private final static File MP4_FILE = new File("D:\\MDS\\videa\\bbb_1080p.mp4");

    // Deklarace proměnné String s cestou k souborům streamu
    private final static String HLS_PATH = "D:\\MDS\\videa\\streamy\\HLS\\";
    private final static String DASH_PATH = "D:\\MDS\\videa\\streamy\\MPEG-DASH\\";

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

        request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, MP4_FILE);
        handler.handleRequest(request, response);
    }

    // Vytvoření metody s anotací RequestMapping, která naslouchá na třech adresách a odpovídá pouze na metodu GET
    // Metoda naslouchá na třech dvou adresách /dash a /hls
    // Podle předaného názvu souboru vrací daný soubor
    // Při obdržení dotazu je vytovřen String, kde je předán vzor, který odpovídá adresám v anotaci
    // Podle vzoru je dále ve switchi přiřazena adresa vedoucí k souborům streamu
    // Stream HLS potřebuje dvě proměnné, protože jednotlivé streamy jsou uloženy ve složkách odpovídajícím profilu
    @RequestMapping(value = {"/dash/{file}", "/hls/{file}", "/hls/{quality}/{file}"}, method = RequestMethod.GET)
    public void adaptive_streaming(
            @PathVariable("file") String file,
            @PathVariable(value = "quality", required = false) String quality,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        File STREAM_FILE = null;
        String handle = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        switch (handle){
            case "/dash/{file}":
                STREAM_FILE = new File(DASH_PATH + file);
                break;
            case "/hls/{file}":
                STREAM_FILE = new File(HLS_PATH + file);
                break;
            case "/hls/{quality}/{file}":
                if(!StringUtils.isEmpty(quality)){
                    STREAM_FILE = new File(HLS_PATH + quality + "\\" + file);
                }
                break;
            default:

                break;
        }
        request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, STREAM_FILE);
        handler.handleRequest(request, response);
    }


}
