package snake;

import javax.swing.*;

public class JFrameUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        //设置初始窗口大小为900*720，初始坐标在（10，10）
        frame.setBounds(10,10,925,750);
        //设置是否可调整窗口大小，不可以
        frame.setResizable(false);
        //默认关闭窗口行为
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MPanel());

        //展示窗口
        frame.setVisible(true);
    }
}
