import com.xiongyingqi.util.FileEncode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class FileHelperTest {
    private File excelFile;

    private File textPlainFile;
    private File textPlainUtf8File;

    @Before
    public void setUp() throws Exception {
        excelFile = new File(getClass().getClassLoader().getResource("ths.xls").getFile());
        textPlainFile = new File(getClass().getClassLoader().getResource("test.txt").getFile());
        textPlainUtf8File = new File(getClass().getClassLoader().getResource("test_utf8.txt").getFile());
    }

    @Test
    public void testEncode() throws Exception {
        Assert.assertEquals(FileEncode.getEncodeByUtil(excelFile), "GB2312");
        Assert.assertEquals(FileEncode.getEncodeByUtil(textPlainFile), "UTF-16LE");
        Assert.assertEquals(FileEncode.getEncodeByUtil(textPlainUtf8File), "UTF-8");
    }
}