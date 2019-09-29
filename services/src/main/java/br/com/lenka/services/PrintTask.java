package br.com.lenka.services;

public class PrintTask implements Runnable {

	public PrintTask() {
		System.out.println("serviço iniciado...");
	}

	//@Override
	public void run() {
		try {
			System.out.println("executando serviço");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}