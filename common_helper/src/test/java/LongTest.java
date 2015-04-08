/**
 * Created by qi<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 15-4-7.
 */
public class LongTest {
    public static void main(String[] args) throws Exception {
        Run r = new Run();
        for (int i = 0; i < 10; i++)
            new Thread(r).start();
    }
}

class Run implements Runnable {

    static final int MIN = 0;

    static final int MAX = 2000000000;

    // 这里加不加volatile都是一样的结果。
    volatile int num = MIN;

    @Override
    public void run() {
        // 变量num就始终就两个取值，MIN和MAX，在这两个值之间来回赋值。
        // 如果发现num即不是MIN、也不是MAX，
        // 就会走到System.err输出这行代码，
        // 那么就说明有某个线程读取到了一个错误的值。
        for (int i = 0; i < 999999; i++) {
            if (num != MIN && num != MAX) {
                System.err.println("!!! ");
                return;
            }
            num = MAX;
            num = MIN;

        }
    }
}
