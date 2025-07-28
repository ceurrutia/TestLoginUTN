# Selenium Java Test - Login UTN

Este proyecto contiene un test automatizado con Selenium WebDriver y JUnit para validar el login en la plataforma UTN usando credenciales de prueba.

## Requisitos previos

Antes de ejecutar este proyecto instalar:

1. Java 17 o superior
2. Maven
3. Chrome
4. Git
5. Conexión a Internet

## Cloná el repositorio

1. git clone https://github.com/ceurrutia/TestLoginUTN.git
2. cd nombre-de-tu-repositorio

## Reporte

Al finalizar la ejecución, se generará un archivo de reporte en: Report/sparkReports.html

## Dependencias

* Selenium WebDriver 
* WebDriverManager 
* JUnit 5 
* ExtentReports 
* Spring Boot (para el contexto de prueba)

## Notas

* Las credenciales de prueba están hardcodeadas como admin / 12345. 
* Si el login falla, el test lo reportará como fallido en el HTML. 
* La URL de prueba puede requerir conectividad a VPN o acceso autorizado.


