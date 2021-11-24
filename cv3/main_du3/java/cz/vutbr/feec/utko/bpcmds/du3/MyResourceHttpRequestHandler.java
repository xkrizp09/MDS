package cz.vutbr.feec.utko.bpcmds.du3;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

// vytvoření vlastní třídy (komponenty) přepisující metodu třídy ResourceHTTPRequestHandler
@Component
public class MyResourceHttpRequestHandler extends ResourceHttpRequestHandler {
    // vytvoření cesty k souboru, která je předána skrze atribut objektu request s názvem ATTR_FILE
    final static String ATTR_FILE = MyResourceHttpRequestHandler.class.getName() + ".file";

    // Přepis metody getResource z třídy ResourceHttpRequestHandler
    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {

        // vytvoření finální verze objektu typu file, který obsahuje objekt s videem
        final File file = (File) request.getAttribute(ATTR_FILE);
        // vrácení požadovaného souboru v objektu file
        // objekt file je v konstruktoru třídy FileSystemResource, který je objektem z frameworku Spring
        // třída resp. objekt FileSystemResource se stará o předání souboru serveru Spring
        return new FileSystemResource(file);
    }

}