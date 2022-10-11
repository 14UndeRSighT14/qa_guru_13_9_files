package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipParseTest {
    ClassLoader classLoader = FileParseTest.class.getClassLoader();

    @Test
    @DisplayName("Чтение из Zip-архива")
    void ZipTest() throws Exception {
        try(InputStream is = classLoader.getResourceAsStream("TestZipFile.zip")) {
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry entry;
            String nameFile;
            while ((entry = zis.getNextEntry()) != null){
                nameFile = entry.getName();
                switch (nameFile){
                    case "TestCsvDoc.csv":
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zis, UTF_8));
                        List<String[]> csv = csvReader.readAll();
                        assertThat(csv).contains(
                                new String[] {"username;password"},
                                new String[] {"test1;123456"},
                                new String[] {"test2;12345"},
                                new String[] {"test3;123"}
                        );
                        break;
                    case "TestExcelDoc.xlsx":
                        String [][] dataXLS = {
                                {"№", "Имя", "Фамилия", "Отчество", "Пол"},
                                {"1","Валентин", "Пудовкин","Анатольевич", "Мужской"},
                                {"2","Инга", "Гуськова", "Игоревна", "Женский"},
                                {"3","Алексей", "Гуськов", "Андреевич", "Мужской"},
                                {"4","Дмитрий", "Поцман", "Иванов", "Мужской"},
                                {"5","Яна", "Глушко", "Максимовна", "Женский"},
                        };
                        XLS xls = new XLS(zis);
                        String strCellValue = null;
                        for (int i = 0; i < 6; i++) {
                            for (int j = 0; j < 5; j++) {
                                Cell CellValue = xls.excel.getSheetAt(0)
                                        .getRow(i)
                                        .getCell(j);
                                if (CellValue.getCellType() == CellType.STRING)
                                    strCellValue = CellValue.getStringCellValue();
                                else if (CellValue.getCellType() == CellType.NUMERIC)
                                    strCellValue = String.valueOf(CellValue.getNumericCellValue());
                                assertThat(strCellValue).contains(dataXLS[i][j]);
                            }
                        }
                        break;
                    case "TestPdfDoc.pdf":
                        PDF pdf = new PDF(zis);
                        assertThat(pdf.text).contains("Тестовая pdf документация!");
                        break;
                    default:
                        System.out.println("Нет возможности распарсить файл '" + "'" + nameFile + "'");
                }
            }
        }
    }
}
