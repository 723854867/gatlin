package org.gatlin.util.io.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gatlin.util.reflect.BeanUtil;

public class ExcelUtil {
	
	public static final <T> List<T> read(File file, Class<T> clazz,String dateFormat) {
		ZipSecureFile.setMinInflateRatio(-1.0d);
		Workbook workbook = null;
		try {
			POIFSFileSystem fs = new POIFSFileSystem(file, true); 
			workbook = new HSSFWorkbook(fs);
			return _parse(workbook, clazz,dateFormat);
		} catch (Exception e) {
			try {
				workbook = new XSSFWorkbook(file);
				return _parse(workbook, clazz,dateFormat);
			} catch (InvalidFormatException | IOException e1) {
				throw new RuntimeException(file.getPath() + " load failure!", e);
			}
		} finally {
			if (null != workbook) {
				try {
					workbook.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private static final <T> List<T> _parse(Workbook workbook, Class<T> clazz,String dateFormat) {
		List<T> list = new ArrayList<T>();
		Sheet sheet = workbook.getSheetAt(0);
		String[] cols = _parseTitle(sheet);
		_parseContent(sheet, cols, list, clazz,dateFormat);
		return list;
	}

	private static final String[] _parseTitle(Sheet sheet) {
		Row row = sheet.getRow(0);
		String[] cols = new String[row.getPhysicalNumberOfCells()];
		for (int i = 0, len = cols.length; i < len; i++) {
			Cell cell = row.getCell(i);
			cell.setCellType(CellType.STRING);
			cols[i] = cell.getStringCellValue();
		}
		return cols;
	}

	private static final <T> void _parseContent(Sheet sheet, String[] cols, List<T> list, Class<T> clazz,String dateFormat) {
		int rowNum = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < rowNum; i++) {
			Row row = sheet.getRow(i);
			list.add(_parseEntity(row, cols, clazz,dateFormat));
		}
	}

	private static final <T> T _parseEntity(Row row, String[] cols, Class<T> clazz,String dateFormat) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0, len = cols.length; i < len; i++) {
			Cell cell = row.getCell(i);
			// 判断是否为日期类型
			if (CellType.NUMERIC == cell.getCellTypeEnum()) {
				if (DateUtil.isCellDateFormatted(cell)) {
					// 用于转化为日期格式
					Date d = cell.getDateCellValue();
					cell.setCellValue(org.gatlin.util.DateUtil.getDate(d, dateFormat));
				}
			}
			cell.setCellType(CellType.STRING);
			map.put(cols[i], cell.getStringCellValue());
		}
		try {
			return BeanUtil.mapToBean(map, clazz.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(clazz.getName() + " initial failure!", e);
		}
	}
	
	public static final void writeSheet(Workbook workbook, String sheetName, Map<String, ?> data) { 
		Sheet sheet = workbook.createSheet(sheetName);
		Row head = sheet.createRow(0);
		Row value = sheet.createRow(1);
		int idx = 0;
		for (Entry<String, ?> entry : data.entrySet()) {
			Cell hc = head.createCell(idx);
			Cell vc = value.createCell(idx);
			idx++;
			hc.setCellValue(entry.getKey());
			vc.setCellValue(entry.getValue().toString());
			hc.setCellType(CellType.STRING);
			vc.setCellType(CellType.STRING);
		}
		idx = 0;
		for (; idx < data.size(); idx++)
			sheet.autoSizeColumn(idx);
	}
	
	public static final void writeSheet(Workbook workbook, String sheetName, Class<?> entityClass, Object... entities) { 
		List<Object> list = new ArrayList<Object>();
		for (Object object : entities)
			list.add(object);
		writeSheet(workbook, sheetName, entityClass, list);
	}
	
	public static final void writeSheet(Workbook workbook, String sheetName, Class<?> entityClass, Collection<?> entities) { 
		List<Object> list = new ArrayList<Object>();
		for (Object object : entities)
			list.add(object);
		writeSheet(workbook, sheetName, entityClass, list);
	}
	
	public static final void writeSheet(Workbook workbook, String sheetName, Class<?> entityClass, List<Object> entities) { 
		Sheet sheet = workbook.createSheet(sheetName);
		ExcelEntity entity = EntityHelper.getEntity(entityClass);
		List<ExcelCol> cols = entity.getCols();
		cols.sort((o1, o2) -> o1.getScale() - o2.getScale());
		Row head = sheet.createRow(0);
		for (int idx = 0, len = cols.size(); idx < len; idx++) {
			ExcelCol col = cols.get(idx);
			Cell cell = head.createCell(idx);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(col.getName());
		}
		for (int rowNum = 0, len = entities.size(); rowNum < len; rowNum++) {
			Row row = sheet.createRow(rowNum + 1);
			Object data = entities.get(rowNum);
			Map<String, Object> map = BeanUtil.beanToMap(data, true);
			for (int colNum = 0, colLen = cols.size(); colNum < colLen; colNum++) {
				ExcelCol col = cols.get(colNum);
				Object value = map.get(col.getField().getName());
				Cell cell = row.createCell(colNum);
				cell.setCellType(CellType.STRING);
				if (null == value)
					System.out.println("here");
				cell.setCellValue(value.toString());
			}
		}
		for (int idx = 0, len = cols.size(); idx < len; idx++)
			sheet.autoSizeColumn(idx, true);
	}
}
