package main.controllers;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import javafx.util.Pair;
import main.domain.Film;
import main.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/")
public class IndexController
{

  @Autowired
  private FilmService filmService;

  private ConcurrentHashMap<String, Pair<Film, File>> videos = new ConcurrentHashMap<>();
  private static final String CONTENT_FOLDER = "content";

  @PostConstruct
  public void init()
  {
    videos.clear();
    List<Film> films = filmService.listAll();

    films.forEach(film -> {
//      File complFile = proccessTorrent(film);
      videos.put(film.getTitle(), new Pair<>(film, new File("")));
    });
    String property = System.getProperty("user.dir");
    Path contentFolder = new File(property).toPath().resolve(CONTENT_FOLDER);
    File dir = contentFolder.toFile();
    if (!dir.exists())
    {
      //noinspection ResultOfMethodCallIgnored
      dir.mkdir();
    }

//		videos.putAll(Arrays.stream(Objects.requireNonNull(dir.listFiles()))
//				.collect(Collectors.toMap(File::getName, (f) -> f)));
  }

  private File proccessTorrent(Film film)
  {
    AtomicReference<File> result = new AtomicReference<>();
    File torrentFile = new File(CONTENT_FOLDER + "/file.torrent");
    File downloadDir = new File(CONTENT_FOLDER);
    try
    {
      final Client client = new Client(InetAddress.getLocalHost(), SharedTorrent.fromFile(torrentFile, downloadDir));
      result.set(new File(client.getTorrent().getFilenames().iterator().next()));

      Runnable runnable = client::run;
      Thread t = new Thread(runnable);
      t.start();
    }
    catch (IOException | NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }

    return result.get();
  }

  @RequestMapping(method = RequestMethod.GET)
  public String sendData(ModelMap model)
  {
    model.addAttribute("filmList", videos.keySet().toArray(new String[0]));
    return "index";
  }

  @RequestMapping(value = "/{film}", method = RequestMethod.GET)
  public String sendDataByFilm(@PathVariable String film, ModelMap model)
  {
    Pair<Film, File> file1 = videos.get(film);
    String file = "http://localhost:8080/" + CONTENT_FOLDER + "/" + file1.getValue().getName();
    model.addAttribute("currentFilm", file);
    model.addAttribute("currentEnSubtitles", file1.getKey().getEnSubtitles());
    model.addAttribute("currentRuSubtitles", file1.getKey().getRuSubtitles());

    return "index";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/" + CONTENT_FOLDER + "/{content:.+}")
  public StreamingResponseBody sendContent(@PathVariable String content)
      throws FileNotFoundException
  {
    File videoFile = videos.get(content).getValue();
    final InputStream videoFileStream = new FileInputStream(videoFile);
    return (os) -> readAndWrite(videoFileStream, os);
  }

  private void readAndWrite(final InputStream is, OutputStream os)
      throws IOException
  {
    byte[] data = new byte[2048];
    int read = 0;
    while ((read = is.read(data)) > 0)
    {
      os.write(data, 0, read);
    }
    os.flush();
  }
}
