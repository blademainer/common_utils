package com.xiongyingqi.logic.condition;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by qi<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2015-06-10 19:54.
 */
public class Not implements Condition {
    public static final Collection<Condition> CONDITIONS = new HashSet<Condition>();

    @Override
    public Condition condition(Condition condition) {
        CONDITIONS.add(condition);
        return this;
    }
}
