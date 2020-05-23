package mock;

import com.gp.beershop.dto.Beer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class BeerMock {
    private BeerMock() {
    }

    private static Map<Long, Beer> beerMap = new HashMap<>() {{
        put(1L, Beer.builder()
            .id(1L)
            .type("светлое")
            .inStock(true)
            .name("Лидское")
            .description("Лучшее пиво по бабушкиным рецептам")
            .alcohol(5.0)
            .density(11.5)
            .country("Республика Беларусь")
            .price(BigDecimal.valueOf(5).setScale(2)).build());
        put(2L, Beer.builder()
            .id(2L)
            .type("темное")
            .inStock(true)
            .name("Аливария")
            .description("Пиво номер 1 в Беларуси")
            .alcohol(4.6)
            .density(10.2)
            .country("Республика Беларусь")
            .price(BigDecimal.valueOf(3).setScale(2)).build());
        put(3L, Beer.builder()
            .id(3L)
            .type("светлое осветлённое")
            .inStock(true)
            .name("Pilsner Urquell")
            .description("непастеризованное")
            .alcohol(4.2)
            .density(12.0)
            .country("Чехия")
            .price(BigDecimal.valueOf(8).setScale(2)).build());
        put(4L, Beer.builder()
            .id(4L)
            .type("светлое")
            .inStock(true)
            .name("Крынiца Pilsner")
            .description("Мягкое пиво с чистым гармоничным вкусом, " +
                         "искрящимся золотистым цветом и плотной белоснежной пеной. " +
                         "Этот сорт придется по вкусу тем, кто ищет легкое пиво, " +
                         "сохраняющее при этом свежий букет ароматного хмеля и деликатную " +
                         "горчинку во вкусе.")
            .alcohol(4.4)
            .density(10.5)
            .country("Республика Беларусь")
            .price(BigDecimal.valueOf(3.2).setScale(2)).build());
        put(5L, Beer.builder()
            .id(3L)
            .type("светлое осветлённое")
            .inStock(false)
            .name("Pilsner Urquell Extra")
            .description("непастеризованное")
            .alcohol(4.5)
            .density(12.2)
            .country("Чехия")
            .price(BigDecimal.valueOf(8.3).setScale(2)).build());
        put(6L, Beer.builder()
            .id(4L)
            .type("светлое")
            .inStock(true)
            .name("Крынiца Pilsner")
            .description("Мягкое пиво с чистым гармоничным вкусом, " +
                         "искрящимся золотистым цветом и плотной белоснежной пеной. " +
                         "Этот сорт придется по вкусу тем, кто ищет легкое пиво, " +
                         "сохраняющее при этом свежий букет ароматного хмеля и деликатную " +
                         "горчинку во вкусе.")
            .alcohol(4.4)
            .density(10.5)
            .country("Республика Беларусь")
            .price(BigDecimal.valueOf(5.2).setScale(2)).build());
    }};

    public static Beer getById(final Long id) {
        return beerMap.get(id);
    }
}
