package com.xiongyingqi.utils.code;

import com.xiongyingqi.util.CollectionHelper;

import java.util.Collection;

/**
 * 代码查找条件
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/3/31 0031.
 */
public class CodeCondition {
    private boolean findWhere;
    private boolean findOnClass;
    private boolean findOnMethod;
    private boolean findOnArgument;
    private Collection<Class<?>> orAnnotatedClasses;
    private Collection<Class<?>> andAnnotatedClasses;
    private Collection<CodeCondition> andCodeConditions;
    private Collection<CodeCondition> orCodeConditions;

    private void setFindWhere() {
        findWhere = true;
    }

    /**
     * 在方法上面进行查找
     *
     * @return
     */
    public CodeCondition onMethod() {
        setFindWhere();
        findOnMethod = true;
        return this;
    }

    /**
     * 在类上进行查找
     *
     * @return
     */
    public CodeCondition onClass() {
        setFindWhere();
        findOnClass = true;
        return this;
    }

    public CodeCondition onArgument() {
        setFindWhere();
        findOnArgument = true;
        return this;
    }

    /**
     * 或者被<code>orAnnotationClass</code>注解的条件，与andAnnotation方法冲突
     *
     * @param orAnnotationClass
     * @return
     */
    public CodeCondition orAnnotation(Class<?> orAnnotationClass) {
        if (!orAnnotationClass.isAnnotation()) {
            System.out.println("Class: " + orAnnotationClass + " 不是注解类！");
            return this;
        }

        orAnnotatedClasses = CollectionHelper.checkOrInitHashSet(orAnnotatedClasses);

        orAnnotatedClasses.add(orAnnotationClass);
        return this;
    }

    /**
     * 并且被<code>andAnnotationClass</code>注解的条件
     *
     * @param andAnnotationClass
     * @return
     */
    public CodeCondition andAnnotation(Class<?> andAnnotationClass) {
        if (!andAnnotationClass.isAnnotation()) {
            System.out.println("Class: " + andAnnotationClass + " 不是注解类！");
            return this;
        }
        andAnnotatedClasses = CollectionHelper.checkOrInitHashSet(andAnnotatedClasses);

        andAnnotatedClasses.add(andAnnotationClass);
        return this;
    }

    /**
     * 条件且
     *
     * @param anotherCodeCondition
     * @return
     */
    public CodeCondition and(CodeCondition anotherCodeCondition) {
        if (anotherCodeCondition == null) {
            System.out.println("CodeCondition为空！");
            return this;
        }
        andCodeConditions = CollectionHelper.checkOrInitHashSet(andCodeConditions);
        return this;
    }

    /**
     * 条件或
     *
     * @param anotherCodeCondition
     * @return
     */
    public CodeCondition or(CodeCondition anotherCodeCondition) {
        if (anotherCodeCondition == null) {
            System.out.println("CodeCondition为空！");
            return this;
        }
        orCodeConditions = CollectionHelper.checkOrInitHashSet(orCodeConditions);
        return this;
    }


    public boolean isFindWhere() {
        return findWhere;
    }

    public boolean isFindOnClass() {
        return findOnClass;
    }

    public boolean isFindOnMethod() {
        return findOnMethod;
    }

    public boolean isFindOnArgument() {
        return findOnArgument;
    }

    public Collection<Class<?>> getOrAnnotatedClasses() {
        return orAnnotatedClasses;
    }

    public Collection<Class<?>> getAndAnnotatedClasses() {
        return andAnnotatedClasses;
    }

    public Collection<CodeCondition> getAndCodeConditions() {
        return andCodeConditions;
    }

    public Collection<CodeCondition> getOrCodeConditions() {
        return orCodeConditions;
    }
}
