package snake;

public class MySnake {
    //长度
    private Integer len;
    //方向
    private String direction;
    //坐标（34*24格子内）
    private int x;
    private int y;

    public MySnake() {
        //蛇初始长度
        this.len = 3;
        //蛇初始位置
        this.x = 4;
        this.y = 2;
        //蛇初始方向
        this.direction = "right";
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
