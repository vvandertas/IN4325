package com.vdtas.selenium;

import static io.restassured.RestAssured.get;
import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testhelpers.SeleniumTest;

import java.util.List;

/**
 * @author jooby generator
 */
public class AppTest extends SeleniumTest {

    @Test
    public void searchResults() {
        // Load landing helper
        driver.get(HOST);

        WebElement queryField = driver.findElement(By.name("query"));
        queryField.sendKeys("Test search");
        queryField.submit();

        // Wait for the ajax call to be done
        helper.waitForAjax();

        assertFalse(driver.findElement(By.id("noresults")).isDisplayed());

        // Validate paging is present twice
        List<WebElement> paging = driver.findElements(By.className("paging"));
        assertEquals(2, paging.size());
        paging.forEach(p -> assertTrue(p.isDisplayed()));

        // Validate we have 20 results displayed
        List<WebElement> webPages = driver.findElements(By.className("webPages"));
        assertEquals("Expecting 20 search results to be displayed",20, webPages.size());
        webPages.forEach(w -> assertTrue(w.isDisplayed()));
    }


    /**
     * After a first search request skip task.
     *
     * Expecting next hint to be displayed and the search results to be removed.
     */
    @Test
    public void skipTask() {
        // Load landing helper
        driver.get(HOST);

        WebElement queryField = driver.findElement(By.name("query"));
        queryField.sendKeys("Test search");
        queryField.submit();

        // Wait for the ajax call to be done
        helper.waitForAjax();

        // Skip the task
        driver.findElement(By.id("skip")).click();

        assertTrue(driver.findElement(By.id("noresults")).isDisplayed());

        // Paging should not be shown
        List<WebElement> paging = driver.findElements(By.className("paging"));
        assertEquals(2, paging.size());
        paging.forEach(p -> assertFalse(p.isDisplayed()));

        // There should be no search results
        List<WebElement> webPages = driver.findElements(By.className("webPages"));
        webPages.forEach(w -> assertFalse(w.isDisplayed()));

        // TODO: Make sure the question and hint are updated

    }

}
