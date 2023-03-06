package snake;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MPanel extends JPanel implements KeyListener, ActionListener {
    public MPanel() {
        loadImage();
        gameinit();
        //可以获取键盘焦点事件
        this.setFocusable(true);
        //
        this.addKeyListener(this);
        timer.start();
        loadBGM();
    }

    private void loadImage() {
        InputStream is;
        try {
            is = getClass().getClassLoader().getResourceAsStream("imgs/title.jpg");
            title = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("imgs/body.png");
            body = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("imgs/up.png");
            up = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("imgs/down.png");
            down = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("imgs/left.png");
            left = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("imgs/right.png");
            right = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("imgs/food.png");
            food = new ImageIcon(ImageIO.read(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBGM() {
        try {
            bgm = AudioSystem.getClip();
            InputStream is = this.getClass().getResourceAsStream("/bgm/bgm2.wav");
            //添加一个缓存，打包成jar才能播放音乐
            BufferedInputStream buffer = new BufferedInputStream(is);
            AudioInputStream ais = AudioSystem.getAudioInputStream(buffer);
            bgm.open(ais);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playBGM() {
        bgm.loop(Clip.LOOP_CONTINUOUSLY);
    }
    private void stopBGM(){
        bgm.stop();
    }

    private void gameinit() {
        isStarted = false;
        isFailed = false;
        pause = 0;
        initSnake();
        createFood();
    }

    private void createFood() {
        foodX = random.nextInt(34)+1;
        foodY = random.nextInt(24)+1;
    }

    private void initSnake() {
        length = 3;
        int startX = 4;
        int startY = 2;
        snakeX[0] = startX;
        snakeY[0] = startY;
        direction = "right";
        score = 0;
        for (int i = 1; i < length; i++) {
            startX-=1;
            snakeX[i] = startX;
            snakeY[i] = startY;
        }
    }

    ImageIcon title;
    ImageIcon body;
    ImageIcon up;
    ImageIcon down;
    ImageIcon left;
    ImageIcon right;
    ImageIcon food;

    //背景音乐
    Clip bgm;

    //分数
    int score;
    //长度
    int length;
    //方向
    String direction;
    //蛇坐标
    int[] snakeX = new int[816];
    int[] snakeY = new int[816];
    //是否开始？
    boolean isStarted;
    //是否失败？
    boolean isFailed;
    //暂停
    int pause;
    //定时器
    Timer timer = new Timer(100,this);
    //食物坐标
    int foodX,foodY;
    Random random = new Random();


    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        this.setBackground(Color.white);
        //标题区
        title.paintIcon(this,graphics,37,11);
        graphics.setColor(Color.BLACK);
        graphics.drawString("长度  "+length,750,35);
        graphics.drawString("分数  "+score,750,55);
        //游戏区
        graphics.fillRect(25,75,875,630);
        //画蛇头
        if (direction == "right"){
            right.paintIcon(this,graphics,snakeX[0]*25 + 25,snakeY[0]*25 +75);
        } else if (direction == "left") {
            left.paintIcon(this,graphics,snakeX[0]*25 + 25,snakeY[0]*25 +75);
        } else if (direction == "up") {
            up.paintIcon(this,graphics,snakeX[0]*25 + 25,snakeY[0]*25 +75);
        }else if (direction == "down"){
            down.paintIcon(this,graphics,snakeX[0]*25 + 25,snakeY[0]*25 +75);
        }
        //画身体
        for (int i = 1; i < length; i++) {
            body.paintIcon(this,graphics,snakeX[i]*25 +25,snakeY[i]*25+75);
        }
        //画食物
        food.paintIcon(this,graphics,foodX*25 + 25,foodY*25 + 75);
        //游戏开始
        if (isStarted == false){
            graphics.setColor(Color.white);
            graphics.setFont(new Font("arial",Font.BOLD,40));
            String output;
            if (pause == 0) {
                output = "Press Space to Start";}
            else {
                output = "Pause!Press Space to Continue";
            }
            graphics.drawString(output,200,300);
        }
        if (isFailed){
            graphics.setColor(Color.red);
            graphics.setFont(new Font("arial",Font.BOLD,40));
            graphics.drawString("Filed! Press Space to Restart",200,300);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE){
            if (isFailed){
                isFailed = false;
                //重新游戏
                gameinit();
            }
            else {
                isStarted = !isStarted;
                pause++;
            }
            repaint();
            if (isStarted){
                playBGM();
            }else {
                stopBGM();
            }
        } else if (keyCode == KeyEvent.VK_LEFT && direction !="right") {
            direction = "left";
        } else if (keyCode == KeyEvent.VK_RIGHT && direction !="left") {
            direction = "right";
        } else if (keyCode == KeyEvent.VK_UP && direction !="down") {
            direction = "up";
        }else if (keyCode == KeyEvent.VK_DOWN && direction !="up"){
            direction = "down";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( (pause %2) == 1 ){
            //如果没暂停，才把闹钟打开
            if (!isFailed){
                //游戏没有失败，才执行
                for (int i = length-1; i > 0;i--){
                    snakeX[i] = snakeX[i-1];
                    snakeY[i] = snakeY[i-1];
                }
                //前进方向
                if (direction == "right"){
                    snakeX[0] +=1;
                    //判断是否越墙
                    if (snakeX[0]>34){
                        snakeX[0] = 1;
                    }
                } else if (direction == "left") {
                    snakeX[0] -= 1;
                    if (snakeX[0] <1){
                        snakeX[0] = 34;
                    }
                }else if (direction == "up"){
                    snakeY[0] -= 1;
                    if (snakeY[0] < 1){
                        snakeY[0] = 24;
                    }
                } else if (direction == "down") {
                    snakeY[0] += 1;
                    if (snakeY[0] > 24){
                        snakeY[0] = 1;
                    }
                }
                //吃食物判断
                if (snakeX[0] == foodX && snakeY[0] == foodY){
                    length ++;
                    score += 10;
                    switch(direction) {
                        case "up":
                        case "down":
                            snakeY[length - 1] -= 25;
                            break;
                        case "left":
                        case "right":
                            snakeX[length - 1] -= 25;
                            break;
                    }
                    //重新生成食物
                    foodX = random.nextInt(34)+1;
                    foodY = random.nextInt(24)+1;
                }
                //失败判断
                for (int i = 1; i < length; i++) {
                    if (snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]){
                        isFailed =true;
                    }
                }
            }
            repaint();
            timer.start();
        }

    }
}
