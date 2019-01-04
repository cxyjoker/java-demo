package com.cxy.ml.nn;

import com.cxy.ml.common.model.Data;

/**
 * @ClassName TestNN
 * @Author chenxiangyu-001
 * @Date 2018/10/10
 * @Version 1.0
 */
public class TestNN {

    public static void main(String[] args) {
        double[][] old = {{1,1,0},
                {3,5,0},
                {5,7,1},
                {8,9,1},
                {3,3,0},
                {1,8,0},
                {5,6,1},
                {6,8,1},
                {2,2,0},
                {7,7,1},
                {4,4,0}};
        Data data = new Data(old);
        NeuralNetworks nn = new NeuralNetworks(0.1);
        nn.fit(data);

//        double[][] lastPoint = new double[old.length][old[0].length];
//        System.arraycopy(old,0,lastPoint,0,old.length);
//        lastPoint[1][0]=10;
//        System.out.println("old:"+old[1][0]);
//        System.out.println("last:"+lastPoint[1][0]);

//        double[] la = new double[4];
//        System.out.println(la);
    }
}
