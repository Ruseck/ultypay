package uibot;

import graph.Node;
import graph.custom.update.predicate.update.UpdateNodesFromXmlBuilder;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import training.Player;
import training.Training;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.TreeSet;

public enum ContextUtil {
    INST;

    public long getUniqNodedId(Context context, Update update) {
        Player player = ContextUtil.INST.getPlayer(context, update);
        long newId = 1;
        for (Long id : new TreeSet<>(player.getNodes().keySet())) {
            if (newId++ != id) {
                break;
            }
        }
        return newId;
    }

    public Optional<String> getText(Update update) {
        return Optional.ofNullable(update)
                .filter(Update::hasMessage)
                .map(Update::getMessage)
                .filter(Message::hasText)
                .map(Message::getText);
    }

    public Player setNewPlayer(Update update, Context context, File xml) {
        HashMap<Long, Node<Update>> map = UpdateNodesFromXmlBuilder.INST.createNodes(xml, context);
        Node<Update> current = map.get(1L);
        Player player = new Player(update.getMessage().getFrom(), current, map, context);

        if (player.getUser().getId() == 395336109) {
            player.setAdmin(true);
        }

        return player;
    }

    public Player getPlayer(Context context, Update update) {
        String id = update.getMessage().getChatId().toString();
        return context
                .getPlayers().stream()
                .filter(player -> player.getUser().getId().toString().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void saveContext(Context context, String path) {
        try (FileOutputStream file = new FileOutputStream(path);
             ObjectOutputStream out = new ObjectOutputStream(file)) {
            out.writeObject(context);
            System.out.println("Object has been serialized");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Context loadContext(String path, TelegramLongPollingBot bot) {
        Context context;
        try {
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);
            context = (Context) in.readObject();
            file.close();
            in.close();
            System.out.println("Object has been deserialized");
        } catch (Throwable e) {
            e.printStackTrace();
            context = new Context();
        }
        context.setBot(bot);
        return context;
    }

    public void sendMessage(long id, String text, TelegramLongPollingBot bot) {
        SendMessage sendMessage = new SendMessage().setText(text).setChatId(id);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
