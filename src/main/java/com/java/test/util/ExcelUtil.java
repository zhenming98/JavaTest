package com.java.test.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Apache POI操作Excel对象
 * HSSF：操作Excel 2007之前版本(.xls)格式,生成的EXCEL不经过压缩直接导出
 * XSSF：操作Excel 2007及之后版本(.xlsx)格式,内存占用高于HSSF
 * SXSSF:从POI3.8 beta3开始支持,基于XSSF,低内存占用,专门处理大数据量(建议)。
 * 注意: 值得注意的是SXSSFWorkbook只能写(导出)不能读(导入)
 * 说明: .xls格式的excel(最大行数65536行,最大列数256列) .xlsx格式的excel(最大行数1048576行,最大列数16384列)
 *
 * @ClassName ExcelUtil
 * @Author yzm
 * @Date 2020/7/22 - 10:44
 * @Email yzm@ogawatech.com.cn
 */
public class ExcelUtil {
    /**
     * 2003 版本的excel
     */
    private final static String EXCEL_2003 = ".xls";
    /**
     * 2007 版本后的excel
     */
    private final static String EXCEL_2007 = ".xlsx";
    /**
     * 默认日期格式（类型为Date即可转换）
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认列宽
     */
    public static final int DEFAULT_COLUMN_WIDTH = 20;
    /**
     * 浏览器标识
     */
    public static final String USER_AGENT_MSIE = "msie";
    public static final String USER_AGENT_TRIDENT = "trident";
    public static final String USER_AGENT_LIKE_GECKO = "like gecko";
    public static final String USER_AGENT_EDGE = "edge";

    /**
     * excel 导出
     *
     * @param title    工作表（sheet）名称
     * @param headMap  标题行 （Key:实体类属性名，Value:excel标题行数据）
     * @param dataList 数据 list
     * @param os       文件输出流
     */
    public static void exportExcel(String title, LinkedHashMap<String, String> headMap, List dataList, OutputStream os) {
        System.out.println(dataList);
        // dataList(List类型) --> dataArray(JSONArray类型)
        JSONArray dataArray = JSONArray.parseArray(JSON.toJSONString(dataList));
        // 声明一个工作薄, 大于100行时会把之前的行写入硬盘
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        /*
        设置是否应压缩临时文件。 SXSSF将工作表数据写入临时文件（每张工作表中有一个临时文件）
        这些临时文件的大小可能会增加到非常大的大小，例如对于20 MB的CSV数据，临时xml文件的大小将变为几GB。
        如果将"compress"标志设置为true，则将临时XML压缩。请注意，“压缩”选项可能会导致性能下降。
        设置此选项仅影响随后的createSheet（）调用的压缩。
         */
        wb.setCompressTempFiles(true);
        //生成一个工作表
        if (StringUtils.isEmpty(title)) {
            title = "工作表1";
        }
        SXSSFSheet sheet = wb.createSheet(title);

        // 生成head相关信息 + 设置每列宽度
        // 列宽数组
        int[] colWidthArr = new int[headMap.size()];
        // headKey数组
        String[] headKeyArr = new String[headMap.size()];
        // headVal数组
        String[] headValArr = new String[headMap.size()];
        int i = 0;

        for (Map.Entry<String, String> entry : headMap.entrySet()) {
            headKeyArr[i] = entry.getKey();
            headValArr[i] = entry.getValue();
            int bytes = headKeyArr[i].getBytes().length;
            colWidthArr[i] = Math.max(bytes, DEFAULT_COLUMN_WIDTH);
            // 设置列宽
            sheet.setColumnWidth(i, colWidthArr[i] * 256);
            i++;
        }
        //遍历数据集合, 产生Excel行数据
        int rowIndex = 0;
        for (Object obj : dataArray) {
            // 生成 head 信息
            if (rowIndex == 0) {
                SXSSFRow headerRow = sheet.createRow(0);
                for (int j = 0; j < headValArr.length; j++) {
                    headerRow.createCell(j).setCellValue(headValArr[j]);
                }
                rowIndex = 1;
            }
            JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
            // 生成数据, 创建行
            SXSSFRow dataRow = sheet.createRow(rowIndex);
            for (int k = 0; k < headKeyArr.length; k++) {
                // 创建单元格
                SXSSFCell cell = dataRow.createCell(k);
                Object o = jo.get(headKeyArr[k]);
                String cellValue;
                if (o == null) {
                    cellValue = "";
                } else if (o instanceof Date) {
                    cellValue = new SimpleDateFormat(DEFAULT_DATE_PATTERN).format(o);
                } else if (o instanceof Float || o instanceof Double) {
                    cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                } else {
                    cellValue = o.toString();
                }
                cell.setCellValue(cellValue);
            }
            rowIndex++;
        }
        try {
            wb.write(os);
            // 刷新此输出流并强制将所有缓冲的输出字节写出
            os.flush();
            // 关闭流
            os.close();
            // 释放workbook所占用的所有windows资源
            wb.dispose();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不同浏览器文件名编码处理
     *
     * @param fileName  文件名
     * @param userAgent 用户浏览器 request.getHeader("User-Agent")
     * @return 编码后文件名
     */
    public static String fileNameCoding(String fileName, String userAgent) throws UnsupportedEncodingException {
        if (userAgent.contains(USER_AGENT_EDGE) || userAgent.contains(USER_AGENT_LIKE_GECKO) ||
                userAgent.contains(USER_AGENT_MSIE) || userAgent.contains(USER_AGENT_TRIDENT)) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //处理文件名多余的加号（+）
            fileName = fileName.replaceAll("\\+", "%20");
        } else {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        }
        return fileName;
    }

}
