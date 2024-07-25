package test.wiki;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import test.wiki.data.Title;
import java.util.List;
import java.util.stream.Stream;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class TestWiki {

    public static final Logger log = LogManager.getLogger(TestWiki.class);

    @BeforeEach
    void setup() {

        open("https://ru.wikipedia.org/");
    }

    @ValueSource(strings = {
            "Обсуждение", "Вклад", "Создать учётную запись", "Войти"
    })
    @ParameterizedTest(name = "В <баре> должна присутсовать ссылка {0} и отдавать страницу с заголовком содержащим {0}")
    @Tag("regress")
    void barTest(String side) {
        $$("#p-personal li").find(text(side)).click();
        $("#content h1").shouldHave(text(side));
        log.debug("barTest -  debug!");
        log.info("barTest -  info!");
        log.warn("barTest - warn");
        log.error("barTest - error");
        log.fatal("barTest - FATAL");
    }

    @CsvSource(value = {
            "ЮАР| Южно-Африканская Республика",
            "ЦАР| Центральноафриканская Республика"

    }, delimiter = '|')
    @ParameterizedTest (name = "Для поискового запроса  {0} в наименование статьи должна отображаться {1}")
    @Tag("regress")
    void searchTest1(String NameСountrySlim, String NameСountryLong) {
        $("#searchform .vector-search-box-input").setValue(NameСountrySlim);
        $("#searchform .searchButton").click();
        $("#content  #firstHeading").shouldHave(text(NameСountryLong));
        log.debug("searchTest1 -  debug!");
        log.info("searchTest1 -  info!");
        log.warn("searchTest1 - warn");
        log.error("searchTest1 - error");
        log.fatal("searchTest1 - FATAL");
    }

    @CsvFileSource(resources  = "/test/wiki/file.csv")
    @ParameterizedTest (name = "Для поискового запроса  {0} в наименование статьи должна отображаться {1}")
    @Tag("regress")
    void searchTest2(String NameСountrySlim, String NameСountryLong) {
        $("#searchform .vector-search-box-input").setValue(NameСountrySlim);
        $("#searchform .searchButton").click();
        $("#content  #firstHeading").shouldHave(text(NameСountryLong));
        log.debug("searchTest2 -  debug!");
        log.info("searchTest2 -  info!");
        log.warn("searchTest2 - warn");
        log.error("searchTest2 - error");
        log.fatal("searchTest2 - FATAL");
    }

    @EnumSource(Title.class)
    @ParameterizedTest
    void searchTest3(Title title) {
        $$("#p-participation li span").find(text(title.name())).click();
        $(".mw-body h1 .mw-page-title-main").shouldHave(text(title.description));
        log.debug("searchTest3 -  debug!");
        log.info("searchTest3 -  info!");
        log.warn("searchTest3 - warn");
        log.error("searchTest3 - error");
        log.fatal("searchTest3 - FATAL");
    }

    static Stream<Arguments> searchTest4() {
        return Stream.of(
                Arguments.of(Title.Форум, List.of("Начинающим","Сообщество","Порталы","Избранное","Проекты","Запросы","Оценивание","Форум", "Опросы", "Голосования", "Обсуждение правил", "Заявки на статусы", "Арбитражный комитет", "Проверка участников")),
                Arguments.of(Title.Справка,List.of("Начинающим","Сообщество","Порталы","Избранное","Проекты","Запросы","Оценивание","Справка", "ЧАВО", "Правила и указания", "Шпаргалка", "Поиск", "Песочница"))
        );
    }
    @MethodSource()
    @ParameterizedTest
    void searchTest4(Title title, List<String> expectedButtons) {
        $$("#p-participation li span").find(text(title.name())).click();
        $$("div[class='ts-Навигация_по_сообществу hlist hlist-items-nowrap'] li a").shouldHave(texts(expectedButtons));
        log.debug("searchTest4 -  debug!");
        log.info("searchTest4 -  info!");
        log.warn("searchTest4 - warn");
        log.error("searchTest4 - error");
        log.fatal("searchTest4 - FATAL");
    }


}
