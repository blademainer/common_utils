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

import java.io.Serializable;
import java.util.Comparator;


@SuppressWarnings("serial")
public class InvertibleComparator<T> implements Comparator<T>, Serializable {

    private final Comparator<T> comparator;

    private boolean ascending = true;


    
    public InvertibleComparator(Comparator<T> comparator) {
        Assert.notNull(comparator, "Comparator must not be null");
        this.comparator = comparator;
    }

    
    public InvertibleComparator(Comparator<T> comparator, boolean ascending) {
        Assert.notNull(comparator, "Comparator must not be null");
        this.comparator = comparator;
        setAscending(ascending);
    }


    
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    
    public boolean isAscending() {
        return this.ascending;
    }

    
    public void invertOrder() {
        this.ascending = !this.ascending;
    }


    @Override
    public int compare(T o1, T o2) {
        int result = this.comparator.compare(o1, o2);
        if (result != 0) {
            // Invert the order if it is a reverse sort.
            if (!this.ascending) {
                if (Integer.MIN_VALUE == result) {
                    result = Integer.MAX_VALUE;
                } else {
                    result *= -1;
                }
            }
            return result;
        }
        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InvertibleComparator)) {
            return false;
        }
        InvertibleComparator<T> other = (InvertibleComparator<T>) obj;
        return (this.comparator.equals(other.comparator) && this.ascending == other.ascending);
    }

    @Override
    public int hashCode() {
        return this.comparator.hashCode();
    }

    @Override
    public String toString() {
        return "InvertibleComparator: [" + this.comparator + "]; ascending=" + this.ascending;
    }

}
