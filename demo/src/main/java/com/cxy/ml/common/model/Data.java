package com.cxy.ml.common.model;

/**
 * 机器学习数据模型
 * @ClassName DataModel
 * @Author chenxiangyu-001
 * @Date 2018/10/10
 * @Version 1.0
 */
public class Data {

    /**
     * 特征数据x
     */
    private double[][] dataX;

    /**
     * 标签值
     */
    private double[] dataY;

    /**
     * 特征名称
     */
    private String[][] featureName;

    /**
     * 特征数量
     */
    private int featureNum;

    public double[][] getDataX() {
        return dataX;
    }

    public double[] getDataY() {
        return dataY;
    }

    public String[][] getFeatureName() {
        return featureName;
    }

    public int getFeatureNum() {
        return featureNum;
    }

    /**
     * X Y数据未分离的原始数据
     * @param initData
     */
    public Data(double[][] initData){
        int featureNum = initData[0].length - 1;
        int dataNum = initData.length;
        dataX = new double[dataNum][featureNum];
        dataY = new double[dataNum];
        for(int i=0;i<dataNum;i++){
            //每一行
            for(int j=0;j<featureNum+1;j++){
                //每一列
                if(j == featureNum){
                    dataY[i] = initData[i][j];
                }else{
                    dataX[i][j] = initData[i][j];
                }
            }
        }
        this.featureNum = dataX[0].length;
    }
}
