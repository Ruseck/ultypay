package graph.custom.update.consumer.update.consumers;

import graph.Node;
import graph.NodeBuilder;
import graph.custom.update.consumer.update.SConsumer;
import graph.custom.update.predicate.update.SPredicate;
import org.telegram.telegrambots.meta.api.objects.Update;
import training.Training;
import uibot.Context;
import uibot.ContextUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class CreateTraining implements SConsumer<Update> {

    Context context;

    public CreateTraining(Context context) {
        this.context = context;
    }

    @Override
    public void accept(Update update) {
        HashMap<String, Serializable> cash = ContextUtil.INST.getPlayer(context, update).getCash();

        String place = cash.get("tb.place").toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
        String strTime = cash.get("tb.time").toString();
        LocalDateTime time = LocalDateTime.parse(strTime, formatter);
        String strLine = cash.get("tb.deadline").toString();
        LocalDateTime deadline = LocalDateTime.parse(strLine, formatter);
        String description = cash.get("tb.description").toString();
        String min = cash.get("tb.minPlayers").toString();
        BigDecimal minPlayers = new BigDecimal(min);
        String costUAH = cash.get("tb.cost").toString();
        BigDecimal cost = new BigDecimal(costUAH);
        Training training = new Training(place, time, description, minPlayers, cost, deadline);
        context.getTrainings().add(training);

        NodeBuilder<Context> nodeBuilder = new NodeBuilder<>();
        nodeBuilder
                .createNodes(0)
                .addAction(
                        cxt -> deadline.isBefore(LocalDateTime.now())
                                && training.getPayed().values().stream().mapToLong(BigDecimal::longValue).sum() < training.getCost().longValue(),
                        ctx -> {
                            String messageText = "Тренировки в " + training.shortInfo() + " не будет, не насобирали(";
                            training.getPayed().keySet().forEach(k -> ContextUtil.INST.sendMessage(k.getUser().getId(), messageText, ctx.getBot()));
                        }
                );
        LocalDateTime curTime = LocalDateTime.now();
        for (long period = 40;
             deadline.minusMinutes(period).isAfter(curTime);
             period *= 3) {

        }
        Node<Context> node = nodeBuilder.build();
    }

}
