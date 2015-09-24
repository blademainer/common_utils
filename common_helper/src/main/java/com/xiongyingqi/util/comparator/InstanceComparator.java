/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiongyingqi.util.comparator;

import com.xiongyingqi.util.Assert;

import java.util.Comparator;


public class InstanceComparator<T> implements Comparator<T> {

    private Class<?>[] instanceOrder;


    
    public InstanceComparator(Class<?>... instanceOrder) {
        Assert.notNull(instanceOrder, "InstanceOrder must not be null");
        this.instanceOrder = instanceOrder;
    }


    @Override
    public int compare(T o1, T o2) {
        int i1 = getOrder(o1);
        int i2 = getOrder(o2);
        return (i1 < i2 ? -1 : (i1 == i2 ? 0 : 1));
    }

    private int getOrder(T object) {
        if (object != null) {
            for (int i = 0; i < instanceOrder.length; i++) {
                if (instanceOrder[i].isInstance(object)) {
                    return i;
                }
            }
        }
        return instanceOrder.length;
    }

}
