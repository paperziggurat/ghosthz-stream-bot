import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import net.moraleboost.streamscraper.ScrapeException;
import net.moraleboost.streamscraper.Stream;
import net.moraleboost.streamscraper.Scraper;
import net.moraleboost.streamscraper.scraper.IceCastScraper;

public class StreamParser {

    private String streamName;
    private List<Stream> streams;

    public StreamParser(String stream){
        streamName = stream;
    }

    public void updateStream(){
        Scraper scraper = new IceCastScraper();
        try {
            streams = scraper.scrape(new URI(streamName));
        } catch (ScrapeException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public String getSongTitle(){
        updateStream();
        String latestSong = "";
        for (Stream stream: streams){
            latestSong = stream.getCurrentSong();
        }
        return latestSong;
    }

    public int getListenerCount(){
        updateStream();
        int listenerCount = 0;
        for (Stream stream: streams){
            listenerCount = listenerCount + stream.getCurrentListenerCount();
        }

        return listenerCount;
    }

    public int getPeakListenerCount(){
        updateStream();
        int listenerCount = 0;
        for (Stream stream: streams){
            listenerCount = listenerCount + stream.getCurrentListenerCount();
        }

        return listenerCount;
    }

    public int getMaxListenerCount(){
        updateStream();
        int listenerCount = 0;
        for (Stream stream: streams){
            listenerCount = listenerCount + stream.getCurrentListenerCount();
        }

        return listenerCount;
    }

}
