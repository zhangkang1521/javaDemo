import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    Account account = new Account();

    @Test
    public void gamma() {
        Assert.assertEquals(764, account.gamma(1, 2, 10));
        Assert.assertEquals(4054, account.gamma(1, 2, 500));
    }
}