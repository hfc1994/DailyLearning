import sun.security.provider.MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by user-hfc on 2017/9/11.
 */
public class Test
{
    public static void main(String[] args)
    {
        String str = "asdasda";
        double d = 15.5555;
        printTest(d);

//        InnerClass innerClass =new Test.InnerClass();
//        innerClass.name = "asd";
//        innerClass.age = 19;
//        innerClass.job = "worker";
//        printTest(innerClass);
    }

    public static void printTest(Object obj) {
        System.out.println(obj instanceof InnerClass);
        System.out.println(obj.toString());
    }

    static class InnerClass{
        private String name;
        private int age;
        private String job;

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

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        private String getContent() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.name).append(this.age).append(this.job);
            return sb.toString();
        }

        @Override
        public String toString() {
            return this.getContent();
        }

        @Override
        public int hashCode() {
            return this.getContent().hashCode();
        }
    }
}


