import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by qi<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 15-4-7.
 */
public class AtomicTest {
    private static int intIndex;
    private static AtomicInteger atomicIntegerIndex = new AtomicInteger();
    private static long longIndex;
    private static AtomicLong atomicLongIndex = new AtomicLong();
    static final int MIN = 0;
    static final int MAX = 2000000000;

    class IntRunner extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 99999; i++) {
                if (intIndex != MIN && intIndex != MAX) {
                    throw new RuntimeException("error!");
                }
                intIndex = MAX;
                intIndex = MIN;
            }
        }
    }

    class AtomicIntRunner extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 99999; i++) {
                if (atomicIntegerIndex.intValue() != MIN && atomicIntegerIndex.intValue() != MAX) {
                    throw new RuntimeException("error!");
                }
                atomicIntegerIndex.compareAndSet(MAX, MAX);
                atomicIntegerIndex.compareAndSet(MIN, MIN);
            }
        }
    }

    class LongRunner extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 99999; i++) {
                if (longIndex != MIN && longIndex != MAX) {
                    throw new RuntimeException("error!");
                }
                longIndex = MAX;
                longIndex = MIN;
            }
        }
    }

    class AtomicLongRunner extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 99999; i++) {
                if (atomicLongIndex.longValue() != MIN && atomicLongIndex.longValue() != MAX) {
                    throw new RuntimeException("error!");
                }
                atomicLongIndex.compareAndSet(MAX, MAX);
                atomicLongIndex.compareAndSet(MIN, MIN);
            }
        }
    }

    @Test
    public void testIntRunner() throws Exception {
        for (int i = 0; i < 1000; i++) {
            IntRunner runner = new IntRunner();
            runner.start();
        }
    }

    @Test
    public void testLongRunner() throws Exception {
        for (int i = 0; i < 1000; i++) {
            LongRunner runner = new LongRunner();
            runner.start();
        }

    }

    @Test
    public void testAtomicIntRunner() throws Exception {
        for (int i = 0; i < 1000; i++) {
            AtomicIntRunner runner = new AtomicIntRunner();
            runner.start();
        }

    }

    @Test
    public void testAtomicLongRunner() throws Exception {
        for (int i = 0; i < 1000; i++) {
            AtomicLongRunner runner = new AtomicLongRunner();
            runner.start();
        }

    }
}
