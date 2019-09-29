package br.com.lenka.comum;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class FuncoesComuns {

	private Properties config = new Properties();
	public String PathSistema = "C:\\ProjetosWeb\\JavaMail\\PrEmail\\Anexos\\";
	public String PathIniFile = "C:\\ProjetosWeb\\JavaMail\\PrEmail\\Anexos\\config.ini";
	public String linha = "************************************************************************************************************************************************************************************************************************************";
	public boolean MostrarConteudoEmail = false;
	private boolean SobrescreverArquivoLog = false;

	public void GrvarLOG(String texto) {
		try {			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(); 
			FileWriter arq = new FileWriter(PathSistema + "LOG_"+ formatter.format(date) +".txt", !SobrescreverArquivoLog);
			PrintWriter gravarArq = new PrintWriter(arq);
			gravarArq.println(texto);
			arq.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String LerIni(String Tag) {
		try {
			config.load(new FileInputStream(PathIniFile));
			return config.getProperty(Tag);
		} catch (IOException e) {
			GrvarLOG("Ocorreu um erro ao ler arquivo ini: " + PathIniFile
					+ "\n" + e.getMessage());
			return "";
		}
	}

}
