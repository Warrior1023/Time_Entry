package POC_pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hpsf.Date;
import org.apache.poi.ss.usermodel.*;
public class time_entry_page {

	private WebDriver driver;
	private Actions actions;
	 int rowCount = 0; // Variable to count non-empty cells
	 int columnToRead = 0; // Column A (0-indexed)
	 String excelFilePath = System.getProperty("user.dir") + "\\Test_Data\\" + "Mujeeb Khan-Daliy Status.xlsx";
	 String data;
	 String des_data;
	 String work_data;
	 String des;
	 String data_PROJECT;
	 String pto_description;
	 String pto_time;
	 String ticket;
	 
	 
	  // Constructor
    public time_entry_page(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    // Locators
    private By my_time = By.xpath("//span[normalize-space()='MyTime']");
    private By projectname_dropdown = By.xpath("(//input[@type='text'])[2]");
    private By C2M = By.xpath("(//span[@class='ng-option-label ng-star-inserted'])[1]");
    private By work = By.xpath("(//input[@type='text'])[3]");
    private By billable = By.xpath("(//input[@type='text'])[6]");
    private By work_element = By.xpath("(//span[@class='ng-option-label ng-star-inserted'])[1]");
    private By descriptions = By.xpath("//input[@placeholder='Description']");
    private By add_button = By.xpath("(//i[@class='fs-4 fas fa-plus-circle'])[1]");
    private By time = By.xpath("(//input[@class='form-control form-control-sm ng-untouched ng-pristine ng-invalid'])[1]");
    private By date = By.xpath("(//span[@class='timeentrystartDate d-block text-center ng-tns-c96-0'])[1]");
    private By pto_edit_button = By.xpath("//*[@id='editIcon_0' or @id='editIcon_323863']");
    private By pto_edit_des = By.xpath("//*[@id='description_textbox_0' or @id='description_textbox_323863']");
    private By pto_edit_time = By.xpath("//*[@id='time_textbox_323863' or @id='time_textbox_0']");
    private By pto_update_button = By.xpath("//*[@id='updateIcon_323863' or @id='updateIcon_0']");
    private By Ticket = By.xpath("//input[@placeholder='Ticket Number']");

    
    public void count_excel() throws IOException {
    	 
    	
    	try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
                 Workbook workbook = new XSSFWorkbook(fis)) {
                 
                Sheet sheet = workbook.getSheetAt(2); // Get the first sheet
                int columnToCount = 0; // Column index to count (0 for the first column)
          
                // Count non-empty rows in the specified column
                for (Row row : sheet) {
                    Cell cell = row.getCell(columnToCount);
                    if (cell != null && cell.getCellType() != CellType.BLANK) {
                        rowCount++;
                    }
                }

                System.out.println("Number of non-empty rows in column " + (columnToCount + 1) + ": " + rowCount);
             // Close the workbook
                workbook.close();
                fis.close();
    	} 
    	
    }
    
    public String getCombinedString() {
    
        String timestamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return timestamp;
  }
    
