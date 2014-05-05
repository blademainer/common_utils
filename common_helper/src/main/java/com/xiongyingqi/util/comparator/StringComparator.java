package com.xiongyingqi.util.comparator;

import java.util.Comparator;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/4 0004.
 */
public class StringComparator implements Comparator<String> {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p/>
     * In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.<p>
     * <p/>
     * The implementor must ensure that <tt>sgn(compare(x, y)) ==
     * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>compare(x, y)</tt> must throw an exception if and only
     * if <tt>compare(y, x)</tt> throws an exception.)<p>
     * <p/>
     * The implementor must also ensure that the relation is transitive:
     * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
     * <tt>compare(x, z)&gt;0</tt>.<p>
     * <p/>
     * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
     * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
     * <tt>z</tt>.<p>
     * <p/>
     * It is generally the case, but <i>not</i> strictly required that
     * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */
    @Override
    public int compare(String o1, String o2) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException();
        }

        if (o1.getClass() != o2.getClass()) {
            throw new ClassCastException();
        }

        if (o1.length() == o2.length()) {
            if (o1.hashCode() < o2.hashCode()) {
                return -1;
            } else if (o1.hashCode() > o2.hashCode()) {
                return 1;
            }
            if (o1.hashCode() == o2.hashCode()) {
                return 0;
            }
        }
        char[] chars1 = o1.toCharArray();
        char[] chars2 = o2.toCharArray();
        int c = 0;

        int minLength = (chars1.length < chars2.length) ? chars1.length : chars2.length;
        for (int i = 0; i < minLength; i++) {
            if (chars1[i] != chars2[i]) {
                if (chars1[i] < chars2[i]) {
                    c = -1;
                } else {
                    c = 1;
                }
                break;
            }
        }

        if (c == 0) {
            c = (chars1.length < chars2.length) ? -1 : 1;
        }
        return c;
    }

    /**
     * Indicates whether some other object is &quot;equal to&quot; this
     * comparator.  This method must obey the general contract of
     * {@link Object#equals(Object)}.  Additionally, this method can return
     * <tt>true</tt> <i>only</i> if the specified object is also a comparator
     * and it imposes the same ordering as this comparator.  Thus,
     * <code>comp1.equals(comp2)</code> implies that <tt>sgn(comp1.compare(o1,
     * o2))==sgn(comp2.compare(o1, o2))</tt> for every object reference
     * <tt>o1</tt> and <tt>o2</tt>.<p>
     * <p/>
     * Note that it is <i>always</i> safe <i>not</i> to override
     * <tt>Object.equals(Object)</tt>.  However, overriding this method may,
     * in some cases, improve performance by allowing programs to determine
     * that two distinct comparators impose the same order.
     *
     * @param obj the reference object with which to compare.
     * @return <code>true</code> only if the specified object is also
     * a comparator and it imposes the same ordering as this
     * comparator.
     * @see Object#equals(Object)
     * @see Object#hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
