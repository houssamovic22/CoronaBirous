package com.amrou.go;

public class Timer implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		}
		catch(Exception e) {
			e.printStackTrace();
			new Thread(this).start();
			System.exit(0);
		}
		
	}

}
