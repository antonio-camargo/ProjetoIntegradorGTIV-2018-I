package aula;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Patrimonio {

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver", "C:\\tony\\geckodriver.exe");
	WebDriver navegador = new FirefoxDriver();
	navegador.get("http://elias.projetointegrador.com/patrimonio/benspatrimoniais-excluir-post.php?num=24");
	navegador.get("http://elias.projetointegrador.com");
	navegador.findElement(By.linkText("Sistema de controle patrimonial")).click();
	navegador.findElement(By.linkText("Bens Patrimoniais")).click();
	navegador.findElement(By.linkText("Adicionar um Novo Bem Patrimonial")).click();
	
	navegador.findElement(By.name("txtNum")).sendKeys("24");
	navegador.findElement(By.name("txtDtAq")).sendKeys("2016-05-15");
	navegador.findElement(By.name("txtDesc")).sendKeys("Ar condicionado Split");
	navegador.findElement(By.name("txtVlrCompra")).sendKeys("3000");
	WebElement departamento = navegador.findElement(By.name("txtDpto"));
	departamento.sendKeys("Gerencia");
	WebElement sala = navegador.findElement(By.name("txtSala"));
	sala.sendKeys("311");
	WebElement botaoSalvar = navegador.findElement(By.id("btnSalvar"));
	botaoSalvar.click();
	
	String pagina = navegador.getPageSource();
	if(pagina.contains("24") & pagina.contains("Ar condicionado Split")) {
		System.out.println("Teste Com sucesso");
	
	}else {
		System.out.println("Teste sem sucesso");
		
	}
	navegador.close();
	}
}
