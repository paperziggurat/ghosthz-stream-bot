import net.moraleboost.streamscraper.ScrapeException;
import org.jibble.pircbot.IrcException;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws IOException, IrcException, URISyntaxException, ScrapeException, InterruptedException {
        Bot myBot = new Bot("ghosthzbot", "irc.rizon.net", "#ghosthz", "http://radio.ghosthz.com:8000/", "https://github.com/paperziggurat/ghosthz-stream-bot");
        myBot.connectBot();
        while (myBot.isConnected()){
            myBot.updateCurrentSong();
            Thread.sleep(5000);
        }
    }

}
