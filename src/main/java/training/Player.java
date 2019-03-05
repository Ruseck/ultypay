package training;

import graph.Node;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import uibot.Context;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Player implements Serializable {

    private User user;
    private String name;
    private BigDecimal balance = BigDecimal.ZERO;
    private Node<Update> current;
    private HashMap<Long, Node<Update>> nodes;
    private HashMap<String, Serializable> cash = new HashMap<>();
    private List<Update> history = new LinkedList<>();

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    private boolean admin = false;
    private Context context;

    public Player(User user, Node<Update> current, HashMap<Long, Node<Update>> nodes, Context context) {
        this.user = user;
        this.current = current;
        this.nodes = nodes;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public HashMap<String, Serializable> getCash() {
        return cash;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void upBalance(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }

    public Node<Update> getCurrent() {
        return current;
    }

    public void setCurrent(Node<Update> current) {
        this.current = current;
    }

    public HashMap<Long, Node<Update>> getNodes() {
        return nodes;
    }

    public List<Update> getHistory() {
        return history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(user.getId(), player.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
