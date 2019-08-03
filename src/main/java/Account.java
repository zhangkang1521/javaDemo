/**
 * 函数对象重构
 */
public class Account {
    public int gamma(int inputVal, int quantity, int yearToDate) {
        return new Gamma(inputVal, quantity, yearToDate, this).compute();
    }

    private int delta() {
        return 1;
    }

    class Gamma {

        int inputVal;
        int quantity;
        int yearToDate;
        Account account;
        int importantValue1;
        int importantValue2;
        int importantValue3;

        public Gamma(int inputVal, int quantity, int yearToDate, Account account) {
            this.inputVal = inputVal;
            this.quantity = quantity;
            this.yearToDate = yearToDate;
            this.account = account;
        }

        int compute() {
            importantValue1 = (inputVal * quantity) + account.delta();
            importantValue2 = (inputVal * yearToDate) + 100;
            importantThing();
            importantValue3 = importantValue2 * 7;
            return importantValue3 - 2 * importantValue1;
        }

        void importantThing(){
            if (yearToDate - importantValue1 > 100) {
                importantValue2 -= 20;
            }
        }
    }
}



