package webdriverdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverDemo {
	private static WebDriver driver;
	
	
	@BeforeAll
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver-win64\\chromedriver.exe");
		// Initialize Web Driver
        driver = new ChromeDriver();
        //Launch website
        driver.navigate().to("http://www.calculator.net/");
        // Maximize the browser
        driver.manage().window().maximize();
        // Click on Percent Calculators
        driver.findElement(By.xpath("//*[@id=\"hl3\"]/li[3]/a")).click();
        
	}
	
	@AfterEach
	public void cleanUp() {
		// firstInput.clear();
		// secondInput.clear();
		driver.findElement(By.xpath("//*[@id=\"content\"]/form[1]/table/tbody/tr[2]/td/input[3]")).click();
	}
	
	@AfterAll
	public static void tearDown() {
		driver.close();
	}
	
	@Test
    @DisplayName("Test Case 1: Valid Percentage Calculation")
    void testValidPercentageCalculation() {
        calculatePercentage("10", "50");
        assertResult("5");
    }

    @Test
    @DisplayName("Test Case 2: Zero as the First Number")
    void testZeroAsFirstNumber() {
        calculatePercentage("0", "50");
        assertResult("0");
    }

    @Test
    @DisplayName("Test Case 3: Values above 100 as Second Number")
    void testValuesAbove100AsSecondNumber() {
        calculatePercentage("120", "20");
        assertResult("24");
    }

    @Test
    @DisplayName("Test Case 4: Negative Values")
    void testNegativeValues() {
        calculatePercentage("-10", "50");
        assertResult("-5");
    }

    @Test
    @DisplayName("Test Case 5: Empty Input")
    void testEmptyInput() {
        calculatePercentage("", "50");
        assertResult("Invalid input");
    }
	
	private void calculatePercentage(String firstValue, String secondValue) {
		// Locate first & second Input box
		WebElement firstInput = driver.findElement(By.id("cpar1"));
		WebElement secondInput = driver.findElement(By.id("cpar2"));
		firstInput.sendKeys(firstValue);
        secondInput.sendKeys(secondValue);
        // Click on Calculate
        driver.findElement(By.xpath("//*[@id=\"content\"]/form[1]/table/tbody/tr[2]/td/input[2]")).click();
    }
	private void assertResult(String expectedResult) {
		String res = "Invalid input";
		try {
			// Obtain result and contrast with the expected result
	        WebElement resElement = driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]/font/b"));
	        res = resElement.getText();
		} catch (NoSuchElementException e) {
			// Nothing in case element is not found in DOM
		} finally {
			assertEquals(expectedResult, res);
		}
	}
}