package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 路径的API
 */
public interface Paths {

    /**
     * 是否存在到达p点的路径
     * @param p 目标点
     */
    boolean hashPathTo(int p);

    /**
     * 所有到达p点的路径
     * @param p 目标点
     */
    Iterable<Integer> pathTo(int p);
}
