import net.moraleboost.streamscraper.ScrapeException;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;


import java.io.IOException;
import java.net.URISyntaxException;

public class Bot extends PircBot {

    private String nickname, server, channel;

    public Bot(String nick, String serv, String chan){
        nickname = nick;
        server = serv;
        channel = chan;
    }

    public void connectBot() throws IrcException, IOException {
        this.setName(nickname);
        this.connect(server);
        this.joinChannel(channel);
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message){
        if (message.equalsIgnoreCase("!song")){
            StreamParser stream = new StreamParser("http://radio.ghosthz.com:8000/");
            try {
                sendMessage(channel, stream.getSongTitle());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ScrapeException e) {
                e.printStackTrace();
            }
        }

        if (message.equalsIgnoreCase("!listeners")){
            StreamParser stream = new StreamParser("http://radio.ghosthz.com:8000/");

            try {
                sendMessage(channel, stream.getListenerCount() + "");
            } catch (URISyntaxException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ScrapeException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }

}
