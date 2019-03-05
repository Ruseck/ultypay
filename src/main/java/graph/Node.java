package graph;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Node<T> implements Serializable {

    private long id;

    public Node(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public List<Action<T>> getActions() {
        return actions;
    }

    private List<Action<T>> actions = new LinkedList<>();

    public void execute(T obj) {
        actions.stream()
                .filter(action -> action.getPredicate().test(obj))
                .findFirst()
                .ifPresent(action -> action.execute(obj));
    }

}
