import com.xiongyingqi.http.HttpAccess;
import com.xiongyingqi.http.HttpBuilder;
import com.xiongyingqi.util.EntityHelper;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.Test;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/7/24 0024.
 */
public class Stock {
    @Test
    public void testStock() throws Exception {
        System.out.println(System.currentTimeMillis());
        HttpRequestBase requestBase = HttpBuilder.newBuilder().url("http://query.sse.com.cn/commonQuery.do?isPagination=true&sqlId=COMMON_SSE_ZQPZ_GPLB_MCJS_SSAG_L&pageHelp.pageSize=100000&pageHelp.pageNo=1&pageHelp.beginPage=1&pageHelp.endPage=100&_=" + System.currentTimeMillis()).header("Referer", "http://www.sse.com.cn/assortment/stock/list/name/").get().build();
        String rs = HttpAccess.execute(HttpAccess.getClient(), requestBase);
        EntityHelper.print(rs);
//        HttpRequestBase requestBase = HttpBuilder.newBuilder().url("http://quote.eastmoney.com/search.html?stockcode=30054").get().build();
//        InputStream inputStream = HttpAccess.executeAndGetInputStream(HttpAccess.getClient(), requestBase);
//        FileOutputStream outputStream = new FileOutputStream(new File("a.html"));
//        byte[] buffer = new byte[1024];
//        int len;
//        try {
//            while ((len = inputStream.read(buffer)) > -1) {
//                outputStream.write(buffer, 0, len);
//            }
//            outputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
