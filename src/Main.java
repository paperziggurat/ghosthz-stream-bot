import org.jibble.pircbot.IrcException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, IrcException {
        Bot myBot = new Bot("ghosthzbot", "irc.rizon.net", "#ghosthz");
        myBot.connectBot();
    }
}
