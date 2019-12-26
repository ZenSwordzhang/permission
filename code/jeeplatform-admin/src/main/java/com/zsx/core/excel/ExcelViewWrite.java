package com.zsx.core.excel;

import com.zsx.util.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.util.SheetUtil.getCell;

/**
 * Created by Nicky on 2017/8/1 0001.
 */
public class ExcelViewWrite extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        Date date = new Date();
        String filename = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
        Cell cell;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
        Sheet sheet = workbook.createSheet("sheet1");

        List<String> titles = (List<String>) model.get("titles");
        int len = titles.size();
        // 标题样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 标题字体
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 11);
        headerStyle.setFont(headerFont);
        int width = 20;
        short height = 25 * 20;
        sheet.setDefaultColumnWidth(width);
        for (int i = 0; i < len; i++) { //设置标题
            cell = getCell(sheet, 0, i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(titles.get(i));
        }
        sheet.getRow(0).setHeight(height);
        // 内容样式
        CellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        List<HashMap<String, Object>> varList = (List<HashMap<String, Object>>) model.get("values");
        int varCount = varList.size();
        for (int i = 0; i < varCount; i++) {
            HashMap<String, Object> vpd = varList.get(i);
            for (int j = 0; j < len; j++) {
                String varStr = vpd.get("var" + (j + 1)) != null ? vpd.get("var" + (j + 1)).toString() : "";
                cell = getCell(sheet, i + 1, j);
                cell.setCellStyle(contentStyle);
                cell.setCellValue(varStr);
            }
        }
    }

}
