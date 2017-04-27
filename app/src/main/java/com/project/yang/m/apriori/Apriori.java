package com.project.yang.m.apriori;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Apriori算法实现 最大模式挖掘，涉及到支持度，但没有置信度计算
 * <p>
 * associationRulesMining()函数实现置信度计算和关联规则挖掘
 */
public class Apriori {
    private static final String TAG = "calculate";
    private int times = 0;//迭代次数
    private double MIN_SUPPORT = 0.02;//默认最小支持度百分比
    private double MIN_CONFIDENCE = 0.6;//默认最小置信度
    private boolean endTag = false;//循环状态，迭代标识
    private List<List<String>> record = new ArrayList<>();//数据集
    private List<List<String>> frequentItemSet = new ArrayList<>();//存储所有的频繁项集
    private List<MyMap> map = new ArrayList();//存放频繁项集和对应的支持度计数

    public double getMinSupport() {
        return MIN_SUPPORT;
    }

    public void setMinSupport(double MIN_SUPPORT) {
        this.MIN_SUPPORT = MIN_SUPPORT;
    }

    public double getMinConfidence() {
        return MIN_CONFIDENCE;
    }

    public void setMinConfidence(double MIN_CONFIDENCE) {
        this.MIN_CONFIDENCE = MIN_CONFIDENCE;
    }

    public List<List<String>> getRecord() {
        return record;
    }

    public void setRecord(List<List<String>> record) {
        this.record = record;
    }

