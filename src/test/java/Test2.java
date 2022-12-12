import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test2 {


    List<String> titles = new ArrayList<>();
    WebDriver driver;

    @Before
    public void start(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.webstaurantstore.com");
        driver.manage().window().maximize();
        driver.findElement(By.id("searchval")).sendKeys("stainless work table", Keys.ENTER);

    }

    @After
    public void end(){
        driver.quit();
            }


    @Test
    public void test1_base() throws InterruptedException {



        List<WebElement> items = driver.findElements(By.xpath("//div[@id='product_listing']/div/div/a[@class='block']"));



        for(int i = 0; i<1;i++){//items.size()
            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@id='product_listing']/div/div/a[@class='block']")).get(i)));
            driver.findElements(By.xpath("//div[@id='product_listing']/div/div/a[@class='block']")).get(i).click();
                        titles.add(driver.getTitle().toString());
                        driver.navigate().back();



        }

        String lastPage = driver.findElement(By.xpath("//a[contains(@aria-label,'last page')]")).getText();
        int pages= Integer.valueOf(lastPage);



        while(pages>1){
            Thread.sleep(3000);

            driver.findElement(By.cssSelector("#paging > nav > ul > li.inline-block.leading-4.align-top.rounded-r-md")).click();
            for(int i = 0; i<1;i++) {//items.size()
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@id='product_listing']/div/div/a[@class='block']")).get(i)));
                driver.findElements(By.xpath("//div[@id='product_listing']/div/div/a[@class='block']")).get(i).click();
                titles.add(driver.getTitle().toString());
                driver.navigate().back();
                pages--;

            }

        }

    }

    @Ignore
    @Test
    public void Test2_assertList(){

        for(String title:titles){

            Assert.assertTrue(title.contains("Table"));

        }


    }
    @Ignore
    @Test
    public void Test3_addAndEmptyCard(){



        driver.findElement(By.xpath("//a[contains(@aria-label,'last page')]")).click();

       List<WebElement> items= driver.findElements(By.xpath("//div[@id='product_listing']/div/div/a[@class='block']"));

        driver.findElements(By.xpath("//div[@id='product_listing']/div/div/a[@class='block']")).get(items.size()-1).click();

        driver.findElement(By.id("buyButton")).click();


        driver.findElement(By.xpath("//a[@data-testid='cart-nav-link']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//button[@class='emptyCartButton btn btn-mini btn-ui pull-right']"))));
        driver.findElement(By.xpath("//button[@class='emptyCartButton btn btn-mini btn-ui pull-right']")).click();

         wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//*[@id='td']/div[11]/div/div/div/footer/button[1]"))));
        driver.findElement(By.xpath("//*[@id='td']/div[11]/div/div/div/footer/button[1]")).click();

        String emptyCard = driver.findElement(By.xpath("//*[@class='empty-cart__text']")).getText();


        Assert.assertEquals("Your cart is empty.",emptyCard);




    }


}