package stanford.course4.week4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Week4 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        //Process first instance
        System.out.println("*** Processing first instance...");
        ArrayList<Clause> clausesOne = readClausesFromFile("2sat1.txt");

        if (processTwoSAT(clausesOne)) {
            System.out.println("2sat1.txt => Satisfiable (1)");
        } else {
            System.out.println("2sat2.txt => No Satisfiable (0)");
        }

        //Process second instance
        System.out.println("*** Processing second instance...");
        ArrayList<Clause> clausesTwo = readClausesFromFile("2sat2.txt");

        if (processTwoSAT(clausesTwo)) {
            System.out.println("2sat2.txt => Satisfiable (1)");
        } else {
            System.out.println("2sat2.txt => No Satisfiable (0)");
        }

        //Process third instance
        System.out.println("*** Processing third instance...");
        ArrayList<Clause> clausesThree = readClausesFromFile("2sat3.txt");

        if (processTwoSAT(clausesThree)) {
            System.out.println("2sat3.txt => Satisfiable (1)");
        } else {
            System.out.println("2sat3.txt => No Satisfiable (0)");
        }

        //Process fourth instance
        System.out.println("*** Processing fourth instance...");
        ArrayList<Clause> clausesFour = readClausesFromFile("2sat4.txt");

        if (processTwoSAT(clausesFour)) {
            System.out.println("2sat4.txt => Satisfiable (1)");
        } else {
            System.out.println("2sat4.txt => No Satisfiable (0)");
        }

        //Process fifth instance
        System.out.println("*** Processing fifth instance...");
        ArrayList<Clause> clausesFive = readClausesFromFile("2sat5.txt");

        if (processTwoSAT(clausesFive)) {
            System.out.println("2sat5.txt => Satisfiable (1)");
        } else {
            System.out.println("2sat5.txt => No Satisfiable (0)");
        }

        //Process sixth instance
        System.out.println("*** Processing sixth instance...");
        ArrayList<Clause> clausesSix = readClausesFromFile("2sat6.txt");

        if (processTwoSAT(clausesSix)) {
            System.out.println("2sat6.txt => Satisfiable (1)");
        } else {
            System.out.println("2sat6.txt => No Satisfiable (0)");
        }
    }

    /**
     * Papadimitriou's random walk algorithm for 2-SAT. It is not
     * the most optimal and faster solution (it is better use SCC)
     *
     * @param clauses List of Clause
     * @return true if satisfiable, else false
     */
    private static boolean processTwoSAT(ArrayList<Clause> clauses) {
        ArrayList<Clause> falseClauses = new ArrayList<Clause>();
        ArrayList<Boolean> confArray = new ArrayList<Boolean>();

        for (int i = 0; i < clauses.size(); i++) {
            confArray.add(true);
        }

        int n = clauses.size();
        int count = 1;
        boolean done = false;

        while (count <= Math.log(n) / Math.log(2)) {
            int count2 = 1;

            //Random assignment
            Collections.shuffle(confArray);

            while (count2 <= (double) 2 * n * n) {
                Clause c = generateRandomFalse(clauses, falseClauses, confArray);

                if (c == null) {
                    done = true;
                    break;
                } else {
                    //Shuffle literals
                    int r = new Random().nextInt(2) + 1;

                    if (r == 1) {
                        Boolean b = confArray.get(Math.abs(c.getA()));
                        confArray.set(Math.abs(c.getA()), !b);
                    } else {
                        Boolean b = confArray.get(Math.abs(c.getB()));
                        confArray.set(Math.abs(c.getB()), !b);
                    }
                }

                count2++;
            }

            if (done) {
                break;
            }

            count++;
        }

        return done;
    }

    /**
     * Generates a random false
     */
    private static Clause generateRandomFalse(ArrayList<Clause> clauses, ArrayList<Clause> falseClauses, ArrayList<Boolean> confArray) {
        Clause randomClause = null;

        //Fill falses
        falseClauses.clear();

        for (Clause c : clauses) {
            if (!c.evaluate(confArray.get(Math.abs(c.getA())), confArray.get(Math.abs(c.getB())))) {
                falseClauses.add(c);
            }
        }

        if (!falseClauses.isEmpty()) {
            Collections.shuffle(falseClauses);
            randomClause = falseClauses.get(0);
        }

        return randomClause;
    }

    /**
     * Read clauses from a given filename
     */
    private static ArrayList<Clause> readClausesFromFile(String filename) {
        ArrayList<Clause> clauses = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            int nItems = Integer.parseInt(br.readLine());
            clauses = new ArrayList<Clause>(nItems);
            String str;
            while ((str = br.readLine()) != null) {
                int i = Integer.parseInt(str.split(" ")[0]);
                if (i < 0) {
                    i = i + 1;
                } else {
                    i = i - 1;
                }

                int j = Integer.parseInt(str.split(" ")[1]);
                if (j < 0) {
                    j = j + 1;
                } else {
                    j = j - 1;
                }

                clauses.add(new Clause(i, j));
            }

            br.close();

        } catch (Exception ex) {
        }

        return clauses;
    }
}

/**
 * Represents a single clause
 */
class Clause {
    int a;
    int b;

    public Clause(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public boolean evaluate(boolean a1, boolean b1) {
        if (a < 0) {
            a1 = !a1;
        }

        if (b < 0) {
            b1 = !b1;
        }

        return a1 || b1;
    }
}
