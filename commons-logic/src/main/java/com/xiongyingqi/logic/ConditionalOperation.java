package com.xiongyingqi.logic;

import com.xiongyingqi.logic.condition.And;
import com.xiongyingqi.logic.condition.Condition;
import com.xiongyingqi.logic.condition.Not;
import com.xiongyingqi.logic.condition.Or;

/**
 * 条件运算
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/10/22 0022.
 */
public class ConditionalOperation {
    Condition condition = new Condition() {
        public Condition condition(Condition condition) {
            return null;
        }
    };

    And       and       = new And();
    Or        or        = new Or();
    Not       not       = new Not();

    public ConditionalOperation and(Condition condition) {
        and.condition(condition);
        return this;
    }

    public ConditionalOperation or(Condition condition) {
        and.condition(condition);
        return this;
    }

    public ConditionalOperation not(Condition condition) {
        and.condition(condition);
        return this;
    }
}
