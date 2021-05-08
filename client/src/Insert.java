import java.util.Scanner;

/**
 * Class sets values of the object of the collection
 * All methods run themselves until the entered data is correct
 */
public class Insert {
    /**
     * Method gets value of the object's coordinate X
     * @return Coordinate X
     * @author Ann
     */
    public double insertX(){
        Scanner in = new Scanner(System.in);
        Insert insert = new Insert();
        System.out.print("Введите координату X (максимальное значение равно 955): ");
        double x;
        try {
            x = Double.parseDouble(in.nextLine());
            if (x > 955){
                System.err.println("Координата X не может быть больше 955!");
                x = insert.insertX();
            }
        } catch (NumberFormatException e) {
            System.err.println("Координата X представляет собой число!");
            x = insert.insertX();
        }
        return x;
    }

    /**
     * Method gets the value of the object's coordinate Y
     *@return Coordinate Y
     */
    public int insertY(){
        Scanner in = new Scanner(System.in);
        System.out.print("Введите координату Y (целое число): ");
        int y;
        try {
            y = Integer.parseInt(in.nextLine());
        }catch (NumberFormatException e){
            System.err.println("Координата Y представляет собой целое число!");
            Insert insert = new Insert();
            y = insert.insertY();
        }
        return y;
    }

    /**
     * Method gets the value of the object's health
     * @return health (Long)
     */
    public Long insertHealth(){
        Scanner in = new Scanner(System.in);
        Insert insert = new Insert();
        System.out.print("Введите значение переменной health (неотрицательное число): ");
        Long health;
        try {
            health = Long.parseLong(in.nextLine());
            if (health <= 0){
                System.err.println("Переменная health не может представлять собой отрицательное число!");
                health = insert.insertHealth();
            }
        }catch (NumberFormatException e){
            System.err.println("Значение health представляет собой целое число!");
            health = insert.insertHealth();
        }
        return health;
    }

    /**
     * Method gets the value of the object's heartCount
     * @return heartCount (int)
     */
    public int insertHeartCount(){
        Scanner in = new Scanner(System.in);
        Insert insert = new Insert();
        System.out.print("Введите значение переменной HeartCount (целое число от 0 до 3 включительно): ");
        int heartCount;
        try {
            heartCount = Integer.parseInt(in.nextLine());
            if (heartCount < 0){
                System.err.println("Переменная heartCount не может быть меньше 0!");
                heartCount = insert.insertHeartCount();
            }
            else if (heartCount > 3){
                System.err.println("Переменная heartCount не может быть больше 3!");
                heartCount = insert.insertHeartCount();
            }
        }catch (NumberFormatException e){
            System.err.println("Значение HeartCount представляет собой целое число!");
            heartCount = insert.insertHeartCount();
        }
        return heartCount;
    }

    /**
     * Method gets the value of the chapter's marinesCount
     * @return marinesCount (int)
     */
    public int insertMarinesCount(){
        Scanner in = new Scanner(System.in);
        Insert insert = new Insert();
        System.out.print("Введите численность части (натуральное число): ");
        int marinesCount;
        try {
            marinesCount = Integer.parseInt(in.nextLine());
            if (marinesCount < 1){
                System.err.println("Значение MarinesCount представляет собой натуральное число!");
                marinesCount = insert.insertMarinesCount();
            }
        }catch (NumberFormatException e){
            System.err.println("Значение MarinesCount представляет собой целое число!");
            marinesCount = insert.insertMarinesCount();
        }
        return marinesCount;
    }

    /**
     * Method gets the value of the chapter's category
     * @return category of the object
     */
    public String insertCategory(){
        Scanner in = new Scanner(System.in);
        System.out.println("Введите значение category из следующих: \nSUPPRESSOR,\nTERMINATOR,\nLIBRARIAN,\nAPOTHECARY");
        String s = in.nextLine();
        if ((s.equals("SUPPRESSOR"))|| (s.equals("TERMINATOR")) || (s.equals("LIBRARIAN"))  || (s.equals("APOTHECARY"))) {
            return s;
        }else{
            System.err.println("Значение category представляет собой имя константы enum AstartesCategory!");
            Insert insert = new Insert();
            return insert.insertCategory();
        }
    }
}
