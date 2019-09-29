package br.com.lenka.mail;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import br.com.lenka.comum.FuncoesComuns;

public class EmailManuseio {
	
	public void BaixarEmail() {
		FuncoesComuns funcoes = new FuncoesComuns();
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");

			funcoes.GrvarLOG("conectando e-mail: " + funcoes.LerIni("email"));
			store.connect(funcoes.LerIni("host"), funcoes.LerIni("email"),
					funcoes.LerIni("senhaMail"));

			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			Message messages[] = inbox.getMessages();
			for (Message message : messages) {
				funcoes.GrvarLOG(funcoes.linha);
				printMessage(message);
			}

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			funcoes.GrvarLOG("Ocorreu um erro: " + e.getMessage());
			System.exit(1);
		} catch (MessagingException e) {
			e.printStackTrace();
			funcoes.GrvarLOG("Ocorreu um erro: " + e.getMessage());
			System.exit(2);
		}
	}

	public static void printMessage(Message message) {
		FuncoesComuns funcoes = new FuncoesComuns();
		try {

			String from = ((InternetAddress) message.getFrom()[0])
					.getPersonal();
			if (from == null)
				from = ((InternetAddress) message.getFrom()[0]).getAddress();

			funcoes.GrvarLOG("DE: " + from);
			funcoes.GrvarLOG("ASSUNTO: " + message.getSubject());

			Part messagePart = message;
			Object content = messagePart.getContent();

			if (content instanceof Multipart) {
				messagePart = ((Multipart) content).getBodyPart(0);
				if (funcoes.MostrarConteudoEmail) {
					funcoes.GrvarLOG("CONTEÚDO: "
							+ messagePart.getContent().toString());
				}
			}

			String contentType = messagePart.getContentType();

			funcoes.GrvarLOG("CONTENT:" + contentType);
			if (contentType.startsWith("text/plain")
					|| contentType.startsWith("text/html")) {
				funcoes.GrvarLOG("Lendo e-mail sem anexo...");
				InputStream is = messagePart.getInputStream();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String thisLine = reader.readLine();
				while (thisLine != null) {
					thisLine = reader.readLine();
				}
			}

			else {
				funcoes.GrvarLOG("Fazendo download de anexos...");
				byte[] buf = new byte[4096];
				// String caminhoBase = System.getProperty("user.dir") + "/";
				Multipart multi = (Multipart) content;
				for (int i = 0; i < multi.getCount(); i++) {
					String nomeDoArquivo = multi.getBodyPart(i).getFileName();
					if (nomeDoArquivo != null) {
						InputStream is = multi.getBodyPart(i).getInputStream();
						FileOutputStream fos = new FileOutputStream(
								funcoes.PathSistema + nomeDoArquivo);
						int bytesRead;
						while ((bytesRead = is.read(buf)) != -1) {
							fos.write(buf, 0, bytesRead);
						}
						fos.close();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
