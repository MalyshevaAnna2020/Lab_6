package client;

import java.util.Scanner;

public class Insert {
    public double insertX(){
        Scanner in = new Scanner(System.in);
        System.out.print("Введите координату X (максимальное значение равно 955): ");
        double x;
        try {
            x = Double.parseDouble(in.nextLine());
            if (x > 955) x = 955;
        } catch (NumberFormatException e) {
            x = 0;
        }
        return x;
    }

    public int insertY(){
        Scanner in = new Scanner(System.in);
        System.out.print("Введите координату Y (целое число): ");
        int y;
        try {
            y = Integer.parseInt(in.nextLine());
        }catch (NumberFormatException e){
            y = 0;
        }
        return y;
    }

   public Long insertHealth(){
        Scanner in = new Scanner(System.in);
        System.out.print("Введите значение переменной health (неотрицательное число): ");
        long health;
        try {
            health = Long.parseLong(in.nextLine());
            if (health <= 0){
                health = 1L;
            }
        }catch (NumberFormatException e){
            health = 1L;
        }
        return health;
    }

    public int insertHeartCount(){
        Scanner in = new Scanner(System.in);
        System.out.print("Введите значение переменной HeartCount (1, 2 или 3): ");
        int heartCount;
        try {
            heartCount = Integer.parseInt(in.nextLine());
            if (heartCount < 1){
                return 1;
            }
            else if (heartCount > 3){
                return 3;
            }
            return heartCount;
        }catch (NumberFormatException e){
            return 1;
        }
    }

    public int insertMarinesCount(){
        Scanner in = new Scanner(System.in);
        System.out.print("Введите численность части (натуральное число): ");
        int marinesCount;
        try {
            marinesCount = Integer.parseInt(in.nextLine());
            if (marinesCount < 1){
                marinesCount = 1;
            }
        }catch (NumberFormatException e){
            marinesCount = 1;
        }
        return marinesCount;
    }

    public String insertCategory(){
        Scanner in = new Scanner(System.in);
        System.out.println("Введите значение category из следующих: \nSUPPRESSOR,\nTERMINATOR,\nLIBRARIAN,\nAPOTHECARY");
        String s = in.nextLine();
        if ((s.equals("SUPPRESSOR"))|| (s.equals("TERMINATOR")) || (s.equals("LIBRARIAN"))  || (s.equals("APOTHECARY"))) {
            return s;
        }else{
            return "";
        }
    }
}
