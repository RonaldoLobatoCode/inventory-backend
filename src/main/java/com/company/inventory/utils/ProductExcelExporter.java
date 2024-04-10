package com.company.inventory.utils;

import java.io.IOException;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.company.inventory.model.Product;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class ProductExcelExporter {

	private XSSFWorkbook workBook; // build book excel
	private XSSFSheet sheet; // references leaves
	private List<Product> product; // iterate in the file excel

	// build constructor
	public ProductExcelExporter(List<Product> products) {
		this.product = products;
		workBook = new XSSFWorkbook();
	}

	/**
	 * method write in the excel header
	 */
	private void writeHeaderLine() {

		sheet = workBook.createSheet("Resultado");
		Row row = sheet.createRow(0);
		CellStyle style = workBook.createCellStyle();

		// create type fountain for the book excel
		XSSFFont font = workBook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "ID", style);
		createCell(row, 1, "Nombre", style);
		createCell(row, 2, "Precio", style);
		createCell(row, 3, "Cantidad", style);
		createCell(row, 4, "Categoria", style);

	}

	/**
	 * method for create cellds in excel
	 */
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {

		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);

		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}

		cell.setCellStyle(style);

	}

	/**
	 * method for create the content the data categories
	 */
	private void writeDataLines() {

		int rowCount = 1;
		CellStyle style = workBook.createCellStyle();
		XSSFFont font = workBook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (Product result : product) {

			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, String.valueOf(result.getId()), style);
			createCell(row, columnCount++, result.getName(), style);
			createCell(row, columnCount++, String.valueOf(result.getPrice()), style);
			createCell(row, columnCount++, String.valueOf(result.getStock()), style);
			createCell(row, columnCount++, String.valueOf(result.getCategory().getName()), style);

		}
	}

	/**
	 * method to export excel file
	 */
	public void export(HttpServletResponse response) throws IOException {

		writeHeaderLine(); // write the header
		writeDataLines(); // write the data

		ServletOutputStream servletOutPut = response.getOutputStream();
		workBook.write(servletOutPut);

		workBook.close();
		servletOutPut.close();

	}
}
