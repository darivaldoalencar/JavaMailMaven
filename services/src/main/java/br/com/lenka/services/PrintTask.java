package br.com.lenka.services;

public class PrintTask implements Runnable {

	public PrintTask() {
		System.out.println("servi�o iniciado...");
	}

	//@Override
	public void run() {
		try {
			System.out.println("executando servi�o");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}