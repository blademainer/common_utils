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

/**
 * Compares objects based on an arbitrary class order. Allows objects to be sorted based
 * on the types of class that they inherit, for example: this comparator can be used to
 * sort a list {@code Number}s such that {@code Long}s occur before {@code Integer}s.
 * <p/>
 * <p>Only the specified {@code instanceOrder} classes are considered during comparison.
 * If two objects are both instances of the ordered type this comparator will return a
 * {@code 0}. Consider combining with a {@link org.springframework.util.comparator.CompoundComparator} if additional sorting
 * is required.
 *
 * @param <T> The type of objects being compared
 * @author Phillip Webb
 * @see org.springframework.util.comparator.CompoundComparator
 * @since 3.2
 */
public class InstanceComparator<T> implements Comparator<T> {

    private Class<?>[] instanceOrder;


    /**
     * Create a new {@link com.xiongyingqi.util.comparator.InstanceComparator} instance.
     *
     * @param instanceOrder the ordered list of classes that should be used when comparing
     *                      objects. Classes earlier in the list will be be given a higher priority.
     */
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