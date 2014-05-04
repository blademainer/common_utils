/**
 * YIXUN_1.5_EE
 */
package com.xiongyingqi.jackson.impl;

import java.util.Collection;
import java.util.LinkedList;

import com.xiongyingqi.jackson.annotation.IgnoreProperties;
import com.xiongyingqi.jackson.annotation.IgnoreProperty;
import com.xiongyingqi.util.EntityHelper;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.MemberValue;

/**
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-10-24 下午2:28:44
 */
public class JavassistTest {
    public static void main(String[] args) {
        Class clazz = null;
        ClassPool pool = ClassPool.getDefault();

        // create the class
        CtClass cc = pool.makeClass("foo");

        // create the method
        CtMethod mthd = null;
        try {
            mthd = CtNewMethod.make("public Integer getInteger() { return null; }", cc);
            try {
                cc.addMethod(mthd);
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        ClassFile ccFile = cc.getClassFile();
        ConstPool constpool = ccFile.getConstPool();

        // create the annotation
        AnnotationsAttribute attr = new AnnotationsAttribute(constpool,
                AnnotationsAttribute.visibleTag);
        Annotation ignorePropertiesAnnotation = new Annotation(IgnoreProperties.class.getName(),
                constpool);

        // ---------- IgnoreProperty[] 数组成员  ----------
        Collection<MemberValue> memberValues = new LinkedList<MemberValue>();

        AnnotationMemberValue annotationMemberValue = new AnnotationMemberValue(constpool);
        Annotation annotationVar = new Annotation(IgnoreProperty.class.getName(), constpool);
        annotationVar.addMemberValue("pojo", new ClassMemberValue(Object.class.getName(),
                constpool));
        annotationVar.addMemberValue("maxIterationLevel", new IntegerMemberValue(constpool, 12));
        annotationMemberValue.setValue(annotationVar);

        memberValues.add(annotationMemberValue);
        // ---------- IgnoreProperty[] 数组成员  end ----------

        ArrayMemberValue arrayMemberValue = new ArrayMemberValue(constpool);
        arrayMemberValue.setValue(memberValues.toArray(new MemberValue[]{}));

        ignorePropertiesAnnotation.addMemberValue("value", arrayMemberValue);
        attr.addAnnotation(ignorePropertiesAnnotation);
        ccFile.addAttribute(attr);

        // generate the class
        try {
            clazz = cc.toClass();
            Object ignoreProperties = clazz.getAnnotation(IgnoreProperties.class);
            EntityHelper.print(ignoreProperties);

            EntityHelper.print(clazz.getPackage());

            try {
                Object instance = clazz.newInstance();
                EntityHelper.print(instance);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        // length is zero
        java.lang.annotation.Annotation[] annots = clazz.getAnnotations();

        // right
        //		mthd.getMethodInfo().addAttribute(attr);
    }
}
