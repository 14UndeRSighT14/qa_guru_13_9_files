package guru.qa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.domain.CarOwner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonParseTest {

    ClassLoader classLoader = FileParseTest.class.getClassLoader();

    @Test
    @DisplayName("Чтение из Json при помощи jackson")
    void JsonTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try(InputStream is = classLoader.getResourceAsStream("TestJsonFile.json")) {
            List<CarOwner> carOwnerList = objectMapper.readValue(new InputStreamReader(is, UTF_8), new TypeReference<>(){});
            assertThat(carOwnerList).hasSize(3);
            assertThat(carOwnerList.get(2).getName()).isEqualTo("Иван");
            assertThat(carOwnerList.get(2).getAge()).isEqualTo(44);
            assertThat(carOwnerList.get(2).getOnCredit()).isEqualTo(false);
            assertThat(carOwnerList.get(2).getCars().getName()).isEqualTo("Lexus");
            assertThat(carOwnerList.get(2).getCars().getModels()).isEqualTo("rx 350");
        }
    }
}
