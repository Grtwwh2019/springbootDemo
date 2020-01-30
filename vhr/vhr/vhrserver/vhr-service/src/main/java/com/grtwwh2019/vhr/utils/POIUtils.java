package com.grtwwh2019.vhr.utils;

import com.grtwwh2019.vhr.model.Employee;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 生成excel的工具类
 */
public class POIUtils {

    /**
     * 员工导出excel
     *
     */
    public static ResponseEntity<byte[]> employee2Excel(List<Employee> list) {
        // 1.创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 2.创建文档摘要(配置文档的基本信息：标题、作者、大小、修改日期、标记等等)
        workbook.createInformationProperties();
        // 3.获取并配置文档信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        // 文档类别
        docInfo.setCategory("员工信息");
        // 文档管理员
        docInfo.setManager("zzj");
        // 设置公司信息
        docInfo.setCompany("zzj.edu");
        // 4.获取文档摘要信息
        SummaryInformation sumInfo = workbook.getSummaryInformation();
        // 文档标题
        sumInfo.setTitle("员工信息表");
        // 文档作者
        sumInfo.setAuthor("zzj");
        // 文档备注
        sumInfo.setComments("本文由zzj学习使用");

        // 5.创建样式
        // 创建标题行样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        // 背景颜色
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        // 填充模式
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 创建日期格式
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        // 相当于simpleDateFormat的 yyyy-MM-dd
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        // 创建表单
        HSSFSheet sheet = workbook.createSheet("员工信息表");
        // 设置每一列的宽度(xxx个字符，x*256)
        sheet.setColumnWidth(0,5*256); // 25个字符
        sheet.setColumnWidth(1,12*256);
        // 6.创建标题行
        HSSFRow r0 = sheet.createRow(0);
        // 创建列
        HSSFCell c0 = r0.createCell(0);
        c0.setCellValue("编号");
        c0.setCellStyle(headerStyle);
        HSSFCell c1 = r0.createCell(1);
        c1.setCellValue("姓名");
        c1.setCellStyle(headerStyle);
        // 遍历list集合
        for (int i = 0; i < list.size(); i++) {
            Employee emp = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getName());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment", new String("员工表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }
}
