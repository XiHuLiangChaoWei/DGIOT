package cn.zz.dgcc.DGIOT.utils.Html;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Style;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelFactory {

    public static Workbook createSingleWorkBook(List<String> data,String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        //根据 type 参数生成工作簿实例对象
        Workbook workbook = (Workbook) Class.forName(type).newInstance();
        //这里还可以指定 sheet 的名字
        //Sheet sheet = workbook.createSheet("sheetName");
        Sheet sheet = workbook.createSheet("测试sheet页");
        //设置列宽 第一个参数代表列id(从0开始),第2个参数代表宽度值
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 4399);
        sheet.setColumnWidth(2, 4399);
        sheet.setColumnWidth(3, 4399);

        //创建一个行
        Row row = sheet.createRow((short) 0);
        //设置行高度
        row.setHeight((short)400);
        //创建样式
        CellStyle cellStyle = workbook.createCellStyle();
        //设置文字水平居中
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置文字垂直居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        //设置边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框


        //设置字体
        Font font = workbook.createFont();
        font.setColor(Font.BOLDWEIGHT_BOLD);
        font.setBold(true);
        cellStyle.setFont(font);
        //设置 标题 单元格 一个单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        Cell cell = row.createCell((short) 0);
        cell.setCellValue("粮情标题");
        cell.setCellStyle(cellStyle);
        cell = row.createCell((short) 1);
        cell.setCellStyle(cellStyle);
        cell = row.createCell((short) 2);
        cell.setCellStyle(cellStyle);



        cellStyle.setFont(workbook.createFont());

        //第二行第一个
        row = sheet.createRow((short) 1);
        cell = row.createCell(0);
        cell.setCellValue("仓库名称：");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("1号仓1廒间");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("仓库类型：");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("普通平房仓");
        cell.setCellStyle(cellStyle);

        //第三行第一个
        row = sheet.createRow((short) 2);
        cell = row.createCell(0);
        cell.setCellValue("检测时间：");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("2019-09-05 14:37:15");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("天气状况");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle);


        //第四行第一个
        row = sheet.createRow((short) 3);
        cell = row.createCell(0);
        cell.setCellValue("实际储量：");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("粮食品种");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("混合麦");
        cell.setCellStyle(cellStyle);

        //第五行第一个
        row = sheet.createRow((short) 4);
        cell = row.createCell(0);
        cell.setCellValue("入库时间：");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("2019-09-04 ");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("粮食等级");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("一等");
        cell.setCellStyle(cellStyle);


        //第六行第一个
        row = sheet.createRow((short) 5);
        cell = row.createCell(0);
        cell.setCellValue("粮食年份：");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("2019");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("水分");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("13");
        cell.setCellStyle(cellStyle);

        //第七行第一个
        row = sheet.createRow((short) 6);
        cell = row.createCell(0);
        cell.setCellValue("保管员：");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("检测批次");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("2019090514-3");
        cell.setCellStyle(cellStyle);

        //第八行第一个
        row = sheet.createRow((short) 7);
        cell = row.createCell(0);
        cell.setCellValue("仓温：");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("0");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("气温");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("25.63");
        cell.setCellStyle(cellStyle);

        //第9行第1个
        row = sheet.createRow((short) 8);
        cell = row.createCell(0);
        cell.setCellValue("仓湿：");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("0");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("气湿");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("69.76");
        cell.setCellStyle(cellStyle);

        //第10行第1个
        row = sheet.createRow((short) 9);
        cell = row.createCell(0);
        cell.setCellValue("其他：");
        cell.setCellStyle(cellStyle);

        sheet.addMergedRegion(new CellRangeAddress(9, 9, 1, 3));
        cell = row.createCell((short) 1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle);
        cell = row.createCell((short) 2);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle);
        cell = row.createCell((short) 3);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle);

        int rows = 5;
        int cells = 7;
        for(int i = 0; i < rows; i++){
            row = sheet.createRow((short) 11 + i);
            for(int j = 0; j < cells; j++){
                if (i == 0 && j == 0){
                    cell = row.createCell((short) 0);
                    cell.setCellValue("行/列");


                    //创建样式
                    CellStyle cellStyles = workbook.createCellStyle();
                    //设置文字水平居中
                    cellStyles.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    //设置文字垂直居中
                    cellStyles.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                    //设置边框
                    cellStyles.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                    cellStyles.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
                    cellStyles.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                    cellStyles.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                    //设置背景颜色
                    cellStyles.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
                    //solid 填充  foreground  前景色
                    cellStyles.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    cellStyles.setFont(font);

                    cell.setCellStyle(cellStyles);
                }else if (i == 0){
                    cell = row.createCell((short) j);
                    cell.setCellValue("列"+j);
                    //创建样式
                    CellStyle cellStyles = workbook.createCellStyle();
                    //设置文字水平居中
                    cellStyles.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    //设置文字垂直居中
                    cellStyles.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                    //设置边框
                    cellStyles.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                    cellStyles.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
                    cellStyles.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                    cellStyles.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                    //设置背景颜色
                    cellStyles.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
                    //solid 填充  foreground  前景色
                    cellStyles.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    cellStyles.setFont(font);

                    cell.setCellStyle(cellStyles);


                }else if (j == 0){
                    cell = row.createCell((short) 0);
                    cell.setCellValue("行"+i);
                    //创建样式
                    CellStyle cellStyles = workbook.createCellStyle();
                    //设置文字水平居中
                    cellStyles.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    //设置文字垂直居中
                    cellStyles.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                    //设置边框
                    cellStyles.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                    cellStyles.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
                    cellStyles.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                    cellStyles.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                    //设置背景颜色
                    cellStyles.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
                    //solid 填充  foreground  前景色
                    cellStyles.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                    cellStyles.setFont(font);

                    cell.setCellStyle(cellStyles);

                }else{
                    cell = row.createCell((short) j);
                    cell.setCellValue("数据" + i * j);
                    cell.setCellStyle(cellStyle);
                }
            }
        }

        return workbook;

    }

    public static void ExcelSingleOutputStream(String fileName, HttpServletRequest request, HttpServletResponse response)
    {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            strings.add(Integer.toString(i+1));
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            createSingleWorkBook(strings,"org.apache.poi.xssf.usermodel.XSSFWorkbook").write(os);

        } catch (Exception e2) {
            e2.printStackTrace();
        }
        ExcelOutputStream(fileName,request,response,os);
    }

    private static void ExcelOutputStream( String fileName, HttpServletRequest request, HttpServletResponse response,ByteArrayOutputStream os)
    {
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);

        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), StandardCharsets.ISO_8859_1));
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch ( IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}