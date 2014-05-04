import com.xiongyingqi.captcha.color.SingleColorFactory;
import com.xiongyingqi.captcha.filter.predefined.*;
import com.xiongyingqi.captcha.service.ConfigurableCaptchaService;
import com.xiongyingqi.captcha.utils.encoder.EncoderHelper;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.Random;

public class PatchcaFilterDemoPNG {

    public static void main(String[] args) throws Exception {
        Random random = new Random();
        for (int counter = 0; counter < 5; counter++) {
            ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            cs.getWordFactory().setMinLength(4);
            cs.getWordFactory().setMaxLength(4);
//            cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
            cs.setColorFactory(new SingleColorFactory(new Color(r, g, b)));
            switch (counter % 5) {
                case 0:
                    cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
                    break;
                case 1:
                    cs.setFilterFactory(new MarbleRippleFilterFactory());
                    break;
                case 2:
                    cs.setFilterFactory(new DoubleRippleFilterFactory());
                    break;
                case 3:
                    cs.setFilterFactory(new WobbleRippleFilterFactory());
                    break;
                case 4:
                    cs.setFilterFactory(new DiffuseRippleFilterFactory());
                    break;
            }
            FileOutputStream fos = new FileOutputStream("patcha_demo" + counter + ".png");
            String word = EncoderHelper.getChallangeAndWriteImage(cs, "png", fos);
            System.out.println(word);
            fos.close();
        }
    }
}
