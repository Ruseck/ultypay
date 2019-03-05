package training;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Training implements Serializable {

    private String place;
    private LocalDateTime time;
    private String description;
    private BigDecimal cost;
    private BigDecimal costPerPerson;
    private BigDecimal minPlayers;
    private HashMap<Player, BigDecimal> payed = new HashMap<>();
    private LocalDateTime deadline;

    public Training(String place, LocalDateTime time, String description, BigDecimal minPlayers, BigDecimal cost, LocalDateTime deadline) {
        this.place = place;
        this.time = time;
        this.description = description;
        this.cost = cost;
        this.minPlayers = minPlayers;
        this.costPerPerson = cost.divide(minPlayers,0, RoundingMode.CEILING);
        this.deadline = deadline;
    }

    public String listOfPlayers() {
        return payed.entrySet().stream()
                .map(e -> e.getKey().getCash().get("cash.userName") + " " + e.getValue().toString())
                .collect(Collectors.joining("\n", "Список оплативших:", ""));
    }

    public String fullInfo() {
        return String.join(", ",
                place, time.toString(),
                "Стоимость тренировки",
                costPerPerson.toString(),
                description,
                "Оплатить необходимо до ",
                deadline.toString());
    }

    public String shortInfo() {
        return String.join(", ", place, time.toString());
    }

    public BigDecimal getCostPerPerson() {
        return costPerPerson;
    }

    public void setCostPerPerson(BigDecimal costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    public String getPlace() {
        return place;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public HashMap<Player, BigDecimal> getPayed() {
        return payed;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }
}
