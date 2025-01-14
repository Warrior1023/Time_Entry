//Created By Abhi Rathore
package Nou_testTime_entry;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;

import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Nou_time_today {
	
	public WebDriver driver;
	private POC_pages.time_entry_page Time_entry_page;

	
	@BeforeTest
	public void setup() throws InterruptedException   {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		Time_entry_page = new POC_pages.time_entry_page(driver);
		POC_pages.LoginPage loginPage = new POC_pages.LoginPage(driver);
		
		loginPage.nou_time_entry();
		
	}
	 @Test
		public void login() throws InterruptedException {
		Thread.sleep(3000);
		POC_pages.LoginPage loginPage = new POC_pages.LoginPage(driver);
		
     // Enter login credentials
     loginPage.enterUsername("abhir@plasmacomp.com");
     loginPage.enterPassword("Plasma1!");
     loginPage.clickLoginButton();

     // Wait for a moment to ensure page loads
     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    
}
	 @Test(enabled = true, priority =1)
	 public void click_my_time() throws InterruptedException, IOException {
		 Time_entry_page.count_excel();
		 Time_entry_page.My_time_entry(); 
		 Thread.sleep(3000);
		 String timestamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//Time_entry_page.select_date("8");// Select Date
		 Time_entry_page.read_from_excel(timestamp);

	 }
	 @Test(enabled = false, priority =2)
	 public void PTO() throws IOException, InterruptedException {
		 
		 Time_entry_page.My_time_entry(); 
		 Thread.sleep(3000);
		 Time_entry_page.select_date("");// Select Date 
		 Time_entry_page.PTO_entry();
	 }
	 
	 @AfterTest(enabled=false)
		public void Teardown() {
	        driver.close();
			driver.quit();
}
}
