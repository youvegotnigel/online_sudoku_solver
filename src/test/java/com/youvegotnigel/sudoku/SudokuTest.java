package com.youvegotnigel.sudoku;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class SudokuTest extends SudokuSolver{

    private final int[][] board = new int[GRID_SIZE][GRID_SIZE];
    private final String AUT_URL = "https://nine.websudoku.com";

    private WebDriver driver;
    private final By table = By.id("puzzle_grid");
    private final By submit = By.xpath("//input[@name='submit']");
    private final By message = By.xpath("//span[@id='message']/font/b");

    @BeforeClass
    private void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(AUT_URL);

    }

    private String getXpath(int row, int col){

        return "//input[@id='f"+ col + row +"']";
    }

    private void readPuzzle(){

        for (int row=0; row<GRID_SIZE; row++){
            for(int col=0; col<GRID_SIZE; col++){

                String cellVal = driver.findElement(By.xpath(getXpath(row,col))).getAttribute("value");

                if(cellVal.equals("") || cellVal.equals(null)){
                    cellVal = "0";
                }
                board[row][col] = Integer.parseInt(cellVal);
            }
        }
    }

    @Test(priority=1)
    public void EasyPuzzleTest(){

        explicitWaitMethod(table);
        Assert.assertTrue(driver.findElement(table).isDisplayed());
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

        driver.findElement(submit).click();

        explicitWaitMethod(message);
        String result = driver.findElement(message).getText();

        Assert.assertTrue(result.contains("Congratulations! You solved this Sudoku"));
    }

    @Test(priority=2)
    public void MediumPuzzleTest(){

        driver.navigate().to(AUT_URL + "/?level=2");

        explicitWaitMethod(table);
        Assert.assertTrue(driver.findElement(table).isDisplayed());
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

        driver.findElement(submit).click();

        explicitWaitMethod(message);
        String result = driver.findElement(message).getText();

        Assert.assertTrue(result.contains("Congratulations! You solved this Sudoku"));
    }

    @Test(priority=3)
    public void HardPuzzleTest(){

        driver.navigate().to(AUT_URL + "/?level=3");

        explicitWaitMethod(table);
        Assert.assertTrue(driver.findElement(table).isDisplayed());
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

        driver.findElement(submit).click();

        explicitWaitMethod(message);
        String result = driver.findElement(message).getText();

        Assert.assertTrue(result.contains("Congratulations! You solved this Sudoku"));
    }

    @Test(priority=4)
    public void EvilPuzzleTest(){

        driver.navigate().to(AUT_URL + "/?level=4");

        explicitWaitMethod(table);
        Assert.assertTrue(driver.findElement(table).isDisplayed());
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

        driver.findElement(submit).click();

        explicitWaitMethod(message);
        String result = driver.findElement(message).getText();

        Assert.assertTrue(result.contains("Congratulations! You solved this Sudoku"));
    }

    private void writeToPuzzle(int[][] board){

        for (int row=0; row<GRID_SIZE; row++){
            for(int col=0; col<GRID_SIZE; col++){

                int solution = board[row][col];
                WebElement element = driver.findElement(By.xpath(getXpath(row,col)));

                if(!isAttributePresent(element,"readonly value")){
                    element.sendKeys(String.valueOf(solution));
                }
            }
        }
    }

    @AfterClass
    private void tearDown() {
        driver.quit();
    }

    private void explicitWaitMethod(By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    private boolean isAttributePresent(WebElement element, String attribute) {
        try {
            String value = element.getAttribute(attribute);
            if (value != null){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
