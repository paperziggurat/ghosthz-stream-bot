import net.moraleboost.streamscraper.ScrapeException;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import java.util.ArrayList;
import java.util.TimerTask;


import java.io.IOException;
import java.net.URISyntaxException;

public class Bot extends PircBot {

    private String nickname, server, channel, streamAddress, sourceAddress;
    private String currentlyPlaying;

    public Bot(String nick, String serv, String chan, String stream, String src){
        nickname = nick;
        server = serv;
        channel = chan;
        streamAddress = stream;
        sourceAddress = src;
        currentlyPlaying = "";
    }

    public void connectBot() throws IrcException, IOException {
        this.setName(nickname);
        this.connect(server);
        this.joinChannel(channel);
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message){
        if (message.contains("!help")){
            displayCommands(sender, message);
        }

        if (message.equalsIgnoreCase("!song")){
            StreamParser stream = new StreamParser(streamAddress);
            msg("Currently Playing: " + stream.getSongTitle());
        }

        if (message.equalsIgnoreCase("!listeners")){
            StreamParser stream = new StreamParser(streamAddress);
            msg(stream.getListenerCount() + "");
        }

        if (message.equalsIgnoreCase("!peaklisteners")){
            StreamParser stream = new StreamParser(streamAddress);
            msg(stream.getPeakListenerCount() + "");
        }

        if (message.equalsIgnoreCase("!maxlisteners")){
            StreamParser stream = new StreamParser(streamAddress);
            msg(stream.getMaxListenerCount() + "");
        }

        if (message.equalsIgnoreCase("!source")){
            msg(sourceAddress);
        }

        if (message.contains("!kick") && isAuthentic(sender)){
            String[] command = message.split(" ");
            String userToKick = command[1];
            if (isUser(userToKick)){
                kick(channel, userToKick);
                msg("Kicking " + userToKick + " from channel.");
            }
            else {
                msg(userToKick + " is not in the channel.");
            }
        }

        if (message.contains("!deop") && isAuthentic(sender)){
            String[] command = message.split(" ");
            String userToDeOp = command[1];
            if (isUser(userToDeOp)){
                msg("Removing operator privileges from " + userToDeOp + ".");
                deOp(channel, userToDeOp);
            }
            else {
                msg(userToDeOp + " is not in the channel.");
            }

        }

        if (message.contains("!op") && isAuthentic(sender)){
            String[] command = message.split(" ");
            String userToOp = command[1];
            if (isUser(userToOp)){
                msg("Granting operator privileges to " + userToOp + ".");
                op(channel, userToOp);
            }
            else {
                msg(userToOp + " is not in the channel.");
            }

        }

    }

    public void msg(String msg){
        sendMessage(channel, msg);
    }

    public void pmsg(String sender, String msg){
        sendMessage(sender, msg);
    }

    public void displayCommands(String sender, String helpRequest){
        String[] message = helpRequest.split(" ");
        if (message.length < 2){
            pmsg(sender, "For help with op commands, enter !help op");
            pmsg(sender, "For help with radio commands, enter !help radio");
            pmsg(sender, "For help with weather commands, enter !help weather");
        }
        if (message[1].equals("op")){
            pmsg(sender, "!kick [user]");
            pmsg(sender, "!op [user]");
            pmsg(sender, "!deop[user]");
        }
        if (message[1].equals("radio")){}
        if (message[1].equals("weather")){}
    }

    public void updateCurrentSong() throws URISyntaxException, ScrapeException {
        StreamParser stream = new StreamParser(streamAddress);
        if (!currentlyPlaying.equals(stream.getSongTitle())){
            currentlyPlaying = stream.getSongTitle();
            //sendMessage(channel, "Currently playing: " + currentlyPlaying);
            setTopic(channel, "Currently playing: " + currentlyPlaying);
        }

    }

    private boolean isAuthentic(String nick){
        User[] users = getUsers(channel);
        for (User user: users){
            if (user.getNick().equals(nick)){
                if (user.isOp()){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isUser(String nick){
        User[] users = getUsers(channel);
        for (User user: users){
            if (user.getNick().equals(nick)){
                return true;
            }
        }
        return false;
    }

}
