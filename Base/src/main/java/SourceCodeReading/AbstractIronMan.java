package SourceCodeReading;

/**
 * Created by user-hfc on 2019/4/1.
 *
 * 一个抽象类里不存在抽象方法，即全部方法都有具体实现
 * 那么说明这个类是被设计用于其它类继承的（抽象类不能实例化）
 */
public abstract class AbstractIronMan {

    public int getWeight() {
        return 1024;
    }

    public int getHeight() {
        return 2;
    }

    public int getPower() {
        return 2048;
    }

    public String getSeries() {
        return "Mark16";
    }
}
