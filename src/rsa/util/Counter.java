package rsa.util;

public class Counter implements Runnable{
	protected boolean mTrucking;
	protected int mCounter;
	
	public Counter() {
		mTrucking = true;
		mCounter = 0;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		while(mTrucking) {
			mCounter++;
			try {Thread.sleep(1); }
			catch(InterruptedException ie) {}
		}
	}
	
	public void stop() { mTrucking = false; }
	public int getCount() { return mCounter; }

}
