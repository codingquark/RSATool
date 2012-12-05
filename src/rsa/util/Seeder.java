package rsa.util;

import java.awt.AWTEventMulticaster;
import java.awt.event.*;

public class Seeder implements KeyListener{
	
	protected byte[] mSeed;
	protected int mBitIndex;
	protected boolean mDone;
	protected char mLastKeyChar;
	protected ActionListener mListenerChain;
	protected Counter counter;

	public Seeder( int seedBytes ) { reset(seedBytes); }
	
	public void reset( int seedBytes) {
		mSeed = new byte[seedBytes];
		mBitIndex = seedBytes * 8 - 1;
		mDone = false;
		mLastKeyChar = '\0';
		mListenerChain = null;
		counter = new Counter();
	}
	
	public byte[] getSeed() { return mSeed; }
	
	public int getBitLength() { return mSeed.length * 8; }
	
	public int getCurrentBitIndex() {
		return mSeed.length * 8 - 1 - mBitIndex;
	}
	
	public void addActionListener(ActionListener al) {
		mListenerChain = AWTEventMulticaster.add(mListenerChain, al);
	}
	
	public void removeActionListener(ActionListener al) {
		mListenerChain = AWTEventMulticaster.remove(mListenerChain, al);
	}

	@Override
	public void keyPressed(KeyEvent ke) {}

	@Override
	public void keyReleased(KeyEvent ke) {}

	@Override
	public void keyTyped(KeyEvent ke) {
		char keyChar = ke.getKeyChar();
		if(keyChar != mLastKeyChar)
			grabTimeBit();
		mLastKeyChar = keyChar;
		
	}
	
	protected void grabTimeBit() {
		if(mDone) return;
		int t = counter.getCount();
		int bit = t & 0x0001;
		
		if(bit != 0) {
			int seedIndex = mBitIndex / 8;
			int shiftIndex = mBitIndex % 8;
			mSeed[ seedIndex ] |= ( bit << shiftIndex);
		}
		
		mBitIndex--;
		
		if(mBitIndex < 0) {
			counter.stop();
			mBitIndex = 0;
			mDone = true;
		}
		
		if(mListenerChain != null) {
			mListenerChain.actionPerformed(new ActionEvent(this, 0, "Your seed is ready."));
		}
	}

}
