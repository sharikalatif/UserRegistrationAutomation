import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SignUpTest {

	WebDriver chromeDriver = null;

	@Before
	public void initDriver() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resoures/driver/chromedriver.exe");
		chromeDriver = new ChromeDriver();
	}

	@Test (expected = Exception.class)
	public void testMeetupSignUp() throws InterruptedException {
		// Open temp email ID generating web site
		chromeDriver.get("https://temp-mail.org/en/");
		// wait to load cookie popup
		Thread.sleep(5000);
		// click ok on cookie popup
		chromeDriver.findElement(By.linkText("Verstanden!")).click();

		// Copy generated emailID
		String emailID = chromeDriver.findElement(By.id("mail")).getAttribute("value");
		chromeDriver.findElement(By.cssSelector("Body")).sendKeys(Keys.CONTROL + "t");

		// open meetup website
		chromeDriver.get("https://secure.meetup.com/");

		// Click on Sign Up
		chromeDriver.findElement(By.xpath("//*[@id=\"globalNav\"]/nav/div/div[4]/button/div/span")).click();

		// Click on sign up with email
		chromeDriver
				.findElement(By.xpath("//*[@id=\"globalNav\"]/nav/div/div[2]/div/div[2]/div[2]/div[6]/a/div/div[2]"))
				.click();

		// enter sign up details
		chromeDriver.findElement(By.id("register-field--name")).sendKeys("test");
		chromeDriver.findElement(By.id("register-field--email")).sendKeys(emailID);
		chromeDriver.findElement(By.id("register-field--password")).sendKeys("test123");

		// Click on cookie popup
		chromeDriver.findElement(By.linkText("Continue")).click();

		// Submit registration form
		chromeDriver.findElement(By.xpath("//*[@id=\"register-fieldset--email\"]/div[6]/p/button")).click();

		// wait to get registration confirmation email and goto mail box of temp
		// generated ID
		Thread.sleep(5000);
		chromeDriver.get("https://temp-mail.org/en/");

		// Some time cookie popup comes again so wait for it
		Thread.sleep(5000);
		try {
			chromeDriver.findElement(By.linkText("Verstanden!")).click();
		} catch (Exception e) {

		}
		// click on received email
		chromeDriver.findElement(By.xpath("//*[@id=\"mails\"]/tbody/tr/td[1]/a")).click();

		// wait to load complete mail elements
		Thread.sleep(5000);
		chromeDriver.findElement(By.partialLinkText("Verify your accou")).click();

		// submit photo
		chromeDriver.findElement(By.name("photo")).sendKeys("<enter here your path for photo>");

	}
	
	@After
	public void cleanUp() {
		chromeDriver.quit();
	}
}
