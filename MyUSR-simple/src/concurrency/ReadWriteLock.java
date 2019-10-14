package concurrency;

import java.util.HashMap;
import java.util.Map;

public class ReadWriteLock {
	// read access if no one is writing
	// write access if no one is reading or writing

	private Map<Thread, Integer> readingThreads = new HashMap<>();

	private int writeAccesses = 0;
	private int writeRequests = 0;
	private Thread writingThread = null;

	synchronized void lockRead() throws InterruptedException {
		Thread callingThread = Thread.currentThread();
		while (!canGrantReadAccess(callingThread)) {
			wait();
		}

		readingThreads.put(callingThread,
				(getReadAccessCount(callingThread) + 1));
	}

	private boolean canGrantReadAccess(Thread callingThread) {
		if (isWriter(callingThread))
			return true;
		if (hasWriter())
			return false;
		if (isReader(callingThread))
			return true;
		return !hasWriteRequests();
	}

	synchronized void lockWrite() throws InterruptedException {
		writeRequests++;
		Thread callingThread = Thread.currentThread();
		while (!canGrantWriteAccess(callingThread)) {
			wait();
		}
		writeRequests--;
		writeAccesses++;
		writingThread = callingThread;
	}

	synchronized void unlockWrite() {
		if (!isWriter(Thread.currentThread())) {
			throw new IllegalMonitorStateException(
					"Thread does not hols the write lock");
		}
		writeAccesses--;
		if (writeAccesses == 0) {
			writingThread = null;
		}
		notifyAll();
	}

	synchronized void unlock() {
		Thread callingThread = Thread.currentThread();
		if (!(isReader(callingThread)) || isWriter(callingThread)) {
			throw new IllegalMonitorStateException(
					"Thread does not hold any lock");
		}

		readingThreads.remove(callingThread);
		writingThread = null;
		notifyAll();
	}

	private boolean canGrantWriteAccess(Thread callingThread) {
		if (isOnlyReader(callingThread))
			return true;
		if (hasReaders())
			return false;
		if (writingThread == null)
			return true;
		return isWriter(callingThread);
	}

	private int getReadAccessCount(Thread callingThread) {
		Integer accessCount = readingThreads.get(callingThread);
		if (accessCount == null)
			return 0;
		return accessCount;
	}

	synchronized void unlockRead() {
		Thread callingThread = Thread.currentThread();
		if (!isReader(callingThread)) {
			throw new IllegalMonitorStateException(
					"Thread does not hold a read lock");
		}
		int accessCount = getReadAccessCount(callingThread);
		if (accessCount == 1) {
			readingThreads.remove(callingThread);
		} else {
			readingThreads.put(callingThread, (accessCount - 1));
		}
		notifyAll();
	}

	private boolean hasReaders() {
		return readingThreads.size() > 0;
	}

	private boolean isReader(Thread callingThread) {
		return readingThreads.get(callingThread) != null;
	}

	private boolean isOnlyReader(Thread callingThreadd) {
		return readingThreads.size() == 1
				&& readingThreads.get(callingThreadd) != null;
	}
	
	private boolean hasWriter() {
		return writingThread != null;
	}
	
	private boolean isWriter(Thread callingThread) {
		return writingThread == callingThread;
	}
	
	private boolean hasWriteRequests() {
		return this.writeRequests>0;
	}
}
