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
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeleniumJavaApplicationTests {

	private WebDriver driver;
	private static ExtentReports extentReports;

	@BeforeAll
	static void configurarReporte() {
		extentReports = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("Report/sparkReports.html");
		extentReports.attachReporter(spark);
	}

	@BeforeEach
	void iniciarNavegador() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://sso.centrodeelearning.com/auth/realms/master/protocol/openid-connect/auth?client_id=Moodle&response_type=code&redirect_uri=https%3A%2F%2Fcursos.utnba.centrodeelearning.com%2Fadmin%2Foauth2callback.php&state=%2Fauth%2Foauth2%2Flogin.php%3Fwantsurl%3Dhttps%253A%252F%252Fcursos.utnba.centrodeelearning.com%252F%26sesskey%3Dtw0etn1qIH%26id%3D1&scope=openid%20profile%20email");
	}

	@AfterEach
	void cerrarNavegador() {
		driver.quit();
	}

	@AfterAll
	static void cerrarReporte() {
		extentReports.flush(); //Una sola vez al final de todos los tests, asi no sobreescribe
	}

	@Test
	void testLoginPc() {
		//crear reporte en carpeta, html
		ExtentTest testLog = extentReports.createTest("Test Login UTN PC");
		//manejo de navegador
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("12345");
		driver.findElement(By.id("kc-login")).click();
		// input[@type='submit' and @value= "Enviar"]

		//driver.findElement(By.xpath("//button[contains(@class, 'btn-success') and text()='LOGIN']")).click();
		if (driver.getPageSource().contains("Usuario o contraseña incorrectos.")){
			System.out.println("Error al loguearse");
			testLog.log(Status.FAIL, "Error al intentar login");
		} else {
			System.out.println("Login exitoso");
			testLog.log(Status.PASS, "El Login resulto exitoso");
		}
	}


	@Test
	void testLoginPcCorrecto(){
		//crear reporte en carpeta, html
		ExtentTest testLogUTN = extentReports.createTest("Test Login Correcto UTN PC");
		//manejo de navegador
		driver.findElement(By.id("username")).sendKeys("mailtest@gmail.com");
		driver.findElement(By.id("password")).sendKeys("Capibara12*");
		driver.findElement(By.id("kc-login")).click();

		if (driver.getPageSource().contains("Usuario o contraseña incorrectos.")){
			System.out.println("Error al loguearse");
			testLogUTN.log(Status.FAIL, "Error al intentar login");
		} else {
			System.out.println("Login exitoso");
			testLogUTN.log(Status.PASS, "El Login resulto exitoso");
		}
	}

	@Test
	void testLoginNumerico(){
		//crear reporte en carpeta, html
		ExtentTest testLogNumerico = extentReports.createTest("Test Login numerico UTN PC");
		//manejo de navegador
		driver.findElement(By.id("username")).sendKeys("12345");
		driver.findElement(By.id("password")).sendKeys("12345");
		driver.findElement(By.id("kc-login")).click();

		if (driver.getPageSource().contains("Usuario o contraseña incorrectos.")){
			System.out.println("Error al loguearse con inputs numericos");
			testLogNumerico.log(Status.FAIL, "Error al intentar login con numeros");
		} else {
			System.out.println("Login exitoso");
			testLogNumerico.log(Status.PASS, "El Login resulto exitoso");
		}

	}
}
