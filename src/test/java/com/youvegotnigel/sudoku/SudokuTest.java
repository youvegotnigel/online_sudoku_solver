package com.youvegotnigel.sudoku;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SudokuTest extends SudokuSolver{

    private final int[][] board = new int[GRID_SIZE][GRID_SIZE];
    private static final int BROWSER_WIDTH = 250;
    private static final int BROWSER_HEIGHT = 800;
    private static int HEIGHT;
    private static int WIDTH;
    private final String AUT_URL = "https://nine.websudoku.com";
    private final By table = By.id("puzzle_grid");
    private final By submit = By.xpath("//input[@name='submit']");
    private final By message = By.xpath("//span[@id='message']/font/b");

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod
    private void setup() {
        WebDriverManager.chromedriver().setup();
        driver.set(ThreadGuard.protect(new ChromeDriver()));
        Dimension initial_size = getDriver().manage().window().getSize();
        HEIGHT = initial_size.getHeight();
        WIDTH = initial_size.getWidth();
        System.out.println("height = " + HEIGHT);
        System.out.println("width = " + WIDTH);
    }

    public static WebDriver getDriver(){
        return driver.get();
    }

    private String getXpath(int row, int col){

        return "//input[@id='f"+ col + row +"']";
    }

    private void readPuzzle(){

        for (int row=0; row<GRID_SIZE; row++){
            for(int col=0; col<GRID_SIZE; col++){

                String cellVal = getDriver().findElement(By.xpath(getXpath(row,col))).getAttribute("value");

                if(cellVal.equals("") || cellVal.equals(null)){
                    cellVal = "0";
                }
                board[row][col] = Integer.parseInt(cellVal);
            }
        }
    }

    @Test(priority=1)
    public void EasyPuzzleTest(){

        getDriver().manage().window().setSize(new Dimension(BROWSER_WIDTH, BROWSER_HEIGHT));
        getDriver().manage().window().setPosition(new Point(0, 0));
        getDriver().get(AUT_URL);

        explicitWaitMethod(table);
        Assert.assertTrue(getDriver().findElement(table).isDisplayed());
        readPuzzle();

        System.out.println("###################################################");
        System.out.println("PUZZLE LEVEL ::: " + PuzzleLevels.EASY);
        System.out.println("After reading the puzzle");
        System.out.println("--------------------------\n");
        printBoard(board);

        if(solveBoard(board)){
            System.out.println("Solved successfully! \n");
        }else {
            System.out.println("Unsolved board :( \n");
        }

        System.out.println("PUZZLE LEVEL ::: " + PuzzleLevels.EASY);
        System.out.println("After solving the puzzle");
        System.out.println("--------------------------\n");
        printBoard(board);

        writeToPuzzle(board);

        getDriver().findElement(submit).click();

        explicitWaitMethod(message);
        String result = getDriver().findElement(message).getText();

        Assert.assertTrue(result.contains("Congratulations! You solved this Sudoku"));
    }

    @Test(priority=2)
    public void MediumPuzzleTest(){

        getDriver().manage().window().setSize(new Dimension(BROWSER_WIDTH, BROWSER_HEIGHT));
        getDriver().manage().window().setPosition(new Point((int) (WIDTH*0.25), 0));
        getDriver().get(AUT_URL + "/?level=2");

        explicitWaitMethod(table);
        Assert.assertTrue(getDriver().findElement(table).isDisplayed());
        readPuzzle();

        System.out.println("###################################################");
        System.out.println("Puzzle Level ::: " + PuzzleLevels.MEDIUM);
        System.out.println("After reading the puzzle");
        System.out.println("--------------------------\n");
        printBoard(board);

        if(solveBoard(board)){
            System.out.println("Solved successfully! \n");
        }else {
            System.out.println("Unsolved board :( \n");
        }

        System.out.println("PUZZLE LEVEL ::: " + PuzzleLevels.MEDIUM);
        System.out.println("After solving the puzzle");
        System.out.println("--------------------------\n");
        printBoard(board);

        writeToPuzzle(board);

        getDriver().findElement(submit).click();

        explicitWaitMethod(message);
        String result = getDriver().findElement(message).getText();

        Assert.assertTrue(result.contains("Congratulations! You solved this Sudoku"));
    }

    @Test(priority=3)
    public void HardPuzzleTest(){

        getDriver().manage().window().setSize(new Dimension(BROWSER_WIDTH, BROWSER_HEIGHT));
        getDriver().manage().window().setPosition(new Point((int) (WIDTH*0.5), 0));
        getDriver().get(AUT_URL + "/?level=3");

        explicitWaitMethod(table);
        Assert.assertTrue(getDriver().findElement(table).isDisplayed());
        readPuzzle();


        System.out.println("###################################################");
        System.out.println("PUZZLE LEVEL ::: " + PuzzleLevels.HARD);
        System.out.println("After reading the puzzle");
        System.out.println("--------------------------\n");
        printBoard(board);

        if(solveBoard(board)){
            System.out.println("Solved successfully! \n");
        }else {
            System.out.println("Unsolved board :( \n");
        }

        System.out.println("PUZZLE LEVEL ::: " + PuzzleLevels.HARD);
        System.out.println("After solving the puzzle");
        System.out.println("--------------------------\n");
        printBoard(board);

        writeToPuzzle(board);

        getDriver().findElement(submit).click();

        explicitWaitMethod(message);
        String result = getDriver().findElement(message).getText();

        Assert.assertTrue(result.contains("Congratulations! You solved this Sudoku"));

    }

    @Test(priority=4)
    public void EvilPuzzleTest(){

        getDriver().manage().window().setSize(new Dimension(BROWSER_WIDTH, BROWSER_HEIGHT));
        getDriver().manage().window().setPosition(new Point((int) (WIDTH*0.75), 0));
        getDriver().get(AUT_URL + "/?level=4");

        explicitWaitMethod(table);
        Assert.assertTrue(getDriver().findElement(table).isDisplayed());
        readPuzzle();

        System.out.println("###################################################");
        System.out.println("PUZZLE LEVEL ::: " + PuzzleLevels.EVIL);
        System.out.println("After reading the puzzle");
        System.out.println("--------------------------\n");
        printBoard(board);

        if(solveBoard(board)){
            System.out.println("Solved successfully! \n");
        }else {
            System.out.println("Unsolved board :( \n");
        }

        System.out.println("PUZZLE LEVEL ::: " + PuzzleLevels.EVIL);
        System.out.println("After solving the puzzle");
        System.out.println("--------------------------\n");
        printBoard(board);

        writeToPuzzle(board);

        getDriver().findElement(submit).click();

        explicitWaitMethod(message);
        String result = getDriver().findElement(message).getText();

        Assert.assertTrue(result.contains("Congratulations! You solved this Sudoku"));
    }

    private void writeToPuzzle(int[][] board){

        for (int row=0; row<GRID_SIZE; row++){
            for(int col=0; col<GRID_SIZE; col++){

                int solution = board[row][col];
                WebElement element = getDriver().findElement(By.xpath(getXpath(row,col)));

                if(!isAttributePresent(element,"readonly value")){
                    element.sendKeys(String.valueOf(solution));
                }
            }
        }
    }

    @AfterMethod
    private void tearDown() {
        getDriver().quit();
        driver.remove();
    }

    private void explicitWaitMethod(By element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    private boolean isAttributePresent(WebElement element, String attribute) {
        try {
            String value   = element.getAttribute(attribute);
            if (value != null){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
