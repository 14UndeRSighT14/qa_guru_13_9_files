package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import guru.qa.domain.Teacher;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {
    ClassLoader classLoader = FileParseTest.class.getClassLoader();

    @Test
    void pdfTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File file = $("a[href*='junit-user-guide']").download();
        // "*=" - содержит подстроку
        // "^=" - начинается с такой подстроки
        PDF pdf = new PDF(file);
        assertThat(pdf.author).isEqualTo("Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein");
    }

    @Test
    void xlsTest() throws Exception{
        open("https://sample-videos.com/download-sample-xls.php");
        File file = $("a[href*='Sample-Spreadsheet-10-rows.xls']").download();
        XLS xls = new XLS(file);
        assertThat(
                xls.excel.getSheetAt(0)
                .getRow(2)
                .getCell(2)
                .getStringCellValue()
        ).isEqualTo("Barry French");
    }

    @Test
    void csvTest() throws Exception {
        try(InputStream is = classLoader.getResourceAsStream("example.csv")) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(is, UTF_8));
            List<String[]> csv = csvReader.readAll();
            assertThat(csv).contains(
                   new String[] {"teacher","lesson","date"},
                    new String[] {"Prepod1","junit5","03.06"},
                    new String[] {"Prepod2","allure","04.06"}
            );
        }
    }

    @Test
    void zipTest() throws Exception {
        try(InputStream is = classLoader.getResourceAsStream("TestZipFile.zip")) {
            ZipInputStream zipInputStream = new ZipInputStream(is);
            ZipEntry entry;
            int i = 0;
            while ((entry = zipInputStream.getNextEntry()) != null){
                if(i == 0){
                    assertThat(entry.getName()).isEqualTo("TestCsvDoc.csv");
                }
                if(i == 1){
                    assertThat(entry.getName()).isEqualTo("TestExcelDoc.xlsx");
                }
                if(i == 2){
                    assertThat(entry.getName()).isEqualTo("TestPdfDoc.pdf");
                }
                i++;
            }
        }

    }

    @Test
    void jsonTest() throws Exception {
        try(InputStream is = classLoader.getResourceAsStream("teacher.json")) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);
            assertThat(jsonObject.get("name").getAsString()).isEqualTo("Вячеслав");
            assertThat(jsonObject.get("isGoodTeacher").getAsBoolean()).isEqualTo(true);
            assertThat(jsonObject.get("age").getAsInt()).isEqualTo(33);
            assertThat(jsonObject.get("passport").getAsJsonObject().get("serial").getAsInt()).isEqualTo(8009);
            assertThat(jsonObject.get("passport").getAsJsonObject().get("number").getAsInt()).isEqualTo(123456);
        }
    }

    @Test
    void jsonTestNextGeneration() throws Exception {
        try(InputStream is = classLoader.getResourceAsStream("teacher.json")) {
            Gson gson = new Gson();
            Teacher jsonObject = gson.fromJson(new InputStreamReader(is), Teacher.class);
            assertThat(jsonObject.getName()).isEqualTo("Вячеслав");
            assertThat(jsonObject.isGoodTeacher()).isEqualTo(true);
            assertThat(jsonObject.getAge()).isEqualTo(33);
            assertThat(jsonObject.getPassport().getSerial()).isEqualTo(8009);
            assertThat(jsonObject.getPassport().getNumber()).isEqualTo(123456);

        }
    }

}
