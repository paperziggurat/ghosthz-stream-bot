import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import net.moraleboost.streamscraper.ScrapeException;
import net.moraleboost.streamscraper.Stream;
import net.moraleboost.streamscraper.Scraper;
import net.moraleboost.streamscraper.scraper.IceCastScraper;

public class StreamParser {

    private String streamName;

    public StreamParser(String stream){
        streamName = stream;
    }

    public String getSongTitle() throws URISyntaxException, ScrapeException {
        Scraper scraper = new IceCastScraper();
        List<Stream> streams = scraper.scrape(new URI(streamName));
        String latestSong = "";
        for (Stream stream: streams){
            latestSong = stream.getCurrentSong();
        }
        return latestSong;
    }

    public int getListenerCount() throws URISyntaxException, ScrapeException {
        Scraper scraper = new IceCastScraper();
        List<Stream> streams = scraper.scrape(new URI(streamName));
        int listenerCount = 0;
        for (Stream stream: streams){
            listenerCount = listenerCount + (stream.getCurrentListenerCount());
        }

        return listenerCount;
    }


}
