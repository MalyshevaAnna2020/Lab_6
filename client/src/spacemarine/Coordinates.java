package spacemarine;

import java.io.Serializable;

public class Coordinates implements Serializable{
    private static final long serialVersionUID = 2954344353170385598L;
    private double x; //Максимальное значение поля: 955
    private int y;

    public void setX(String x){
        try {
            this.x = Double.parseDouble(x);
            if (this.x > 955) this.x = 955;
        }catch (NumberFormatException e){
            this.x = 0;
        }
    }
    public void setY(String y){
        try {
            this.y = Integer.parseInt(y);
        }catch (NumberFormatException e){
            this.y = 0;
        }
    }
    public double getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    @Override
    public String toString(){
        return this.getX() + " " + this.getY();
    }
}
