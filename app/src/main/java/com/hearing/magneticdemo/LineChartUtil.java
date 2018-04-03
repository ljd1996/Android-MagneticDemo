package com.hearing.magneticdemo;

import android.graphics.Color;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.*;
import lecho.lib.hellocharts.view.LineChartView;

import java.util.ArrayList;
import java.util.List;

public class LineChartUtil {

    private List<Float> data = new ArrayList<>();//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<>();
    private List<AxisValue> mAxisXValues = new ArrayList<>();

    private List<Line> lines = new ArrayList<>();
    private Axis axisX = new Axis(); //X轴
    private Axis axisY = new Axis();  //Y轴

    public void addData(LineChartView lineChart, float value, String lable) {
        if (data.size() > 81) {
            data.remove(0);
        }

        data.add(value);
        mPointValues.clear();
        mAxisXValues.clear();
        setPoint();
        drawLineChart(lineChart, lable);
    }

    private void setPoint() {
        for (int i = 0; i < data.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(String.valueOf(i)));
            mPointValues.add(new PointValue(i, data.get(i)));
        }
    }

    private void drawLineChart(LineChartView lineChart, String lable){
        Line line = new Line(mPointValues).setColor(Color.rgb(250, 120, 130));  //折线的颜色
        line.setStrokeWidth(1);
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(false);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）

        lines.clear();
        lines.add(line);
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);

        //坐标轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        axisX.setName(lable + "轴测量次数/n---磁场强度：" + data.get(data.size()-1) + "uT");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(1);
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        axisX.setHasLines(true); //x 轴分割线
        lineChartData.setAxisXBottom(axisX); //x 轴在底部

        axisY.setName(lable + "轴磁场强度/uT");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        lineChartData.setAxisYLeft(axisY);  //Y轴设置在左边

        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(lineChartData);

        //X轴固定可见数据个数
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = data.size() - 5;
        v.right= data.size() - 1;
        lineChart.setCurrentViewport(v);
//        lineChart.setMaximumViewport(v);
    }
}
