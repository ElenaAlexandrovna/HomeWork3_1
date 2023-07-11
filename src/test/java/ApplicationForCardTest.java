
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class ApplicationForCardTest {
    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");

    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void differentTest()  {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+78880000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);
//        Thread.sleep(5000);// Задержка,чтобы брауер не сразу закрывался


    }

    @Test
    void wrongNameTest()  {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanova Milena");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+78880000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);


    }

    @Test
    void nothingNameTest() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+78880000000");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);


    }

    @Test
    void nothingPhoneTest() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);

    }

    @Test
    void longPhoneTest() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999876543219");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);


    }

    @Test
    void notCheckBoxTest()  {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+78880000000");
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText().trim();
        assertEquals(expected, actual);


    }
}
