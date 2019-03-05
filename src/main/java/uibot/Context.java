package uibot;

import graph.custom.update.Listeners;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import training.Player;
import training.Training;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Context implements Serializable {
    transient private TelegramLongPollingBot bot;
    private List<Player> players = new LinkedList<>();
    private List<Training> trainings = new LinkedList<>();

    public Listeners getListeners() {
        return listeners;
    }

    private Listeners listeners = new Listeners(this);

    public Context() {
        listeners.start();
    }

    public TelegramLongPollingBot getBot() {
        return bot;
    }

    public void setBot(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Training> getTrainings() {
        return trainings;
    }
}
