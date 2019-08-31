package org.zk.extractSuperClass;

import java.util.ArrayList;
import java.util.List;

public class Department extends Party {
    private List<Party> parties = new ArrayList<>();

    public Department(String name) {
        super(name);
    }

    public List<Party> getParties() {
        return parties;
    }

    public void addParty(Party employee) {
        parties.add(employee);
    }

    public int getAnnualCost() {
        return parties.stream().mapToInt(Party::getAnnualCost).sum();
    }

    public int getHeadCount() {
        // 递归调用
        return parties.stream().mapToInt(Party::getHeadCount).sum();
    }
}
