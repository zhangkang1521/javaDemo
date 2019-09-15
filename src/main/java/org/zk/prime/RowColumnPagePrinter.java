package org.zk.prime;

import java.io.PrintStream;

public class RowColumnPagePrinter {
    private final int ROWS_PER_PAGE;
    private final int COLUMNS_PER_PAGE;
    private final int NUMBERS_PER_PAGE;
    private String pageHeader;
    private PrintStream printStream;

    public RowColumnPagePrinter(int ROWS_PER_PAGE, int COLUMNS_PER_PAGE, String pageHeader) {
        this.ROWS_PER_PAGE = ROWS_PER_PAGE;
        this.COLUMNS_PER_PAGE = COLUMNS_PER_PAGE;
        this.pageHeader = pageHeader;
        NUMBERS_PER_PAGE = ROWS_PER_PAGE * COLUMNS_PER_PAGE;
        printStream = System.out;
    }

    public void print(int data[]) {
        int pageNumber = 1;
        for (int firstIndexOfPage = 0; firstIndexOfPage < data.length; firstIndexOfPage += NUMBERS_PER_PAGE) {
            printPageHeader(pageNumber);
            int lastIndexOfPage = Math.min(firstIndexOfPage + NUMBERS_PER_PAGE, data.length);
            printPage(firstIndexOfPage, lastIndexOfPage, data);
            printStream.println("\f");
            pageNumber++;
        }
    }

    private void printPage(int firstIndexOfPage, int lastIndexOfPage, int[] data) {
        for (int firstIndexOfRow = firstIndexOfPage; firstIndexOfRow < lastIndexOfPage; firstIndexOfRow += COLUMNS_PER_PAGE) {
           printRow(firstIndexOfRow, lastIndexOfPage, data);
           printStream.println();
        }
    }

    private void printRow(int firstIndexOfRow, int lastIndexOfPage, int[] data) {
        for (int column = 0; column < COLUMNS_PER_PAGE; column++) {
            int index = firstIndexOfRow + column;
            if (index < lastIndexOfPage) {
                printStream.format("%4d", data[index]);
            }
        }
    }

    private void printPageHeader(int pageNumber) {
        printStream.println(pageHeader + " " + pageNumber + " " + pageHeader);
    }
}
