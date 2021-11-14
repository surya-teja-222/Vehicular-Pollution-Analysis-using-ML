package com.pollutionmonitor.helperclass;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

import android.os.Build;

import androidx.annotation.RequiresApi;


public class LinearRegression {
    private   List<Integer> x = asList(100,92,87,77,71,68,62,58,40,39,36,35,33,30,27,25,20,15,10,9,7,5,2,0);
    private   List<Integer> y = asList(100,102,108,115,100,107,120,134,150,161,150,147,150,138,140,145,150,145,151,155,160,152,160,158);

    public void setX(List<Integer> xx){
        x = xx;
    }
    public void setY(List<Integer> yy){
        y = yy;
    }
    LinearRegression(List<Integer> xx , List<Integer> yy)
    {
        x = xx ;
        y = yy ;
    }
    LinearRegression()
    {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Double> predictForList(List<Integer> x) {
        return IntStream.range(0, x.size())
                .mapToObj(i -> predictForValue(x.get(i)))
                .collect(Collectors.toList());
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private Double predictForValue(int predictForDependentVariable) {
        if (x.size() != y.size()){
            System.out.println(x.size() + " " + y.size());
            throw new IllegalStateException("Must have equal X and Y data points");
        }

        Integer numberOfDataValues = x.size();

        List<Double> xSquared = x
                .stream()
                .map(position -> Math.pow(position, 2))
                .collect(Collectors.toList());

        List<Integer> xMultipliedByY = IntStream.range(0, numberOfDataValues)
                .map(i -> x.get(i) * y.get(i))
                .boxed()
                .collect(Collectors.toList());

        Integer xSummed = x
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Integer ySummed = y
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumOfXSquared = xSquared
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Integer sumOfXMultipliedByY = xMultipliedByY
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        int slopeNominator = numberOfDataValues * sumOfXMultipliedByY - ySummed * xSummed;
        Double slopeDenominator = numberOfDataValues * sumOfXSquared - Math.pow(xSummed, 2);
        Double slope = slopeNominator / slopeDenominator;

        double interceptNominator = ySummed - slope * xSummed;
        double interceptDenominator = numberOfDataValues;
        Double intercept = interceptNominator / interceptDenominator;

        return (slope * predictForDependentVariable) + intercept;
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public static void main(String[] args) {
//        LinearRegression linearRegression = new LinearRegression();
//        //asList(100,92,87,77,71,68,62,58,40), asList(100,102,108,115,100,107,120,134,150)
//        System.out.println(linearRegression.predictForValue(0));
//        System.out.println(linearRegression.predictForList(asList(5,4,3,2,1,0,-1,-2,-3,-4,-5)));
//    }
}