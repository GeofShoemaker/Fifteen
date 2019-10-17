import java.util.*;
public class Fifteen{
    public static void main(String[] args){
        Scanner r = new Scanner(System.in);
        System.out.print("You can type \"end\" at any point to end the game.\nHello, what size would you like your board to be? ");
        String in = r.next();
        if(in.equalsIgnoreCase("end"))
            if(prompt()){}
            else{
                System.out.print("What size would you like your board to be? ");
                in = r.next();
                while(in.equals("end")){
                if(prompt())
                    break;
                else
                    System.out.print("What size would you like your board to be? ");
                in = r.next();
                }
                if(!in.equals("end"))
                    play(in,r);
            }
        else
            play(in,r);
        System.out.println("Thanks for playing!");
    }

    static void play(String s, Scanner r){
        int size = Integer.valueOf(s);
            String[][] b = setup(size);
            System.out.print("Would you like to randomize the board? (y/n) ");
            String ans = r.next();
            if(ans.equalsIgnoreCase("y"))
                randomizer(b,size);

            while(!check(b,size)){
                display(b);
                System.out.print("What number would you like to move? ");
                String num = r.next();
                if(num.equalsIgnoreCase("end")){
                    if(prompt())
                        break;
                    else
                        continue;
                }
                else if(!num.matches("[0-9]+"))
                    System.out.println("Please enter a number 1-"+(size*size-1)+".");
                else
                    move(b,size,num);
                if(check(b,size))
                    break;
            }
            display(b);
            System.out.println(check(b,size)?"\nCongratulations! You've won!":"");
    }

    static boolean prompt(){
        Scanner r = new Scanner(System.in);
        System.out.print("Are you sure you want to quit? (y/n) ");
        String num=r.next();
        return num.equalsIgnoreCase("y");
    }

    static void move(String[][] b, int s, String m){
        if(canMove(b,s,m)){
            for(int i=0;i<s;i++)
                for(int j=0;j<s;j++)
                    if(b[i][j].equals(m))
                        b[i][j]="_";
                    else if(b[i][j].equals("_"))
                        b[i][j]=m;
        }
        else
            System.out.println(Integer.valueOf(m)>=s*s?"That number is too big, try again.":"You can't move that number, try again.");
    }

    static boolean canMove(String[][] b, int s, String m){
        int empty = findEmpty(b,s);
        int moveNum = coordinate(b,s,m);
        if(empty==moveNum+s||empty==moveNum-s)
            return true;
        else if(empty%s==0&&moveNum%s==s-1||empty%s==s-1&&moveNum%s==0)
            return false;
        else if(empty==moveNum+1||empty==moveNum-1)
            return true;
        return false;
    }

    static int findEmpty(String[][] b, int s){
        for(int i=0;i<s;i++)
            for(int j=0;j<s;j++)
                if(b[i][j].equals("_"))
                    return s*i+j;
        return 0;
    }

    static int coordinate(String[][] b, int s, String n){
        for(int i=0;i<s;i++)
            for(int j=0;j<s;j++)
                if(b[i][j].equals(n))
                    return s*i+j;
        return 0;
    }

    static String spaces(String s){
        return s.length()==2?s:s+" ";
    }

    static void display(String[][] b){
        for(String[] s:b){
            for(int i=0;i<s.length;i++)
                System.out.print(s[i]==null?"_ ":spaces(s[i])+"  ");
            System.out.println("\n");
        }
    }

    static String[][] setup(int s){
        String[][] board = new String[s][s];
        for(int i=0;i<s;i++)
            for(int j=0;j<s;j++)
                board[i][j]=Integer.toString((s*s-1)-(i*s+j));
        if(s%2==0){
            board[s-1][s-3]="1";
            board[s-1][s-2]="2";
        }
        board[s-1][s-1]="_";
            return board;
    }

    static boolean check(String[][] b,int s){
        int count =s*s-1;
        for(int i=s-1;i>=0;i--)
            for(int j=i==s-1?s-2:s-1;j>=0;j--){
                if(!b[i][j].equals(Integer.toString(count)))
                    return false;
                count--;
            }
        return true;
    }

    static void randomizer(String[][] b, int s){
        Random r = new Random();
        for(int i=0;i<r.nextInt(50)+100;){
            String go = Integer.toString(r.nextInt(s*s-1)+1);
            if(canMove(b,s,go)){
                move(b,s,go);
                i++;
            }

        }


    }
}
