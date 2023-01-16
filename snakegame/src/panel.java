import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {

    //height and width of the panel in pixels
    static int width=1200;
    static int height=600;
    //unit size of a single grid unit
    static int unit=50;

    // timer to constantly check on the state of the game
    Timer timer;
    //random variable to randomly spawn the food
    Random random;

    //coordinate for food
    int fx,fy;
    int foodeaten;

    //length of the snake
    int body =3;
    //flag to check if the game runnig
    boolean flag =false;
    //diration of snake
    char dir ='R';

    static int Delay =160;
    static  int gridsize = (width*height)/(unit*unit);//=288

    int x_snake[] = new int[gridsize];
    int y_snake[] = new int[gridsize];
    panel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKey());
        random = new Random();
        Game_Start();
    }
    public void Game_Start(){
        spawnfood();
        flag = true;
        timer = new Timer(Delay,this);
        timer.start();
    }
    public void spawnfood(){
        fx=random.nextInt((int)(width/unit))*unit;//random integer between 0 - 1200 multiple of 50
        fy=random.nextInt((int)(height/unit))*unit;
    }

    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void draw(Graphics graphic){
        //game is runing
        if(flag){
            graphic.setColor(Color.orange);
            graphic.fillOval(fx,fy,unit,unit);

            //drawing the body of the snake
            for(int i=0;i<body;i++){
                if(i==0){
                    graphic.setColor(Color.red);
                    graphic.fillRect(x_snake[0],y_snake[0],unit,unit);
                }
                else {
                    graphic.setColor(Color.green);
                    graphic.fillRect(x_snake[i],y_snake[i],unit,unit);
                }
            }
            //score display
            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
            FontMetrics fm= getFontMetrics(graphic.getFont());
            graphic.drawString("score"+foodeaten,(width - fm.stringWidth("Score:"+foodeaten))/2,graphic.getFont().getSize());

        }
        else {
            gameover(graphic);
        }
    }
    public void gameover(Graphics graphic){
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics fm= getFontMetrics(graphic.getFont());
        graphic.drawString("score:"+foodeaten,(width - fm.stringWidth("score:"+foodeaten))/2,graphic.getFont().getSize());

        //gameover display
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,80));
        FontMetrics fm2= getFontMetrics(graphic.getFont());
        graphic.drawString("Game Over",(width - fm2.stringWidth("Game Over"))/2,height/2);

        //press r to replay
        graphic.setColor(Color.CYAN);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics fm3= getFontMetrics(graphic.getFont());
        graphic.drawString("Press the R to reply",(width - fm3.stringWidth("Press the R to reply"))/2, height/2-150);
    }
    public void move(){
        //updating the body
        for(int i=body;i>0;i--){
           x_snake[i]=x_snake[i-1];
           y_snake[i]=y_snake[i-1];
        }
        //update the head
        switch (dir){
            case 'U':
                y_snake[0]=y_snake[0] - unit;
                break;
            case 'D':
                y_snake[0]=y_snake[0] + unit;
                break;
            case 'L':
                x_snake[0] = x_snake[0] - unit;
                break;
            case 'R':
                x_snake[0] = x_snake[0] + unit;
                break;
        }
    }

    public void check(){
        for(int i=body;i>0;i--){
            if((x_snake[0] == x_snake[i]) && (y_snake[0] == y_snake[i])){
                flag = false;
            }
        }

        //to check hit with wall
        if(x_snake[0]<0){
            flag=false;
        }
        else if (x_snake[0]>width) {
            flag=false;
        }
        else if(y_snake[0]<0){
            flag=false;
        }
        else if(y_snake[0]>height){
            flag=false;
        }

        if(!flag){
            timer.start();
        }
    }

    public void food(){
        if((x_snake[0]==fx)&&(y_snake[0]==fy)){
            body++;
            foodeaten++;
            spawnfood();
        }
    }


    public class MyKey extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir!='R'){
                        dir='L';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir!='D'){
                        dir='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!='U'){
                        dir='D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L'){
                        dir='R';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!flag){
                        foodeaten=0;
                        body=3;
                        dir='R';
                        Arrays.fill(x_snake,0);
                        Arrays.fill(y_snake,0);
                        Game_Start();
                    }
            }
        }

    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (flag) {
            move();
            food();
            check();
        }
        repaint();

    }
}
