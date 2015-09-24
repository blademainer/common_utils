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

package com.xiongyingqi.util;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.*;


@SuppressWarnings("serial")
public class AutoPopulatingList<E> implements List<E>, Serializable {

    
    private final List<E> backingList;

    
    private final ElementFactory<E> elementFactory;


    
    public AutoPopulatingList(Class<? extends E> elementClass) {
        this(new ArrayList<E>(), elementClass);
    }

    
    public AutoPopulatingList(List<E> backingList, Class<? extends E> elementClass) {
        this(backingList, new ReflectiveElementFactory<E>(elementClass));
    }

    
    public AutoPopulatingList(ElementFactory<E> elementFactory) {
        this(new ArrayList<E>(), elementFactory);
    }

    
    public AutoPopulatingList(List<E> backingList, ElementFactory<E> elementFactory) {
        Assert.notNull(backingList, "Backing List must not be null");
        Assert.notNull(elementFactory, "Element factory must not be null");
        this.backingList = backingList;
        this.elementFactory = elementFactory;
    }


    @Override
    public void add(int index, E element) {
        this.backingList.add(index, element);
    }

    @Override
    public boolean add(E o) {
        return this.backingList.add(o);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return this.backingList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return this.backingList.addAll(index, c);
    }

    @Override
    public void clear() {
        this.backingList.clear();
    }

    @Override
    public boolean contains(Object o) {
        return this.backingList.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.backingList.containsAll(c);
    }

    
    @Override
    public E get(int index) {
        int backingListSize = this.backingList.size();
        E element = null;
        if (index < backingListSize) {
            element = this.backingList.get(index);
            if (element == null) {
                element = this.elementFactory.createElement(index);
                this.backingList.set(index, element);
            }
        } else {
            for (int x = backingListSize; x < index; x++) {
                this.backingList.add(null);
            }
            element = this.elementFactory.createElement(index);
            this.backingList.add(element);
        }
        return element;
    }

    @Override
    public int indexOf(Object o) {
        return this.backingList.indexOf(o);
    }

    @Override
    public boolean isEmpty() {
        return this.backingList.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return this.backingList.iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.backingList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return this.backingList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return this.backingList.listIterator(index);
    }

    @Override
    public E remove(int index) {
        return this.backingList.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        return this.backingList.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.backingList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.backingList.retainAll(c);
    }

    @Override
    public E set(int index, E element) {
        return this.backingList.set(index, element);
    }

    @Override
    public int size() {
        return this.backingList.size();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return this.backingList.subList(fromIndex, toIndex);
    }

    @Override
    public Object[] toArray() {
        return this.backingList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.backingList.toArray(a);
    }


    @Override
    public boolean equals(Object other) {
        return this.backingList.equals(other);
    }

    @Override
    public int hashCode() {
        return this.backingList.hashCode();
    }


    
    public interface ElementFactory<E> {

        
        E createElement(int index) throws ElementInstantiationException;
    }


    
    public static class ElementInstantiationException extends RuntimeException {

        public ElementInstantiationException(String msg) {
            super(msg);
        }
    }


    
    private static class ReflectiveElementFactory<E> implements ElementFactory<E>, Serializable {

        private final Class<? extends E> elementClass;

        public ReflectiveElementFactory(Class<? extends E> elementClass) {
            Assert.notNull(elementClass, "Element class must not be null");
            Assert.isTrue(!elementClass.isInterface(), "Element class must not be an interface type");
            Assert.isTrue(!Modifier.isAbstract(elementClass.getModifiers()), "Element class cannot be an abstract class");
            this.elementClass = elementClass;
        }

        @Override
        public E createElement(int index) {
            try {
                return this.elementClass.newInstance();
            } catch (InstantiationException ex) {
                throw new ElementInstantiationException("Unable to instantiate element class [" +
                        this.elementClass.getName() + "]. Root cause is " + ex);
            } catch (IllegalAccessException ex) {
                throw new ElementInstantiationException("Cannot access element class [" +
                        this.elementClass.getName() + "]. Root cause is " + ex);
            }
        }
    }

}
