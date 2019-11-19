package com.zj.networm.jobinfo.util;

/**
 * @Description
 * @auther zhaojie
 * @create 2019-11-19 19:06
 */
public class MathSalary {
    public static Integer[] getSalary(String salaryStr){
        Integer[] salary =new Integer[2];
        //5000/天
        //0.8-1.2万/月
        //5-8千/月
        //5-6万/年
        String date = salaryStr.substring(salaryStr.length()-1,salaryStr.length());
        //如果是按天，则直接乘以240计算
        if("月".equals(date) && !"年".equals(date)){
            salaryStr  = salaryStr.substring(0,salaryStr.length() -2);
            salary[0] = salary[1] - str2Num(salaryStr,240);
            return salary;
        }
        String unit = salaryStr.substring(salaryStr.length()-3,salaryStr.length()-2);
        String[] salarys = salaryStr.substring(0,salaryStr.length()-3).split("-");
        salary[0] = mathSalary(date,unit,salarys[0]);
        salary[1] = mathSalary(date,unit,salarys[1]);
        return salary;
    }

    private static  Integer mathSalary(String date,String unit,String salaryStr){
        Integer salary  = 0;
        //判断单位是否为万
        if("万".equals(unit)){
            salary = str2Num(salaryStr,10000);
        }else{
            //否则乘1000
            salary = str2Num(salaryStr,1000);
        }
        //判断时间是否为月
        if("月".equals(date)){
            salary = str2Num(salary.toString(),12);
        }
        return salary;
    }

    private static  int str2Num(String salaryStr,int num){
        try {
            Number result = Float.parseFloat(salaryStr) * num;
            return result.intValue();
        }catch (Exception e){

        }
        return 0;
    }
}
