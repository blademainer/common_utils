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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@SuppressWarnings({"serial", "rawtypes"})
public class CompoundComparator<T> implements Comparator<T>, Serializable {

    private final List<InvertibleComparator> comparators;


    
    public CompoundComparator() {
        this.comparators = new ArrayList<InvertibleComparator>();
    }

    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public CompoundComparator(Comparator... comparators) {
        Assert.notNull(comparators, "Comparators must not be null");
        this.comparators = new ArrayList<InvertibleComparator>(comparators.length);
        for (Comparator comparator : comparators) {
            this.addComparator(comparator);
        }
    }


    
    @SuppressWarnings("unchecked")
    public void addComparator(Comparator<? extends T> comparator) {
        if (comparator instanceof InvertibleComparator) {
            this.comparators.add((InvertibleComparator) comparator);
        } else {
            this.comparators.add(new InvertibleComparator(comparator));
        }
    }

    
    @SuppressWarnings("unchecked")
    public void addComparator(Comparator<? extends T> comparator, boolean ascending) {
        this.comparators.add(new InvertibleComparator(comparator, ascending));
    }

    
    @SuppressWarnings("unchecked")
    public void setComparator(int index, Comparator<? extends T> comparator) {
        if (comparator instanceof InvertibleComparator) {
            this.comparators.set(index, (InvertibleComparator) comparator);
        } else {
            this.comparators.set(index, new InvertibleComparator(comparator));
        }
    }

    
    public void setComparator(int index, Comparator<T> comparator, boolean ascending) {
        this.comparators.set(index, new InvertibleComparator<T>(comparator, ascending));
    }

    
    public void invertOrder() {
        for (InvertibleComparator comparator : this.comparators) {
            comparator.invertOrder();
        }
    }

    
    public void invertOrder(int index) {
        this.comparators.get(index).invertOrder();
    }

    
    public void setAscendingOrder(int index) {
        this.comparators.get(index).setAscending(true);
    }

    
    public void setDescendingOrder(int index) {
        this.comparators.get(index).setAscending(false);
    }

    
    public int getComparatorCount() {
        return this.comparators.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int compare(T o1, T o2) {
        Assert.state(this.comparators.size() > 0,
                "No sort definitions have been added to this CompoundComparator to compare");
        for (InvertibleComparator comparator : this.comparators) {
            int result = comparator.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompoundComparator)) {
            return false;
        }
        CompoundComparator<T> other = (CompoundComparator<T>) obj;
        return this.comparators.equals(other.comparators);
    }

    @Override
    public int hashCode() {
        return this.comparators.hashCode();
    }

    @Override
    public String toString() {
        return "CompoundComparator: " + this.comparators;
    }

}
