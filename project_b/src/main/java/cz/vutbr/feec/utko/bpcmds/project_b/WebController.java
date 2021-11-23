package cz.vutbr.feec.utko.bpcmds.project_b;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
public class WebController {

    @Autowired
    private ProjectResourceComponent handler;

    private final static String DASH_DIRECTORY = "C:\\Users\\petrk\\Desktop\\Videa\\MPEG-DASH\\";
    private final static String SUFFIX = "mpd";


    @RequestMapping(value = {"/dash/{file}"}, method = RequestMethod.GET)
    public void streaming(@PathVariable("file") String file, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        File STREAM_FILE = null;
        STREAM_FILE = new File(DASH_DIRECTORY + file);

        request.setAttribute(ProjectResourceComponent.ATTR_FILE, STREAM_FILE);
        handler.handleRequest(request, response);
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping(value = "/getvideo/{name}")
    public void byterange(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(ProjectResourceComponent.ATTR_FILE, new File(DASH_DIRECTORY + name));
        handler.handleRequest(request, response);
    }

}
