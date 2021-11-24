package cz.vutbr.feec.utko.bpcmds.streamingserver;

import org.apache.commons.io.FilenameUtils;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieLibrary {

    // Metoda
    public List<Movie> getImages(Collection<File> files, String pathToStore) throws IOException, JCodecException {
        List<Movie> movies = new ArrayList<Movie>();
        for (File file:files) {
                int frame_time = 150;
                Picture frame = FrameGrab.getFrameFromFile(file, frame_time);
                BufferedImage bufferedImage = AWTUtil.toBufferedImage(frame);
                String image_name = file.getName() + "frame_" + frame_time + ".png";
                String image_path = pathToStore + "\\" + image_name;
                ImageIO.write(bufferedImage, "png", new File(image_path));
                movies.add(new Movie(file, image_name, file.getName()));
        }
        return movies;
    }

    // Metoda pro filtraci nalezených souborů podle jejich přípony (typu)
    public Collection<File> getFiles(String path, String suffix){
        Collection<File> files = scanFiles(path);

        files.removeIf(file -> !FilenameUtils.getExtension(file.getName()).contains(suffix));

        return files;
    }

    // Metoda pro přetvoření objektů file z objektu path
    private Collection<File> scanFiles(String path){
        Collection<File> files = new ArrayList<File>();
        Path directory = Path.of(path);
        try {
            discoverFiles(directory, files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    // Metoda pro automatické zjisťování souborů ze zadané cesty
    // Využívá walkFileTree, která naplní předanou kolekci all
    static void discoverFiles(Path directory, final Collection<File> all) throws IOException{
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                all.add(new File(String.valueOf(file)));
                return super.visitFile(file, attrs);
            }
        });
    }
}
