package dbutil;

import javax.swing.*;
import java.awt.*;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 19:47
 * @author: Eric
 * @Description: TODO 设置背景图片类
 */
public class Background {
    //设置背景
    public static void setBackgroundPicture(JFrame jFrame, ImageIcon picture) {
        JLabel lbl_image = new JLabel(picture);
        jFrame.getLayeredPane().add(lbl_image, JLayeredPane.FRAME_CONTENT_LAYER);
        lbl_image.setSize(picture.getIconWidth(), picture.getIconHeight());
        Container cp = jFrame.getContentPane();
        cp.setLayout(new BorderLayout());
        //设成透明
        ((JPanel) cp).setOpaque(false);
        jFrame.setSize(picture.getIconWidth(), picture.getIconHeight());
    }
}
