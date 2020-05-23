package mock;

import com.gp.beershop.dto.User;

import java.util.HashMap;
import java.util.Map;

public class UsersMock {
    private UsersMock() {
    }

    private static final Map<Long, User> userMap = new HashMap<>() {{
        put(1L, User.builder()
            .id(1L)
            .firstName("Иван")
            .secondName("Иванов")
            .email("ivan.ivanov@mail.ru")
            .password("123456")
            .phone("+375331234567")
            .build());
        put(2L, User.builder()
            .id(2L)
            .firstName("Петр")
            .secondName("Петров")
            .email("petr.petrov@yandex.ru")
            .password("654321")
            .phone("+375337654321")
            .build());
        put(3L, User.builder()
            .id(3L)
            .firstName("Алексей")
            .secondName("Алексеев")
            .email("alex.alexeev@gmail.com")
            .password("password")
            .phone("+375337654321")
            .build());
        put(4L, User.builder()
            .id(4L)
            .firstName("Антон")
            .secondName("Антонов")
            .email("anton.antonov@mail.ru")
            .password("anton")
            .phone("+375331234567")
            .build());
    }};

    public static User convertToUserWithoutPassword(final User user) {
        return User.builder()
            .id(user.getId())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .secondName(user.getSecondName())
            .phone(user.getPhone())
            .build();
    }

    public static User getById(final Long id) {
        return userMap.get(id);
    }
}
