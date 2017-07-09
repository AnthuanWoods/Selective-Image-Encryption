import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

  public class RegionSelectorListener extends MouseAdapter{
    final JLabel label;
    public static int imageSelector[] = new int[4];

    public RegionSelectorListener(JLabel theLabel) {
        this.label = theLabel;
        theLabel.addMouseListener(this);
    }

    Point origin = null;

    public void mouseClicked(MouseEvent event) {
        if (origin == null) { //If the first corner is not set...

            origin = event.getPoint(); //set it.

        } else { //if the first corner is already set...
           
            //calculate width/height substracting from origin
            int width = event.getX() - origin.x;
            int height = event.getY() - origin.y;
            
            System.out.println(origin.x);
            System.out.println(origin.y);
            System.out.println(width);
            System.out.println(height);
            
            
            imageSelector[0] = origin.x;
            imageSelector[1] = origin.y;
            imageSelector[2] = width;
            imageSelector[3] = height;
        }
    }
}