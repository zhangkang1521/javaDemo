package org.zk.prime;

public class PrimePrinter {
    public static void main(String[] args) {
        int[] data = GeneratePrimes.generatePrimes(130);
        RowColumnPagePrinter printer = new RowColumnPagePrinter(3, 5, "================");
        printer.print(data);
    }
}
