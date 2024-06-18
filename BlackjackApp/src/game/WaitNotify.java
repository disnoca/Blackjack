package game;

public class WaitNotify {

	public synchronized void doWait() throws InterruptedException {
		wait();
	}
	
	public synchronized void doNotify() {
		notify();
	}
	
}
