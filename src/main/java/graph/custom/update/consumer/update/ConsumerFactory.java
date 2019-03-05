package graph.custom.update.consumer.update;

import graph.custom.update.consumer.update.consumers.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import uibot.Context;

import java.util.HashMap;

public enum ConsumerFactory {
    INST;

    public SConsumer<Update> create(HashMap<String, String> data, Context context) {
        String type = data.get("type");
        switch (type) {
            case "save-text":
                return new SaveText(context, data.get("to"));
            case "change-node":
                return new ChangeNode(context, data.get("id"));
            case "change-balance":
                return new ChangeBalance(context);
            case "send-message":
                return new SendMessage(context, data.get("text"));
            case "send-message-balance":
                return new SendMessageBalance(context);
            case "create-and-change-node":
                return new CreateAndChangeTrainingsNode(context);
            case "create-training":
                return new CreateTraining(context);
            case "cancel-training":
                return new CancelTraining(context);
            default:
                throw new IllegalStateException();
        }
    }
}
