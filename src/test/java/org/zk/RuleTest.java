package org.zk;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.Is.is;

public class RuleTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testFileCreateAndWrite() throws IOException {
        File file = tempFolder.newFile("simple.txt");
        System.out.println(file.getAbsoluteFile());
    }
}
