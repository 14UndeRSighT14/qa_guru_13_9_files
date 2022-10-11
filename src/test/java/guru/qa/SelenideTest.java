package guru.qa;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideTest {

    @Test
    void downloadTest() throws Exception {
        open("https://github.com/junit-team/junit5/blob/main/README.md");
        File file = $("#raw-url").download();
        try (InputStream is = new FileInputStream(file)) {
            byte[] fileContent = is.readAllBytes();
            String asString = new String(fileContent, UTF_8);
            assertThat(asString).contains("Contributions to JUnit 5");
        }
    }

    @Test
    void uploadTest() {
        open("https://the-internet.herokuapp.com/upload");
        //$("input[type='file']").uploadFile(new File("/src/test/resources/1.txt"));
        $("input[type='file']").uploadFromClasspath("1.txt");
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(Condition.text("1.txt")).shouldBe(Condition.visible);
    }
}
