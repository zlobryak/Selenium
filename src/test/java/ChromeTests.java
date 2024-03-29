import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChromeTests {

    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void test() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Василий Уткин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79062421277");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actual);
    }

    @Test
    void negativeTestNameIsEmpty() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79062421277");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    void negativeTestPhoneIsEmpty() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Василий Уткин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    void negativeTestPhoneAndNameIsEmpty() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }
    @Test
    void negativeTestNameIsWrong() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Vasilij Utkin");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }
    @Test
    void negativeTestNoAgreement() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Случайное Имя");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("123");
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getCssValue("color");
        String expected = "rgba(255, 92, 92, 1)";
        assertEquals(expected, actual);
    }
}