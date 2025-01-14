//Created By Abhi Rathore
package Nou_testTime_entry;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Nou_time_entry {
	
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
	  @Test(enabled = true, priority = 1)
	    public void click_my_time() throws InterruptedException, IOException {
	        Time_entry_page.count_excel();
	        Time_entry_page.My_time_entry();
	        Thread.sleep(3000);

	        // List of dates in "yyyy-MM-dd" format
	        List<String> dates = Arrays.asList("2025-01-09");

	        // Iterate through each date
	        for (String date : dates) {
	            System.out.println("Processing date: " + date);

	            // Extract day (DD) from the date (yyyy-MM-dd)
	            String day = extractDayFromDate(date);

	            // Call select_date with extracted day (DD)
	            Time_entry_page.select_date(day);

	            // Call read_from_excel with the full date
	            Time_entry_page.read_from_excel(date);
	        }
	    }
	 @Test(enabled = false, priority =2)
	 public void PTO() throws IOException, InterruptedException {
		 
		 Time_entry_page.My_time_entry(); 
		 Thread.sleep(3000);
		 Time_entry_page.select_date("");// Select Date 
		 Time_entry_page.PTO_entry();
	 }
	// Helper method to extract day (DD) from a date in "yyyy-MM-dd" format
	    private String extractDayFromDate(String date) {
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            Date parsedDate = sdf.parse(date);
	            Calendar cal = Calendar.getInstance();
	            cal.setTime(parsedDate);

	            // Return the day (DD) as a string
	            return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "";
	        }
	    }

	 @AfterTest(enabled=true)
		public void Teardown() {
	        driver.close();
			driver.quit();
}
}
