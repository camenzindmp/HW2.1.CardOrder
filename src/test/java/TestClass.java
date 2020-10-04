import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClass {

    private WebDriver driver;

    @BeforeAll
    static void driver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null; }

    @Test
    void successCase() {   // полностью корректное заполнение;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String successText = driver.findElement(By.cssSelector("[data-test-id]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", successText.trim());
    }

    @Test
    void blankedCheckbox() {  // чекбокс не отмечен
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector(".button__text")).submit();
        String blankedCheckboxText = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", blankedCheckboxText);
    }

    // КЕЙСЫ С ПОЛЕМ ДЛЯ ВВОДА ИМЕНИ И ФАМИЛИИ:
    @Test
    void latinLettersInName() {   // имя на латинице;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Default Name");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNameText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", wrongNameText);
        // тест проходит успешно;
    }

    @Test
    void digitsInName() {   // Цифры в имени;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("12123312 123123");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNameText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", wrongNameText);
    }

    @Test
    void emptyNameField() {  // незаполненное имя;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String emptyNameFieldText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", emptyNameFieldText);
    }

    // КЕЙСЫ С ПОЛЕМ ДЛЯ ВВОДА НОМЕРА ТЕЛЕФОНА:
    @Test
    void firstDigitIsEight() {   // в номере телефона 10 цифр (меньше, чем должно быть)
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("81717171717");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNumberText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", wrongNumberText);
    }

    @Test
    void twelveDigitsInNumber() {  // 12 цифр в номере телефона (больше, чем должно быть);
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+717171717171");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNumberText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", wrongNumberText);
    }

    @Test
    void emptyPhoneNumber() {  // незаполненный номер телфона;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String emptyNumberFieldText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", emptyNumberFieldText);
    }

    @Test
    void lettersInNumber() {  // буквы в номере телфона;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+7901number8");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNumberText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", wrongNumberText);
    }

    @Test
    void twelvePlusesInNumber() {  // 12 знаков "+" в номере телфона;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("++++++++++++");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNumberText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", wrongNumberText);
    }
}
