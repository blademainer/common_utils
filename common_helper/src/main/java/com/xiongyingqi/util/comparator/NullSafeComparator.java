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


public class NullSafeComparator<T> implements Comparator<T> {

    
    @SuppressWarnings("rawtypes")
    public static final NullSafeComparator NULLS_LOW = new NullSafeComparator<Object>(true);

    
    @SuppressWarnings("rawtypes")
    public static final NullSafeComparator NULLS_HIGH = new NullSafeComparator<Object>(false);

    private final Comparator<T> nonNullComparator;

    private final boolean nullsLow;


    
    @SuppressWarnings({"unchecked", "rawtypes"})
    private NullSafeComparator(boolean nullsLow) {
        this.nonNullComparator = new ComparableComparator();
        this.nullsLow = nullsLow;
    }

    
    public NullSafeComparator(Comparator<T> comparator, boolean nullsLow) {
        Assert.notNull(comparator, "The non-null comparator is required");
        this.nonNullComparator = comparator;
        this.nullsLow = nullsLow;
    }


    @Override
    public int compare(T o1, T o2) {
        if (o1 == o2) {
            return 0;
        }
        if (o1 == null) {
            return (this.nullsLow ? -1 : 1);
        }
        if (o2 == null) {
            return (this.nullsLow ? 1 : -1);
        }
        return this.nonNullComparator.compare(o1, o2);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NullSafeComparator)) {
            return false;
        }
        NullSafeComparator<T> other = (NullSafeComparator<T>) obj;
        return (this.nonNullComparator.equals(other.nonNullComparator) && this.nullsLow == other.nullsLow);
    }

    @Override
    public int hashCode() {
        return (this.nullsLow ? -1 : 1) * this.nonNullComparator.hashCode();
    }

    @Override
    public String toString() {
        return "NullSafeComparator: non-null comparator [" + this.nonNullComparator + "]; " +
                (this.nullsLow ? "nulls low" : "nulls high");
    }

}
