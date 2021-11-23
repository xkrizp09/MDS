package cz.vutbr.feec.utko.bpcmds.projekt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Controller
public class WebController {

    @Autowired
    private ProjectResourceComponent handler;

    private final static String DASH_DIRECTORY = "C:\\Users\\NoOne\\Desktop\\Priprava\\projekt\\video\\DASH-BIG BUCK BUNNY\\";

    ArrayList<Video> VideoList = new ArrayList<>();

    @RequestMapping(value = "/dash/{file}", method = RequestMethod.GET)
    public void streaming(@PathVariable("file") String file, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File STREAM_FILE;
        //String handle = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        STREAM_FILE = new File(DASH_DIRECTORY + file);
        request.setAttribute(ProjectResourceComponent.ATTR_FILE,STREAM_FILE);
        handler.handleRequest(request,response);
    }

    @GetMapping("/index")
    public String home(){return "index";}

    @GetMapping("/addvideo")
    public String addvideo(Model model) {
        model.addAttribute("video", new Video());
        return "addvideo";
    }

    @RequestMapping(value = "/videolibrary", method = {RequestMethod.POST, RequestMethod.GET})
    public String videolibrary(Model model,@ModelAttribute("video") Video video,@RequestParam(required = false, name = "name") String name,
                               @RequestParam(required = false, name = "URL") String URL) {
        VideoList.add(new Video (URL,name));
        model.addAttribute("name", name);
        model.addAttribute("url", URL);
        model.addAttribute("videoList", VideoList);
        System.out.println(VideoList.toString());
        return "videolibrary";
    }



    @RequestMapping(value = {"/player/{url}"}, method = RequestMethod.POST)
    public ModelAndView player(Model model, @PathVariable("url") URI url) throws IOException {
        System.out.println(url);
        String viewName = "player";
        model.addAttribute("url", url);
       // return new ModelAndView(viewName, model);
        return null;
    }

        /*
    Test streams:
http://rdmedia.bbc.co.uk/dash/ondemand/testcard/1/client_manifest-events.mpd
http://rdmedia.bbc.co.uk/dash/ondemand/elephants_dream/1/client_manifest-all.mpd
https://dash.akamaized.net/akamai/bbb_30fps/bbb_30fps.mpd
     */
}
