package org.zk;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * Created by zhangkang on 2018/3/14.
 */
public class User {
    private int id;
    private String username;
    private Integer age;
    private List<Record> records;

    @XStreamAlias("record")
    public static class Record {
        private String customerSalarySeq;
        private String fee;
        private String bankstatus;
        private String ewpCheckcode;
        private String ewpSequence;
        private String errorreason;

        public String getCustomerSalarySeq() {
            return customerSalarySeq;
        }

        public void setCustomerSalarySeq(String customerSalarySeq) {
            this.customerSalarySeq = customerSalarySeq;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getBankstatus() {
            return bankstatus;
        }

        public void setBankstatus(String bankstatus) {
            this.bankstatus = bankstatus;
        }

        public String getEwpCheckcode() {
            return ewpCheckcode;
        }

        public void setEwpCheckcode(String ewpCheckcode) {
            this.ewpCheckcode = ewpCheckcode;
        }

        public String getEwpSequence() {
            return ewpSequence;
        }

        public void setEwpSequence(String ewpSequence) {
            this.ewpSequence = ewpSequence;
        }

        public String getErrorreason() {
            return errorreason;
        }

        public void setErrorreason(String errorreason) {
            this.errorreason = errorreason;
        }
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
