package com.cxy.ml.nn;

import com.cxy.ml.common.model.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName NeuralNetworks
 * @Author chenxiangyu-001
 * @Date 2018/10/10
 * @Version 1.0
 */
public class NeuralNetworks {

    /**
     * 学习率
     */
    private double rate;

    /**
     * 隐藏层数量
     */
    private int hiddenNum;

    /**
     * 训练集特征数据集
     */
    private double[][] trainX;

    /**
     * 训练集标签集
     */
    private double[] trainY;

    /**
     * 输入层节点数量
     */
    private int inputPointNum;

    /**
     * 输出层节点数量
     */
    private int outputPointNum;

    /**
     * 隐藏层节点数量
     */
    private int hiddenPointNum;

    /**
     * 权重
     */
    private Map<String,Double> weights;

    /**
     * 偏向
     */
    private double[][] bias;

    /**
     * 标签类别map
     */
    private Map<Double,double[]> labelMap;

    public NeuralNetworks(double rate){
        this.rate = rate;
    }

    /**
     * 训练模型，暂时只训练不测试
     * @param trainData 测试数据集
     */
    public void fit(Data trainData){
        trainX = trainData.getDataX();
        trainY = trainData.getDataY();
        //构建模型配置
        buildConfig();
        //训练模型
        for(int i=0;i<1000;i++){
            int num = (int) Math.random()*trainData.getDataY().length;
            trainOne(trainX[num],labelMap.get(trainY[num]));
        }
    }

    /**
     * 构建神经网络模型参数
     * 初始化权重，偏向
     */
    private void buildConfig(){
        //输入层神经元数量
        inputPointNum = this.trainX[0].length;
        //输出层
        outputPointNum = getOutPointNum();
        //隐藏层
        hiddenNum = 1;
        hiddenPointNum = getHiddenPointNum(inputPointNum,outputPointNum);
        //权重
        weights = new HashMap<>();
        bias = new double[hiddenNum+2][];
        setWeightAndBias();
    }

    /**
     * 为每一层权重赋值
     */
    private void setWeightAndBias(){
        for(int i=1;i<=hiddenNum+2;i++){
            int lastPointNum = hiddenPointNum;
            int nextPointNum = outputPointNum;
            //权重层为隐藏层+1
            if(i == 1){
                //输入层至第一隐藏层
                lastPointNum = inputPointNum;
                nextPointNum = hiddenPointNum;
            }else if(i < hiddenNum+1){
                //隐藏层
                lastPointNum = hiddenPointNum;
                nextPointNum = hiddenPointNum;
            }
            //权重
            if(i != hiddenNum+2){
                for(int last=1;last<=lastPointNum;last++){
                    for(int next=1;next<=nextPointNum;next++){
                        weights.put(i+"-"+last+"-"+(i+1)+"-"+next,getRandom());

                    }
              }
            }
            //偏向
            bias[i-1] = new double[lastPointNum];
            for(int point=1;point<=lastPointNum;point++){
                bias[i-1][point-1] = getRandom();
            }
        }
    }

    /**
     * 获取-1至1之间随机数
     * @return
     */
    private double getRandom(){
        double we = Math.random();
        if(Math.random()<0.5){
            we = we * -1;
        }
        return we;
    }

    /**
     * 获取输出层数量，将输出类别转换为{0,1,0,0}类型表示
     * @return
     */
    private int getOutPointNum(){
        List<Double> lable = new ArrayList<Double>();
        for(int i=0;i<trainY.length;i++){
            double temp = trainY[i];
            if(!lable.contains(temp)){
                lable.add(temp);
            }
        }
        int lableNum = lable.size();
        labelMap = new HashMap<>();
        for(int i=0;i<lableNum;i++){
            double[] la = new double[lableNum];
            la[i] = 1;
            labelMap.put(lable.get(i),la);
        }
        return lableNum;
    }

    private int getHiddenPointNum(int inputPointNum,int outputPointNum){
        return (int)Math.sqrt(inputPointNum*outputPointNum);
    }

    /**
     * 单次训练
     * @param train 单个训练数据
     * @param target 目标标签值
     */
    private void trainOne(double[] train,double[] target){
        //节点输出值
        double[][] outs = new double[hiddenNum+2][];
        double[][] errs = new double[hiddenNum+2][];
        outs[0] = new double[inputPointNum];
        errs[0] = new double[inputPointNum];
        for (int i=0;i<train.length;i++) {
            outs[0][i] = train[i];
        }
        //正向计算
        for(int i=2;i<=hiddenNum+2;i++){
            //第i层神经网络
            outs[i-1] = new double[bias[i-1].length];
            errs[i-1] = new double[bias[i-1].length];
            for(int point=1;point<=bias[i-1].length;point++){
                //第point个神经元
                double out = 0;
                for(int last=1;last<=bias[i-2].length;last++){
                    //遍历上一层神经元
                    out += outs[i-2][last-1] * weights.get((i-1)+"-"+last+"-"+i+"-"+point);
                }
                outs[i-1][point-1] = 1.0/(1.0+Math.pow(Math.E,0-out));
            }
        }
        //输出层误差
        for(int i=1;i<=outputPointNum;i++){
            double out = outs[hiddenNum+1][i-1];
            errs[hiddenNum+1][i-1] = out*(1-out)*(target[i-1]-out);
        }
        //隐藏层误差
        for(int i=hiddenNum+1;i>1;i--){

            double out = outs[i][i-1];
            double errWs = 0;
//            for(int j=1;j<=i)
//            errs[hiddenNum+1-i][i-1] = out*(1-out)*();
        }
    }
}
