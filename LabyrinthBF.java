import java.util.*;
import java.io.*;

public class LabyrinthBF {
    static int Rows; // Rows
    static int Columns; // Columns
    static int row;
    static int column;
    static int[][] LAB; // Labyrinth.
    static int[][] labCopy;
    static int[] Row = new int[5]; // 4 production _ shifts in X and Y.
    static int[] Column = new int[5];
    static int[] fx;
    static int[] fy;
    static int close, newn, k, u, v;
    static String[] n = new String[1000];
    static int max = 0;
    static String nodes = "Nodes: ";

    static int L; // Move number. Starts from 2. Visited positions are marked.
    static boolean YES;

    public static void main(String args[]) throws IOException {
        try {
            Scanner scanner = new Scanner(System.in);


            // Reading data from the txt file.
            Scanner myReader = new Scanner(new FileInputStream(args[0]));

            // Storing array's size
            String R = myReader.next();
            String C = myReader.next();
            Rows = Integer.parseInt(R);
            Columns = Integer.parseInt(C);

            // Initializing LAB array
            LAB = new int[Rows][Columns];
            labCopy = new int[Rows][Columns];
            fx = new int[Rows * Columns];
            fy = new int[Rows * Columns];

            // Storing agent’s position.
            String c = myReader.next();
            String r = myReader.next();
            column = Integer.parseInt(c);
            row = Integer.parseInt(r);

            // Storing the labyrinth in LAB[][].
            int size = Rows * Columns;
            int i = 0;
            Stack<Integer> stack = new Stack<>();
            while (i < size) {
                String ITEM = myReader.next();
                int item = Integer.parseInt(ITEM);
                stack.push(item);
                i++;
            }

            if (stack.size() != size) {
                System.out.println("Error!!!");
            } else {
                int kr = Rows - 1;
                while (kr >= 0) {
                    int j = Columns - 1;
                    while (j >= 0) {
                        int a = stack.pop();
                        LAB[kr][j] = a;
                        j--;
                    }
                    kr--;
                }
            }

            // Printing the labyrinth
            System.out.println();
            System.out.println("PART 1. Data");
            System.out.println("  " + "1.1. Labyrinth");
            for (int t = 0; t < Rows; t++) {
                for (int tt = 0; tt < Columns; tt++) {
                    System.out.print("    " + LAB[t][tt] + " ");
                    if (tt == Columns - 1) {
                        System.out.println();
                    }
                }
            }
            System.out.println();
            System.out.println("  1.2. Initial position X=" + row + ", Y=" + column + ", L=" + L);
            System.out.println();

            // Forming four production rules.
            Column[1] = -1;
            Row[1] = 0; // Go Left.

            Column[2] = 0;
            Row[2] = 1; // Go Down.

            Column[3] = 1;
            Row[3] = 0; // Go Right. 2

            Column[4] = 0;
            Row[4] = -1; // Go Up.

            // Initialization
            row = row - 1;
            column = column - 1;
            L = 2;
            labCopy[row][column] = L;
            fx[0] = row;
            fy[0] = column;
            close = 0;
            newn = 1;
            YES = false;

            System.out.println();
            L=3;
            int wave=0;
            boolean next=true;

            System.out.println("PART 2. Trace");
            System.out.println("");


            if (row == 0 || row == Rows - 1 || column == 0 || column == Columns - 1) {
                YES = true;
                u = row;
                v = column;
            }

            if(!YES){
                System.out.println("WAVE " + (wave) + ", label L=\"" + L + "\". Initial position X=" + (column + 1) + ", Y=" + (Rows-row) + ", NEWN=" + newn);
                System.out.println();
                wave++;
            }

            if (row > 0 && row < Rows - 1 && column > 0 && column < Columns - 1) {
                do {
                    row = fx[close];
                    column = fy[close];
                    k = 0;
                    
                    if (labCopy[row][column]+1>L){
                        wave++;
                        L++;
                        next=true;
                    }

                    if(next==true){
                        next=false;
                        System.out.println();
                        System.out.println();System.out.println("WAVE " + (wave) + ", label L=\"" + L + "\"." );
                        System.out.println();
                    }
                    
                    System.out.println("  Close CLOSE=" + (close+1) + ", X=" + (column + 1) + ", Y=" + (Rows-row) + ".");
                    do {
                        k++;
                        u = row + Row[k];
                        v = column + Column[k];
                        System.out.print("   R" + k + ". X=" + (v+1) + ", Y=" + (Rows-u) + ". ");
                        if (LAB[u][v] == 0 && labCopy[u][v] == 0) {
                            labCopy[u][v] = labCopy[row][column] + 1;

                            

                            if (u == 0 || u == Rows - 1 || v == 0 || v == Columns - 1) {
                                YES = true;
                                System.out.print("Free. NEWN=" + (newn + 1) + ".");
                            } else {
                                fx[newn] = u;
                                fy[newn] = v;
                                newn++;
                                System.out.print("Free. NEWN=" + newn + ".");
                                
                            }
                        } else {
                            if(LAB[u][v] == 1){
                                System.out.print("Wall.");
                            }
                            else{
                                System.out.print("CLOSED or OPEN.");
                            }
                        }
                        System.out.println();
                        
                    } while (k < 4 && !YES);
                    close++;
                    System.out.println();
                } while (close < newn && !YES);
            }

            // 6. Printing result.
            System.out.println();
            System.out.println("Part 3. Results");

            if (YES) {
                System.out.println();
                System.out.println("  3.1. Path is found");
                back(u, v);
                System.out.println();
                System.out.println("  3.2. Path graphically :");
                System.out.println();
                System.out.print(" ");

                System.out.println("    Y");
                System.out.println("     ^");
                System.out.println("     |");

        System.out.print(" ");
      

                for (int t = 0; t < Rows; t++) {
                    System.out.printf(" %02d",(Rows - t));
                    System.out.print(" |");
                    for (int tt = 0; tt < Columns; tt++) {
                        if (LAB[t][tt] < 0 || LAB[t][tt] > 9) {
                            System.out.print(LAB[t][tt] + "   |  ");
                        } else {
                            System.out.print(" " + LAB[t][tt] + "   |  ");
                        }
                        if (tt == Columns - 1) {

                            System.out.println();
                            System.out.print(" ");
                        }

                        if (LAB[t][tt] > max) {
                            max = LAB[t][tt];
                        }
                    }
                }
                
                System.out.println();
                for (int a = 1; a <= 8*Columns; a++) {
                    if (a==1){
                        System.out.print("      "); 
                    }
                    System.out.print("-");
                }
                System.out.println(" > X");
                for (int a = 1; a <= Columns; a++) {
                    System.out.print("     " );
                    System.out.printf(" %02d",a);
                }
                System.out.println();
                System.out.println();
                System.out.println("  3.3 " + nodes);

            } else {
                System.out.println("Path does not exist");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void back(int u, int v) {
        LAB[u][v] = labCopy[u][v];
        int k = 5;
        do {
            k--;
            int uu = u + Row[k];
            int vv = v + Column[k];
            if (uu >= 0 && uu < Rows && vv >= 0 && vv < Columns && labCopy[uu][vv] == labCopy[u][v] - 1) {
                LAB[uu][vv] = labCopy[uu][vv];
                u = uu;
                v = vv;
                k = 5;
            }
        } while (labCopy[u][v] != 2);

        for (int t = 0; t < Rows; t++) {
            for (int tt = 0; tt < Columns; tt++) {
                if (LAB[t][tt] > max) {
                    max = LAB[t][tt];
                }
            }
        }

        int a = 0;
        int num = 2;
        while (num <= max) {
            for (int t = 0; t < Rows; t++) {
                for (int tt = 0; tt < Columns; tt++) {
                    if (LAB[t][tt] == num) {
                        num++;
                        n[a] = "[X=" + (tt + 1) + ",Y=" + (Rows - t) + "], ";
                        a++;

                    }
                }
            }
        }

        for (int b = 0; b < a; b++) {
            nodes += n[b];
        }

        int trials=0;
        for (int t = 0; t < Rows; t++) {
            for (int tt = 0; tt < Columns; tt++) {
                if (labCopy[t][tt] != 0) {
                    trials++;
                    LAB[t][tt] = labCopy[t][tt];
                }

            }
        }
        System.out.println();
        System.out.println("    Total Trials : " + trials);
    }
}
