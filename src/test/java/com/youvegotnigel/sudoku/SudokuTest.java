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

    private int[][] board = new int[GRID_SIZE][GRID_SIZE];

    private WebDriver driver;
    private By table = By.id("puzzle_grid");
    private By submit = By.xpath("//input[@name='submit']");
    private By message = By.xpath("//span[@id='message']/font/b");

    @BeforeClass
    private void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://nine.websudoku.com/");

    }

    private String getXpath(int row, int col){

        String xpath = "//input[@id='f"+ col + row +"']";
        //System.out.println("Finding xpath for ::: " +xpath);
        return xpath;
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

    @Test
    public void PuzzleTest(){

        explicitWaitMethod(table);
        Assert.assertTrue(driver.findElement(table).isDisplayed());
        readPuzzle();

        System.out.println("After reading the puzzle");
        System.out.println("--------------------------\n");
        printBoard(board);

        if(solveBoard(board)){
            System.out.println("Solved successfully! \n");
        }else {
            System.out.println("Unsolved board :( \n");
        }

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
        Boolean result = false;
        try {
            String value = element.getAttribute(attribute);
            if (value != null){
                result = true;
            }
        } catch (Exception e) {}

        return result;
    }

}
