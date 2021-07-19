package com.java.test.reptile;

import com.java.test.reptile.homedics.Product;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

/**
 * @ClassName ExcelUtil
 * @Author yzm
 * @Date 2020/7/22 - 10:44
 * @Email yzm@ogawatech.com.cn
 */
public class Excel {
    public static void export(List<Product> productList) throws Exception {
        long startTime = System.currentTimeMillis();
        String filePath = "C:\\Users\\Administrator\\Desktop\\1.xlsx";
        SXSSFWorkbook sxssfWorkbook = null;
        BufferedOutputStream outputStream = null;
        try {
            //这样表示SXSSFWorkbook只会保留100条数据在内存中，其它的数据都会写到磁盘里，这样的话占用的内存就会很少
            sxssfWorkbook = new SXSSFWorkbook(getXSSFWorkbook(filePath), 100);
            //获取第一个Sheet页
            SXSSFSheet sheet = sxssfWorkbook.getSheetAt(0);

            sheet.setColumnWidth(0, 1000);
            sheet.setColumnWidth(1, 6000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 3000);
            sheet.setColumnWidth(5, 3000);
            sheet.setColumnWidth(6, 3000);
            sheet.setColumnWidth(7, 10000);
            sheet.setColumnWidth(8, 15000);
            sheet.setColumnWidth(9, 15000);

            SXSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("序号");
            headerRow.createCell(1).setCellValue("产品名");
            headerRow.createCell(2).setCellValue("产品型号");
            headerRow.createCell(3).setCellValue("产品可用");
            headerRow.createCell(4).setCellValue("评价总数");
            headerRow.createCell(5).setCellValue("产品评分");
            headerRow.createCell(6).setCellValue("产品价格");
            headerRow.createCell(7).setCellValue("产品功能");
            headerRow.createCell(8).setCellValue("产品图片");
            headerRow.createCell(9).setCellValue("产品网址");


            CellStyle cellStyle = sxssfWorkbook.createCellStyle();
            cellStyle.setWrapText(true);

            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                SXSSFRow row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getModel());
                row.createCell(3).setCellValue(product.getAvailability());
                row.createCell(4).setCellValue(product.getEvaluation());
                row.createCell(5).setCellValue(product.getScore());
                row.createCell(6).setCellValue(product.getPrice());
                row.createCell(7).setCellStyle(cellStyle);
                row.createCell(7).setCellValue(product.getFunction());
                row.createCell(8).setCellValue(product.getImg());
                row.createCell(9).setCellValue(product.getSrc());
            }

            outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            sxssfWorkbook.write(outputStream);
            outputStream.flush();
            sxssfWorkbook.dispose();// 释放workbook所占用的所有windows资源
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    public static void setHeaderRow(SXSSFRow headerRow, List<String> headerList) {
        headerRow.createCell(0).setCellValue("序号");
        for (int i = 0; i < headerList.size(); i++) {
            headerRow.createCell(i + 1).setCellValue(headerList.get(i));
        }
    }

    public static XSSFWorkbook getXSSFWorkbook(String filePath) {
        XSSFWorkbook workbook = null;
        BufferedOutputStream outputStream = null;
        try {
            File fileXlsxPath = new File(filePath);
            outputStream = new BufferedOutputStream(new FileOutputStream(fileXlsxPath));
            workbook = new XSSFWorkbook();
            workbook.createSheet("Sheet1");
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }
}