    public void setRecord(String url) {
        try {
            String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 )
            File file = new File(url);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTXT;
                while ((lineTXT = bufferedReader.readLine()) != null) {//读一行文件
                    String[] lineString = lineTXT.split(",");
                    List<String> lineList = new ArrayList<String>();
                    for (int i = 0; i < lineString.length; i++) {
                        lineList.add(lineString[i]);
                    }
                    record.add(lineList);
                }

                read.close();
            } else {
//                LogUtil.d(TAG, "找不到文件！");
                System.out.println("找不到文件！");
            }
        } catch (Exception e) {
//            LogUtil.e(TAG, "读取文件内容操作出错！");
            System.out.println("读取文件内容操作出错！");
        }
    }

    /*实现apriori算法*/
    public void calculate()
    {
        //************获取候选1项集**************
        System.out.println("第一次扫描后的1级 备选集CandidateItemset");
        List<List<String>> CandidateItemset = findFirstCandidate();
        ShowData(CandidateItemset);


        //************获取频繁1项集***************
        System.out.println("第一次扫描后的1级 频繁集FrequentItemset");
        List<List<String>> FrequentItemset = getSupportedItemSet(CandidateItemset);
        AddToFrequenceItem(FrequentItemset);//添加到所有的频繁项集中
        //控制台输出1项频繁集
        ShowData(FrequentItemset);


        //迭代过程
        times = 2;
        while (endTag != true) {

//            LogUtil.d(TAG,"第" + times + "次扫描后备选集");
            System.out.println("第" + times + "次扫描后备选集");
            //**********连接操作****获取候选times项集**************
            List<List<String>> nextCandidateItemset = getNextCandidate(FrequentItemset);
            //输出所有的候选项集
            ShowData(nextCandidateItemset);


            /*计数操作***由候选k项集选择出频繁k项集*/
//            LogUtil.d(TAG,"第" + times + "次扫描后频繁集");
            System.out.println("第" + times + "次扫描后频繁集");
            List<List<String>> nextFrequentItemset = getSupportedItemSet(nextCandidateItemset);
            AddToFrequenceItem(nextFrequentItemset);//添加到所有的频繁项集中
            //输出所有的频繁项集
            ShowData(nextFrequentItemset);


            //*********如果循环结束，输出最大模式**************
            if (endTag == true) {
//                LogUtil.d(TAG,"Apriori算法--->最大频繁集");
                System.out.println("Apriori算法--->最大频繁集");
                ShowData(FrequentItemset);
            }
            //****************下一次循环初值********************
            FrequentItemset = nextFrequentItemset;
            times++;//迭代次数加一
        }
    }


    public void associationRulesMining()//关联规则挖掘
    {
        for (int i = 0; i < frequentItemSet.size(); i++) {
            List<String> tem = frequentItemSet.get(i);
            if (tem.size() > 1) {
                List<String> temclone = new ArrayList<>(tem);
                List<List<String>> AllSubset = getSubSet(temclone);//得到频繁项集tem的所有子集
                for (int j = 0; j < AllSubset.size(); j++) {
                    List<String> s1 = AllSubset.get(j);
                    List<String> s2 = gets2set(tem, s1);
                    double conf = isAssociationRules(s1, s2, tem);
                    if (conf > 0)
                        System.out.println("置信度为：" + conf);
                }
            }

        }
    }


    public double isAssociationRules(List<String> s1, List<String> s2, List<String> tem)//判断是否为关联规则
    {
        double confidence = 0;
        int counts1;
        int countTem;
        if (s1.size() != 0 && s1 != null && tem.size() != 0 && tem != null) {
            counts1 = getCount(s1);
            countTem = getCount(tem);
            confidence = countTem * 1.0 / counts1;

            if (confidence >= MIN_CONFIDENCE) {
                System.out.print("关联规则：" + s1.toString() + "=>>" + s2.toString() + "   ");
                return confidence;
            } else
                return 0;

        } else
            return 0;

    }

    public int getCount(List<String> in)//根据频繁项集得到其支持度计数
    {
        int rt = 0;
        for (int i = 0; i < map.size(); i++) {
            MyMap tem = map.get(i);
            if (tem.isListEqual(in)) {
                rt = tem.getcount();
                return rt;
            }
        }
        return rt;

    }


    public List<String> gets2set(List<String> tem, List<String> s1)//计算tem减去s1后的集合即为s2
    {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < tem.size(); i++)//去掉s1中的所有元素
        {
            String t = tem.get(i);
            if (!s1.contains(t))
                result.add(t);
        }
        return result;
    }


    public List<List<String>> getSubSet(List<String> set) {
        List<List<String>> result = new ArrayList<>();    //用来存放子集的集合，如{{},{1},{2},{1,2}}
        int length = set.size();
        int num = length == 0 ? 0 : 1 << (length);    //2的n次方，若集合set为空，num为0；若集合set有4个元素，那么num为16.

        //从0到2^n-1（[00...00]到[11...11]）
        for (int i = 1; i < num - 1; i++) {
            List<String> subSet = new ArrayList<>();

            int index = i;
            for (int j = 0; j < length; j++) {
                if ((index & 1) == 1) {//每次判断index最低位是否为1，为1则把集合set的第j个元素放到子集中
                    subSet.add(set.get(j));
                }
                index >>= 1;//右移一位
            }

            result.add(subSet);//把子集存储起来
        }
        return result;
    }


    public boolean AddToFrequenceItem(List<List<String>> fre) {

        for (int i = 0; i < fre.size(); i++) {
            frequentItemSet.add(fre.get(i));
        }
        return true;
    }


    public void ShowData(List<List<String>> CandidateItemset)//显示出candidateitem中的所有的项集
    {
        for (int i = 0; i < CandidateItemset.size(); i++) {
            List<String> list = new ArrayList<String>(CandidateItemset.get(i));
            for (int j = 0; j < list.size(); j++) {
                System.out.print(list.get(j) + " ");
            }
            System.out.println();
        }
    }


    /**
     * 有当前频繁项集自连接求下一次候选集
     */
    private List<List<String>> getNextCandidate(List<List<String>> FrequentItemset) {
        List<List<String>> nextCandidateItemset = new ArrayList<List<String>>();

        for (int i = 0; i < FrequentItemset.size(); i++) {
            HashSet<String> hsSet = new HashSet<String>();
            HashSet<String> hsSettemp = new HashSet<String>();
            for (int k = 0; k < FrequentItemset.get(i).size(); k++)//获得频繁集第i行
                hsSet.add(FrequentItemset.get(i).get(k));
            int hsLength_before = hsSet.size();//添加前长度
            hsSettemp = (HashSet<String>) hsSet.clone();
            for (int h = i + 1; h < FrequentItemset.size(); h++) {//频繁集第i行与第j行(j>i)连接   每次添加且添加一个元素组成    新的频繁项集的某一行，
                hsSet = (HashSet<String>) hsSettemp.clone();//！！！做连接的hasSet保持不变
                for (int j = 0; j < FrequentItemset.get(h).size(); j++)
                    hsSet.add(FrequentItemset.get(h).get(j));
                int hsLength_after = hsSet.size();
                if (hsLength_before + 1 == hsLength_after && isNotHave(hsSet, nextCandidateItemset)) {
                    //如果不相等，表示添加了1个新的元素       同时判断其不是候选集中已经存在的一项
                    Iterator<String> itr = hsSet.iterator();
                    List<String> tempList = new ArrayList<String>();
                    while (itr.hasNext()) {
                        String Item = (String) itr.next();
                        tempList.add(Item);
                    }
                    nextCandidateItemset.add(tempList);
                }
            }
        }
        return nextCandidateItemset;
    }


    /**
     * 判断新添加元素形成的候选集是否在新的候选集中
     */
    private boolean isNotHave(HashSet<String> hsSet, List<List<String>> nextCandidateItemset) {//判断hsset是不是candidateitemset中的一项
        List<String> tempList = new ArrayList<String>();
        Iterator<String> itr = hsSet.iterator();
        while (itr.hasNext()) {//将hsset转换为List<String>
            String Item = itr.next();
            tempList.add(Item);
        }
        for (int i = 0; i < nextCandidateItemset.size(); i++)//遍历candidateitemset，看其中是否有和templist相同的一项
            if (tempList.equals(nextCandidateItemset.get(i)))
                return false;
        return true;
    }


    /**
     * 由k项候选集剪枝得到k项频繁集
     *
     * @param CandidateItemSet
     * @return
     */
    private List<List<String>> getSupportedItemSet(List<List<String>> CandidateItemSet) { //对所有的商品进行支持度计数
        boolean end = true;
        List<List<String>> supportedItemSet = new ArrayList<List<String>>();

        for (int i = 0; i < CandidateItemSet.size(); i++) {

            int count = countFrequent1(CandidateItemSet.get(i));//统计记录数

            if (count >= MIN_SUPPORT * (record.size() - 1)) {
                supportedItemSet.add(CandidateItemSet.get(i));
                map.add(new MyMap(CandidateItemSet.get(i), count));//存储当前频繁项集以及它的支持度计数
                end = false;
            }
        }
        endTag = end;//存在频繁项集则不会结束
        if (endTag == true) {
//            LogUtil.d(TAG, "无满足支持度的" + times + "项集,结束连接");
            System.out.println("无满足支持度的" + times + "项集,结束连接");
        }
        return supportedItemSet;
    }


    /**
     * 统计record中出现list集合的个数
     */
    private int countFrequent1(List<String> list) {//遍历所有数据集record，对单个候选集进行支持度计数

        int count = 0;
        for (int i = 0; i < record.size(); i++)//从record的第一个开始遍历
        {
            boolean flag = true;
            for (int j = 0; j < list.size(); j++)//如果record中的第一个数据集包含list中的所有元素
            {
                String t = list.get(j);
                if (!record.get(i).contains(t)) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                count++;//支持度加一
        }

        return count;//返回支持度计数

    }

    /**
     * 获得一项候选集
     *
     * @return
     */
    private List<List<String>> findFirstCandidate() {
        List<List<String>> tableList = new ArrayList<List<String>>();
        HashSet<String> hs = new HashSet<String>();//新建一个hash表，存放所有的不同的一维数据

        for (int i = 1; i < record.size(); i++) {  //遍历所有的数据集，找出所有的不同的商品存放到hs中
            for (int j = 1; j < record.get(i).size(); j++) {
                hs.add(record.get(i).get(j));
            }
        }
        Iterator<String> itr = hs.iterator();
        while (itr.hasNext()) {
            List<String> tempList = new ArrayList<String>();
            String Item = itr.next();
            tempList.add(Item);   //将每一种商品存放到一个List<String>中
            tableList.add(tempList);//所有的list<String>存放到一个大的list中
        }
        return tableList;//返回所有的商品
    }
}