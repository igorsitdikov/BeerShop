package mock;

import com.gp.beershop.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;

public class UsersMock {
    private UsersMock() {
    }

    private static final Map<Integer, UserDTO> userMap = new HashMap<>() {{
        put(1, UserDTO.builder()
            .id(1)
            .firstName("Иван")
            .secondName("Иванов")
            .email("ivan.ivanov@mail.ru")
            .password("123456")
            .phone("+375331234567")
            .build());
        put(2, UserDTO.builder()
            .id(2)
            .firstName("Петр")
            .secondName("Петров")
            .email("petr.petrov@yandex.ru")
            .password("654321")
            .phone("+375337654321")
            .build());
        put(3, UserDTO.builder()
            .id(3)
            .firstName("Алексей")
            .secondName("Алексеев")
            .email("alex.alexeev@gmail.com")
            .password("password")
            .phone("+375337654321")
            .build());
        put(4, UserDTO.builder()
            .id(4)
            .firstName("Антон")
            .secondName("Антонов")
            .email("anton.antonov@mail.ru")
            .password("anton")
            .phone("+375331234567")
            .build());
    }};

    public static UserDTO convertToUserWithoutPassword(final UserDTO user) {
        return UserDTO.builder()
            .id(user.getId())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .secondName(user.getSecondName())
            .phone(user.getPhone())
            .build();
    }

    public static UserDTO getById(final Integer id) {
        return userMap.get(id);
    }
}
