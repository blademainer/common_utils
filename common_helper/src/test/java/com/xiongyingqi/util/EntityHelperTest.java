package com.xiongyingqi.util;

import org.junit.Test;

import java.util.HashSet;

public class EntityHelperTest {
    public class AEntity extends EntityHelper {
        private int id;
        private String name;
        private int age;
        private int b;
        private int c;
        private int d;
        private int e;
        private int f;
        private int g;

        public AEntity() {
        }

        public AEntity(int id, String name, int age) {
            this.id = id;
            this.b = id;
            this.c = id;
            this.d = id;
            this.e = id;
            this.f = id;
            this.g = id;

            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        public int getD() {
            return d;
        }

        public void setD(int d) {
            this.d = d;
        }

        public int getE() {
            return e;
        }

        public void setE(int e) {
            this.e = e;
        }

        public int getF() {
            return f;
        }

        public void setF(int f) {
            this.f = f;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        @Override
        public int hashCode() {
            int i = super.hashCode();
            return i;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }
    public class BEntity extends EntityHelper {
        private Integer id;
        private String name;
        private Integer age;
        private Integer b;
        private Integer c;
        private Integer d;
        private Integer e;
        private Integer f;
        private Integer g;

        public BEntity() {
        }

        public BEntity(int id, String name, int age) {
            this.id = id;
            this.b = id;
            this.c = id;
            this.d = id;
            this.e = id;
            this.f = id;
            this.g = id;

            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        public int getD() {
            return d;
        }

        public void setD(int d) {
            this.d = d;
        }

        public int getE() {
            return e;
        }

        public void setE(int e) {
            this.e = e;
        }

        public int getF() {
            return f;
        }

        public void setF(int f) {
            this.f = f;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + (age != null ? age.hashCode() : 0);
            result = 31 * result + (b != null ? b.hashCode() : 0);
            result = 31 * result + (c != null ? c.hashCode() : 0);
            result = 31 * result + (d != null ? d.hashCode() : 0);
            result = 31 * result + (e != null ? e.hashCode() : 0);
            result = 31 * result + (f != null ? f.hashCode() : 0);
            result = 31 * result + (g != null ? g.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

    @org.junit.Test
    public void testHashCode() throws Exception {
        TimerHelper.getTime();
        HashSet<AEntity> aHashEntities = new HashSet<>();
        HashSet<AEntity> aHashEntitiesToRemove = new HashSet<>();
        for (int i = 0; i < 100000; i++) {
            AEntity aEntity = new AEntity(i, i + "", i * 10);
            aHashEntities.add(aEntity);
        }

        for (int i = 0; i < 10000; i++) {
            AEntity aEntity = new AEntity(i, i + "", i * 10);
            aHashEntitiesToRemove.add(aEntity);
        }
        aHashEntities.removeAll(aHashEntitiesToRemove);
        System.out.println("HashSet A least ============ " + aHashEntities.size());
        System.out.println("HashSet A ====== " + TimerHelper.getTime());

        TimerHelper.getTime();
        HashSet<BEntity> bHashSetEntities = new HashSet<>();
        HashSet<BEntity> bHashSetEntitiesToRemove = new HashSet<>();
        for (int i = 0; i < 100000; i++) {
            BEntity aEntity = new BEntity(i, i + "", i * 10);
            bHashSetEntities.add(aEntity);
        }

        for (int i = 0; i < 10000; i++) {
            BEntity aEntity = new BEntity(i, i + "", i * 10);
            bHashSetEntitiesToRemove.add(aEntity);
        }
        bHashSetEntities.removeAll(bHashSetEntitiesToRemove);
        System.out.println("HashSet B least ============ " + bHashSetEntities.size());
        System.out.println("HashSet B ====== " + TimerHelper.getTime());

    }

    @Test
    public void testEquals() throws Exception {

    }
}