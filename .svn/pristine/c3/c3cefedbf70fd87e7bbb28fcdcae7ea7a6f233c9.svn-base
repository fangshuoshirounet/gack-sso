package citic.gack.sso.utils;

import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerifyCode {
    // 默认验证码图片的宽度。
    private static int WIDTH = 60;

    // 默认验证码图片的高度。
    private static int HEIGHT = 20;

    // 默认验证码字符个数
    private static final int CODECOUNT = 4;

    // 默认验证码元素类型digit|letter|mix
    private static final String CODESTYLE = "mix";

    private static Random random = new Random();

    private static final char[] codedigitSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final char[] codeletterSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final char[] codemixSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9'};

    private VerifyCode() {
    }

    public static String getVerifyCode() {
        return getVerifyCode(CODECOUNT, CODESTYLE);
    }

    public static String getVerifyCode(int codecount) {
        return getVerifyCode(codecount, CODESTYLE);
    }

    public static String getVerifyCode(String codestyle) {
        return getVerifyCode(CODECOUNT, codestyle);
    }

    public static String getVerifyCode(int codecount, String codestyle) {
        if (codecount < 1) {
            codecount = VerifyCode.CODECOUNT;
        }

        // 随机产生codecount数字的验证码。
        char[] codeSequence = codemixSequence;
        if ("digit".equalsIgnoreCase(codestyle)) {
            codeSequence = codedigitSequence;
        } else if ("letter".equalsIgnoreCase(codestyle)) {
            codeSequence = codeletterSequence;
        }

        StringBuilder verifyCode = new StringBuilder();
        for (int i = 0; i < codecount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            verifyCode.append(strRand);
        }

        return verifyCode.toString();
    }

    public static BufferedImage getImage(String verifyCode) {
        return getImage(verifyCode, WIDTH, HEIGHT);
    }

    public static BufferedImage getImage(String verifyCode, int width, int height) {
        if (width < 1) {
            width = VerifyCode.WIDTH;
        }
        if (height < 1) {
            height = VerifyCode.HEIGHT;
        }
        if (StringUtils.isBlank(verifyCode)) {
            verifyCode = getVerifyCode();
        }

        int fontHeight = height - 2;
        int codeY = height - 4;
        int x = width / (verifyCode.length() + 1);

        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 将图像填充为白色
        g.setColor(new Color(238, 247, 254));
        g.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // 设置字体。
        g.setFont(font);

        // 画边框。
        g.setColor(new Color(203, 203, 203));
        g.drawRect(0, 0, width - 1, height - 1);

        // 随机产生10条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(new Color(81, 121, 24));
        for (int i = 0; i < 10; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(12);
            int y2 = random.nextInt(12);
            g.drawLine(x1, y1, x1 + x2, y1 + y2);
        }

        for (int i = 0; i < verifyCode.length(); i++) {
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(new Color(81, 121, 24));
            g.drawString(StringUtils.substring(verifyCode, i, i + 1), (i + 1) * x, codeY);
        }

        return buffImg;
    }
}
