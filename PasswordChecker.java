/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3;

import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rawan
 */
public class PasswordChecker {

    HashT wordsHash;
    HashT passHash;

    public PasswordChecker() {
        int size = getHashSize();
        wordsHash = new HashT(size);
        passHash = new HashT(gethashsize());
        readfromFile();
        readFile2();
    }

    private int getHashSize() { // count the words in txt file
        int count = 0;
        try {

            Scanner input = new Scanner(new File("words.txt"));
            while (input.hasNext()) {
                String a = input.next();
                count++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    private int gethashsize() {
        int count = 0;
        try {
            Scanner input = new Scanner(new File("passwords.txt"));
            while (input.hasNext()) {
                String a = input.next();
                count++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    private void readfromFile() {
        try {
            Scanner input = new Scanner(new File("words.txt"));
            while (input.hasNext()) {
                String a = input.next();
                wordsHash.insert(a);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void readFile2() {
        try {
            Scanner input = new Scanner(new File("passwords.txt"));
            while (input.hasNext()) {
                String a = input.next();
                passHash.insert(a);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayHash1() {
        wordsHash.print();
    }

    public void displayHash2() {
        passHash.print();
    }

    private boolean checkFile1(String s) {
        
        String str = (String) wordsHash.get(s);
        if (str == null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkFile2(String s) {
        
        String str = (String) passHash.get(s);
        if (str == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean check3(String s) {
        String reg = "[A-Za-z]+[0-9]{1}|[0-9]{1}[A-Za-z]+";
        Pattern pat = Pattern.compile(reg);
        Matcher mt = pat.matcher(s);
        boolean result = mt.matches();
        if (result == false && checkFile1(s) && checkFile2(s)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isGood(String s) {
       
        String x = s.trim();
        if (x.length() >= 5 && check3(s)) {
            return true;
        } else {
            return false;
        }
    }

    public void recommendPassword(String s) {
        String newPassword = s;
        String referenceString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ01234567899";
        Random rand = new Random();
        while (isGood(newPassword) == false) {
            // generate and add a random character
            int x = rand.nextInt(referenceString.length());
            newPassword += referenceString.charAt(x);
        }
        System.out.println("Recommended password: " + newPassword);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PasswordChecker pc = new PasswordChecker();
        

        System.out.println("Hi");

        System.out.println("Enter a password: ");
        String password = input.nextLine();
        String p = password.trim(); // removes unnecessary white-spaces optional
        // String p = password.replaceAll("\\s", ""); optional 
        boolean check = pc.isGood(p);

        if (check) {
            System.out.println("Good password! ");
        } else {
            System.out.println("Weak password! ");
            pc.recommendPassword(p);
        }
    }

}
