import com.xiongyingqi.http.HttpAccess;
import com.xiongyingqi.http.HttpBuilder;
import com.xiongyingqi.util.EntityHelper;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/7/24 0024.
 */
public class Stock {
    @Test
    public void testStock() throws Exception {
        System.out.println(System.currentTimeMillis());
        HttpRequestBase requestBase = HttpBuilder.newBuilder()
                .url("http://query.sse.com.cn/commonQuery.do?isPagination=true&sqlId=COMMON_SSE_ZQPZ_GPLB_MCJS_SSAG_L&pageHelp.pageSize=100000&pageHelp.pageNo=1&pageHelp.beginPage=1&pageHelp.endPage=100&_="
                        + System.currentTimeMillis())
                .header("Referer", "http://www.sse.com.cn/assortment/stock/list/name/").get()
                .build();
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

    @Test
    public void testApple() throws Exception {
        for (int i = 0; i < 100; i++) {
            HttpRequestBase build = HttpBuilder.newBuilder().get()
                    .url("http://www.tiaobaoji.net/zlfx.php?zid=150900")
                    .build();
            HttpRequestBase build2 = HttpBuilder.newBuilder().post()
                    .url("http://www.tiaobaoji.net/getmon.php")
                    .param("action", "zlmon")
                    .param("SjTime", System.currentTimeMillis() + "")
                    .build();
            CloseableHttpClient client = HttpAccess.getClient();
            String rs = HttpAccess.execute(client, build);
            EntityHelper.print(rs);
            String rs2 = HttpAccess.execute(client, build2);
            EntityHelper.print(rs2);
            try {
                int i1 = Integer.parseInt(rs2);
                if(i1 <= 0){
                    break;
                }
            } catch (Exception e){

            }

        }

    }
    
}
