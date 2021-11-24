package cz.vutbr.feec.utko.bpcmds.streamingserver;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jcodec.api.JCodecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

@Controller
public class WebController {
    @Autowired
    private MyResourceHttpRequestHandler handler;
    
    private final static File MP4_FILE = new File("D:\\MDS\\video\\bbb_1080p.mp4");
    private final static String HLS_PATH = "D:\\MDS\\video\\streamy\\HLS\\";
    private final static String DASH_PATH = "D:\\MDS\\video\\streamy\\MPEG-DASH\\";

    private final static String MP4_DIRECTORY = "D:\\MDS\\video\\";
    private final static String IMAGES_DIRECTORY = "D:\\MDS\\video\\images\\";
    private final static String SUFFIX = "mp4";

    // supports byte-range requests
    @GetMapping("/index")
    public String home() {
        return "video";
    }

    // supports byte-range requests
    @GetMapping("/byterange")
    public void byterange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, MP4_FILE);
        handler.handleRequest(request, response);
    }

    @RequestMapping(value = {"/dash/{file}", "/hls/{file}", "/hls/{quality}/{file}"}, method = RequestMethod.GET)
    public void adaptive_streaming(@PathVariable("file") String file, @PathVariable(value = "quality", required = false) String quality, HttpServletRequest request, HttpServletResponse response)
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

    @GetMapping("/gallery")
    public ModelAndView generateGallery() throws IOException, JCodecException {
        Map<String, Object> model = new HashMap<String, Object>();
        String viewName = "gallery";

        MovieLibrary library = new MovieLibrary();
        Collection<File> files = library.getFiles(MP4_DIRECTORY, SUFFIX);
        List<Movie> movies = library.getImages(files, IMAGES_DIRECTORY);

        model.put("moviesItems", movies);
        model.put("numberOfMovies", movies.size());

        return new ModelAndView(viewName, model);
    }

    @GetMapping("/video/{name}")
    public ModelAndView showVideo(@PathVariable("name") String name) throws IOException {
        Map<String, Object> model = new HashMap<String, Object>();
        String viewName = "video";

        model.put("movieName", name);
        return new ModelAndView(viewName, model);
    }

    // supports byte-range requests
    @GetMapping(value = "/getvideo/{name}")
    public void byterange(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, new File(MP4_DIRECTORY + name));
        handler.handleRequest(request, response);
    }
}
