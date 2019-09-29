package br.com.lenka.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableTester {

	public static void main(String[] args) throws InterruptedException {
		boolean continua = true;
		while (continua) {
			ExecutorService threadExecutor = Executors.newFixedThreadPool(3);
			PrintTask task1 = new PrintTask();
			threadExecutor.execute(task1);
			threadExecutor.shutdown();
			Thread.sleep(30000);
		}

	}

}
