package Excercise_Challenge;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class AppiumTest<var> {
	AndroidDriver<AndroidElement> driver;
	String ModelName = "Model1";
	String ServerName = "http://127.0.0.1:4723/wd/hub";
	int passedTests=0;
	int failedTests=0;



	@BeforeClass
	public void setUp() throws MalformedURLException {
		//load all neccessary information
		File appDir = new File("C:\\tools\\Challenge");
		File app = new File(appDir, "app-debug.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ModelName);
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		driver = new AndroidDriver<AndroidElement>(new URL(ServerName), capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void Test() throws MalformedURLException {
		int random = (int )(Math.random() * 50 + 1);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//TEST 1
		//verify whether the application is launched
		WebElement appLaunch= driver.findElementByXPath("//android.widget.TextView[@index='0']");
		System.out.println(appLaunch.getText());
		Assert.assertEquals(appLaunch.getText(),"Challenge");
		if(appLaunch.getText().equals("Challenge"))
		{
			System.out.println("Test 1 is passed, Application is launched");
			passedTests=passedTests+1;
		} else
		{
			System.out.println("Test 1 is failed, failed to launch the application");
			failedTests=failedTests+1;
		}

		//TEST 2
		//Verify whether an item is selected
		//selecting an item called 'Practical remove'
		WebElement selectItem= (WebElement) driver.findElementByXPath("//*[@text='Practical Remove']");
		Assert.assertEquals(selectItem.getText(),"Practical Remove", "The practical remove shoes was displayed in the list");
		//Click on the product from the list
		driver.findElement(By.xpath("//*[@text='Practical Remove']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Verify whether the product review screen was displayed
		WebElement itemDisplay= driver.findElementByXPath("//android.widget.TextView[@index='1']");
		if (itemDisplay.getText().equals("Practical Remove"))
		{
			System.out.println("Test 2 is passed, 'Practical remove' item is selected");
			passedTests=passedTests+1;
		} else
		{
			System.out.println("Test 2 is failed, failed to open the product details page");
			failedTests=failedTests+1;
		}

		//TEST 3
		//Verify if review button is present
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement addReviewBtn=driver.findElement(By.xpath("//*[@text='ADD REVIEW']"));

				if (addReviewBtn.isDisplayed())
				//if(addReviewBtn.getText().equals("Add review"))
				{
					System.out.println("Test 3 is passed, 'Add review' button is present");
					passedTests=passedTests+1;
				} else
				{
					System.out.println("Test 3 is failed, 'Add review' is not present");
					failedTests=failedTests+1;
				}

		//Adding a review
		driver.findElement(By.xpath("//*[@text='ADD REVIEW']")).click();
		//Add a review, rating and click the save button
		driver.findElement(By.xpath("//android.widget.EditText[@index='0']")).click();
		driver.findElement(By.xpath("//android.widget.EditText[@index='0']")).sendKeys("This is a fantastic product" + random);
		List<AndroidElement> items = driver.findElements(By.xpath("//android.widget.Spinner"));
		items.get(0).click();
		driver.findElement(By.xpath("//*[@text='1']")).click();
		driver.findElement(By.xpath("//*[@text='SAVE']")).click();

		//Verify whether the recently added review is present in the review list
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {
			driver.findElementByXPath("//*[@text='This is a fantastic product" + random + " 1']").isDisplayed();
			System.out.println("Test 4 is passed, Review is added and visible in the review list");
			passedTests=passedTests+1;
		}catch (Exception e){
			System.out.println("Test 4 is failed, Review is not added and visible in the review list");
			throw new RuntimeException("Test failed");
		}
}

	@AfterClass
	public void teardown(ITestContext context){
		System.out.println("Number of tests passed:" + passedTests);
		System.out.println("Number of tests failed:" + failedTests);
		driver.quit();
	}
}