    public void My_time_entry() throws InterruptedException {
       	Thread.sleep(7000);
        driver.findElement(my_time).click();
        Thread.sleep(5000);
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(1));
        driver.getCurrentUrl();
    }
    
    public void select_date(String no) throws InterruptedException {
    	Thread.sleep(6000);
    	driver.findElement(date).click();
    	Thread.sleep(6000);
    	String date_no = "(//div[normalize-space()='";
    	String add_no = no;
    	String rem_string = "'])[1]";
        String ADate_no = date_no + add_no + rem_string;
        System.out.println("date x path is "+ADate_no);
        driver.findElement(By.xpath(ADate_no)).click();
        Thread.sleep(4000);
    }
    
    public void read_from_excel(String targetDate) throws IOException, InterruptedException {
    	
       
        FileInputStream fis = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0); // Get the desired sheet
        // Initialize FormulaEvaluator
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        int dateColumnIndex = 0; // Adjust based on your Excel sheet structure

        // Iterate through rows
        for (int i1 = 1; i1 < sheet.getPhysicalNumberOfRows(); i1++) { // Skip header row
            Row row = sheet.getRow(i1);

            if (row != null) {
                Cell dateCell = row.getCell(dateColumnIndex);

                if (dateCell != null) {
                    String cellDate = getCellValue(dateCell);

                    // Check if the date matches the target date
                    if (cellDate.equals(targetDate)) {
                        System.out.println("Processing row: " + (i1 + 1));
                    
                        
                        // Extract other data from the row
                        Cell cell_des = row.getCell(12);
                        Cell cellData = row.getCell(10);
                        Cell cellWork = row.getCell(8);
                        Cell CellTicket = row.getCell(6);
                        Cell cellProject = row.getCell(4);

                        des_data= getCEllValue(cell_des,evaluator);
                        data = getCellValue(cellData);
                        work_data = getCellValue(cellWork);
                        data_PROJECT = getCellValue(cellProject);
                        ticket= getCellValue(CellTicket);
                       /* System.out.println("1"+des_data);
                        System.out.println("2"+data);
                        System.out.println("3"+work_data);
                        System.out.println("4"+data_PROJECT);*/
                         
                        
                        
                       
                        // Perform actions
                        Thread.sleep(4000);
                        driver.findElement(billable).click();
                    	driver.findElement(billable).sendKeys("No");
                    	WebElement no = driver.findElement(By.xpath("(//div[@class='ng-option ng-star-inserted ng-option-marked'])[1]"));
                    	actions.moveToElement(no).click().build().perform();
                        Thread.sleep(2000);
                        System.out.println("1"+data_PROJECT);
                        driver.findElement(projectname_dropdown).click();
                        driver.findElement(projectname_dropdown).sendKeys(data_PROJECT);
                        driver.findElement(C2M).click();
                        Thread.sleep(2000);
                        System.out.println(data);
                        driver.findElement(time).sendKeys(data);
                        Thread.sleep(3000);
                        System.out.println(des_data);
                        driver.findElement(descriptions).sendKeys(des_data);
                        Thread.sleep(4000);
                        System.out.println(ticket);
                        driver.findElement(Ticket).sendKeys(ticket);
                        Thread.sleep(4000);
                        System.out.println(work_data);
                        driver.findElement(work).click();
                        driver.findElement(work).sendKeys(work_data);
                        driver.findElement(work_element).click();
                        driver.findElement(add_button).click();
                        Thread.sleep(6000);
                        System.out.println("Successfully inserted row number: " + i1);
                    }
                }
            }
        }

        workbook.close();
        fis.close();
        }
    

    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new DataFormatter().formatCellValue(cell);
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }


    
    public void PTO_entry() throws IOException, InterruptedException {
    	driver.findElement(pto_edit_button).click();
    	  FileInputStream fis = new FileInputStream(new File(excelFilePath));
          @SuppressWarnings("resource")
   	Workbook workbook = new XSSFWorkbook(fis);
          
          Sheet sheet = workbook.getSheetAt(2);
          Row row = sheet.getRow(1);
          Cell cell_pto_des = row.getCell(4);
          Cell cell_pto_time = row.getCell(5);
       // Check the cell type and read the value accordingly
          switch (cell_pto_des.getCellType()) {
              case STRING:
            	  pto_description = cell_pto_des.getStringCellValue();
                  break;
              case NUMERIC:
            	  pto_description = String.valueOf(cell_pto_des.getNumericCellValue());
                  break;
              case BOOLEAN:
            	  pto_description = String.valueOf(cell_pto_des.getBooleanCellValue());
                  break;
              default:
                  pto_description = ""; // Handle other types as needed
                  break;
          }
          
          switch (cell_pto_time.getCellType()) {
          case STRING:
        	  pto_time = cell_pto_time.getStringCellValue();
              break;
          case NUMERIC:
        	  pto_time = String.valueOf(cell_pto_time.getNumericCellValue());
              break;
          case BOOLEAN:
        	  pto_time = String.valueOf(cell_pto_time.getBooleanCellValue());
              break;
          default:
        	  pto_time = ""; // Handle other types as needed
              break;
      }
      
          
    driver.findElement(pto_edit_des).sendKeys(pto_description);
    Thread.sleep(2000);
  	driver.findElement(pto_edit_time).sendKeys(pto_time);
  	Thread.sleep(2000);
   	driver.findElement(pto_update_button).click();
    	Thread.sleep(6000);
    }
    private String getCEllValue(Cell cell, FormulaEvaluator evaluator) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new DataFormatter().formatCellValue(cell); // Format date values
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                CellValue evaluatedValue = evaluator.evaluate(cell);
                if (evaluatedValue == null) return "";
                switch (evaluatedValue.getCellType()) {
                    case STRING:
                        return evaluatedValue.getStringValue().trim();
                    case NUMERIC:
                        return String.valueOf(evaluatedValue.getNumberValue());
                    case BOOLEAN:
                        return String.valueOf(evaluatedValue.getBooleanValue());
                    default:
                        return ""; // Handle other cases like ERROR
                }
            default:
                return ""; // Handle BLANK and other unsupported types
        }
    }

   }
    
    
   
    	
    
    
	

