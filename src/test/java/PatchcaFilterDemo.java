import com.xiongyingqi.captcha.color.SingleColorFactory;
import com.xiongyingqi.captcha.filter.predefined.*;
import com.xiongyingqi.captcha.service.ConfigurableCaptchaService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;


public class PatchcaFilterDemo extends Frame implements ActionListener {

    private static final long serialVersionUID = 6698906953413370733L;
    private BufferedImage img;
    private Button reloadButton;
    private int counter;

    public PatchcaFilterDemo() {
        super("Patchca demo");
        setSize(200, 140);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - this.getSize().width) / 2;
        int y = (dim.height - this.getSize().height) / 2;
        setLocation(x, y);
        Panel bottom = new Panel();
        reloadButton = new Button("Next filter");
        reloadButton.addActionListener(this);
        bottom.add(reloadButton);
        add(BorderLayout.SOUTH, bottom);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        if (img == null) {
            createImage();
        }
        if (img != null) {
            g.drawImage(img, 20, 30, this);
        }
    }

    public void createImage() {
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
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
        img = cs.getCaptcha().getImage();
        counter++;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == reloadButton) {
            createImage();
            repaint();
        }

    }

    public static void main(String[] args) {
//        PatchcaFilterDemo f = new PatchcaFilterDemo();
//        f.setVisible(true);
//
//        Object a = null;
//        Integer i = (Integer) a;
//        System.out.println(i);

        Random random = new Random();
        System.out.println(Integer.MAX_VALUE - random.nextInt(100000) + "");
    }


}
