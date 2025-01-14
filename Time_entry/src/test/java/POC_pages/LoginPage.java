package POC_pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	private WebDriver driver;
	
	
	String C2m_preprod = "https://dccloud-preprod.c2m.net/data-interface/my-data-source";
	String URL1= "https://cloud-staging-console.c2m.net/login";
	String Nou_link = "https://cloud.c2m.net/login";
    String Nou_preprod = "https://dccloud-preprod-nou.c2m.net/app-list";
    // Locators using @FindBy annotation
    @FindBy(id = "login-username")
    private WebElement usernameField;

    @FindBy(id = "login-password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // Initialize PageFactory elements
    }
    
    public void c2m_Staging(){
    	driver.get(URL1);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }
    public void c2m_preprod(){
    	driver.get(C2m_preprod);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }
    
    public void nou_time_entry(){
    	driver.get(Nou_link);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    // Method to enter username
    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    // Method to enter password
    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    // Method to click the login button
    public void clickLoginButton() throws InterruptedException {
        loginButton.click();
        Thread.sleep(5000);
    }

    // Method to verify login success
    public boolean isLoginSuccessful() {
        WebElement profileIconElem = driver.findElement(By.xpath("//img[@alt='avatar']"));
        return profileIconElem.isDisplayed();

    }
}

