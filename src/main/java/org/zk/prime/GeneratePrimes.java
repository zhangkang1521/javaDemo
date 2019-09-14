package org.zk.prime;

public class GeneratePrimes {

    /** 素数标志，true代表是素数 */
    private static boolean[] primeFlag;
    private static int[] result;

    public static int[] generatePrimes(int maxValue) {
        if (maxValue < 2) {
            return new int[0];
        }
        initialize(maxValue);
        flagNotPrime();
        putPrimeToResult();
        return result;
    }

    private static void initialize(int maxValue) {
        primeFlag = new boolean[maxValue + 1];
        // 先初始化未全部是素数，后面用埃氏筛选，将不是素数的打上标记
        for (int i = 0; i < primeFlag.length; i++) {
            primeFlag[i] = true;
        }
        primeFlag[0] = primeFlag[1] = false; // 0,1 不是素数
    }

    private static void flagNotPrime() {
        for (int i = 2; i <= Math.sqrt(primeFlag.length); i++) {
            if (primeFlag[i]) { // 是素数，将素数的倍数值都置为不是素数
                flagMultiOf(i);
            }
        }
    }

    private static void flagMultiOf(int i) {
        for (int j = 2 * i; j < primeFlag.length; j += i) {
            primeFlag[j] = false;
        }
    }



    private static int countPrime() {
        int count = 0;
        for (int i = 0; i < primeFlag.length; i++) {
            if (primeFlag[i]) {
                count++;
            }
        }
        return count;
    }

    private static void putPrimeToResult() {
        result = new int[countPrime()];
        for (int i = 0, j = 0; i < primeFlag.length; i++) {
            if (primeFlag[i]) {
                result[j++] = i;
            }
        }
    }

}
