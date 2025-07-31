package com.automat.seleniumJava;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTexto {

    private WebDriver driver;
    private static ExtentReports extentReports;

    @BeforeAll
    static void configurarReporte() {
        extentReports = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("Report/webReports.html");
        extentReports.attachReporter(spark);
    }

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    static void cerrarReporte() {
        extentReports.flush();
    }

    @Test
    void verificarTextoEnWeb(){
        driver.get("https://profile-psi-topaz.vercel.app");
        String pageSource = driver.getPageSource();

        ExtentTest testBusquedaTexto = extentReports.createTest("Test búsqueda de texto 'About Me'");

        if (pageSource.contains("About Me")){
            System.out.println("Se ha encontrado");
            testBusquedaTexto.log(Status.PASS, "El texto 'About Me' fue encontrado exitosamente.");
        } else {
            System.out.println("No se ha encontrado el texto");
            testBusquedaTexto.log(Status.FAIL, "El texto 'About Me' no fue encontrado en la página.");
        }

        // Validación final (esto lanza un error si falla)
        assertTrue(pageSource.contains("About Me"), "El texto 'About Me' no está presente en la página.");
    }


    @Test
    void verificarBoton() {
        driver.get("https://profile-psi-topaz.vercel.app");

        ExtentTest testButtonAGit = extentReports.createTest("Test botón redirecciona a GitHub");
        driver.get("https://profile-psi-topaz.vercel.app");

        ExtentTest testBusquedaTexto = extentReports.createTest("Test búsqueda de texto 'About Me'");

        if (driver.getPageSource().contains("About Me")) {
            testBusquedaTexto.log(Status.PASS, "El texto 'About Me' fue encontrado exitosamente.");
        } else {
            testBusquedaTexto.log(Status.FAIL, "El texto 'About Me' no fue encontrado en la página.");
            Assertions.fail("Texto no encontrado");
        }

        driver.findElement(By.linkText("Watch docs")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Nueva pestaña antes de url
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "https://github.com/ceurrutia/delivery/tree/main/docs";

        System.out.println("URL actual: " + currentUrl);

        try {
            assertEquals(expectedUrl, currentUrl, "La url del click no es la esperada");
            testButtonAGit.log(Status.PASS, "Redireccionó correctamente a: " + currentUrl);
        } catch (AssertionError e) {
            testButtonAGit.log(Status.FAIL, "Ha fallado: " + e.getMessage());
            throw e;
        }
    }
}

