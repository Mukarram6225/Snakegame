import javax.swing.JFrame;
public class snakeframe extends JFrame{
    snakeframe(){
        this.add(new panel());
        this.setTitle("snakegame");
        //ensuring that size of the frame window cannot be changed
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        //spawing the frame in the center of the screen
        this.setLocationRelativeTo(null);
    }

}