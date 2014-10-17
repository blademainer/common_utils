/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/10/17 0017.
 */
public class LinkList<T> {
    class Entry {
        /**
         * 当前节点的数据
         */
        T data;
        /**
         * 下一个节点
         */
        Entry next;
    }

    private Entry header;// 头部标志
    private Entry current;// 当前元素
    private int size;
    private int index;

    LinkList() {
        Entry entry = new Entry();
        header = entry;
        current = entry;
        current.next = entry;
        size = 0;
        index = -1;
    }

    /**
     * 插入到列表最后一个元素后
     *
     * @param t
     */
    public void add(T t) {
        Entry entry = new Entry();
        current.next = entry;
        entry.data = t;
        entry.next = header;
        current = entry;
        size++;
        index++;
    }

    /**
     * 插入到第一个元素
     * @param t
     */
    public void addAfterHeader(T t) {
        Entry entry = new Entry();
        entry.next = header.next;
        header.next = entry;
        entry.data = t;
        current = entry;
    }

    /**
     * 答应当前元素
     */
    public void print() {
        Entry next = header.next;
        while (next != header) {
            System.out.print("->" + next.data);
            next = next.next;
        }
        System.out.println();
    }


    public static void main(String[] args) {
        LinkList<Integer> list = new LinkList<Integer>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        list.print();

        LinkList<Integer> list2= new LinkList<Integer>();
        for (int i = 0; i < 100; i++) {
            list2.addAfterHeader(i);
        }
        list2.print();
    }


}
