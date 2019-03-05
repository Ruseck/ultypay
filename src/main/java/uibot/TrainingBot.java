package uibot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import training.Player;

import java.io.File;
import java.time.LocalDateTime;

public class TrainingBot extends TelegramLongPollingBot {
    private String token;
    private static final String INFO_XML = "info.xml";
    private static final String SER_OBJ = "ser.obj";
    private Context context = ContextUtil.INST.loadContext(SER_OBJ, this);

    public TrainingBot(String arg) {
        token = arg;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Player player = ContextUtil.INST.getPlayer(context, update);
        if (player == null) {
            player = ContextUtil.INST.setNewPlayer(update, context, new File(INFO_XML));
            context.getPlayers().add(player);
        }
        player.getCurrent().execute(update);

        System.out.println("===================================");
        System.out.println(LocalDateTime.now());
        System.out.println(ContextUtil.INST.getPlayer(context, update).getUser());
        System.out.println(ContextUtil.INST.getText(update).orElse(""));
        System.out.println("===================================");
        ContextUtil.INST.saveContext(context, SER_OBJ);
    }

    @Override
    public String getBotUsername() {
        return "Story2711bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
