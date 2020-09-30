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
        System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    void successCase() {   // полностью корректное заполнение;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String successText = driver.findElement(By.cssSelector("[data-test-id]")).getText();
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", successText);
        // тест проходит успешно;
    }

    @Test
    void blankedCheckbox() {  // чекбокс не отмечен
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector(".button__text")).submit();
        String blankedCheckboxText = driver.findElement(By.cssSelector("[data-test-id=agreement]")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", blankedCheckboxText);
        // тест проверяет текст, но не его цвет, пока не знаю как указать в селекторе именно input__invalid;
    }

    // КЕЙСЫ С ПОЛЕМ ДЛЯ ВВОДА ИМЕНИ И ФАМИЛИИ:
    // Сейчас тесты падают;
    // Чтобы тесты с валидацией имени успешно проходили МОЖНО указать в селекторе input__sub, но input__sub'а на странице два;
    // Поэтому нужно/желательно(?) указать input__sub именно для [data-test-id=name];
    // =========>
    @Test
    void latinLettersInName() {   // имя на латинице;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Default Name");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNameText = driver.findElement(By.cssSelector("[data-test-id=name]")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", wrongNameText);
    }

    @Test
    void digitsInName() {   // Цифры в имени;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("12123312 123123");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNameText = driver.findElement(By.cssSelector("[data-test-id=name]")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", wrongNameText);
    }

    @Test
    void emptyNameField() {  // незаполненное имя;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String emptyNameFieldText = driver.findElement(By.cssSelector("[data-test-id=name]")).getText();
        assertEquals("Поле обязательно для заполнения", emptyNameFieldText);
    }

    // КЕЙСЫ С ПОЛЕМ ДЛЯ ВВОДА НОМЕРА ТЕЛЕФОНА:
    // Сейчас тесты падают;
    // Для кейсов с валидацией номера телефона необходимо указать input__sub относящийся именно к [data-test-id=phone];
    // =========>
    @Test
    void firstDigitIsEight() {   // в номере телефона 10 цифр (меньше, чем должно быть)
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("81717171717");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNumberText = driver.findElement(By.cssSelector("[data-test-id=phone]")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", wrongNumberText);
    }

    @Test
    void twelveDigitsInNumber() {  // 12 цифр в номере телефона (больше, чем должно быть);
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+717171717171");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNumberText = driver.findElement(By.cssSelector("[data-test-id=phone]")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", wrongNumberText);
    }

    @Test
    void emptyPhoneNumber() {  // незаполненный номер телфона;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String emptyNumberFieldText = driver.findElement(By.cssSelector("[data-test-id=phone]")).getText();
        assertEquals("Поле обязательно для заполнения", emptyNumberFieldText);
    }

    @Test
    void lettersInNumber() {  // буквы в номере телфона;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+7901number8");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNumberText = driver.findElement(By.cssSelector("[data-test-id=phone]")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", wrongNumberText);
    }

    @Test
    void twelvePlusesInNumber() {  // 12 знаков "+" в номере телфона;
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("++++++++++++");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNumberText = driver.findElement(By.cssSelector("[data-test-id=phone]")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", wrongNumberText);
    }
}
