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
    void successCase() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+71717171717");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String successText = driver.findElement(By.cssSelector("[data-test-id]")).getText();
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", successText);
    }

    @Test
    void firstDigitIsEight() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Дефолтное Имя");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("81717171717");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).submit();
        String wrongNumberText = driver.findElement(By.cssSelector("[data-test-id=phone] input")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", wrongNumberText);
    }
}
