import java.time.LocalDateTime;

public class TestTime {
    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.of(2019, 2, 17, 8, 0);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(time);
    }
}
