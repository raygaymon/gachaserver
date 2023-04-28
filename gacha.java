package poop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class gacha {

    public int ssrPity;
    public int bobaPity;

    public int getSsrPity() {
        return ssrPity;
    }

    public void setSsrPity(int ssrPity) {
        this.ssrPity = ssrPity;
    }

    public int getBobaPity() {
        return bobaPity;
    }

    public void setBobaPity(int bobaPity) {
        this.bobaPity = bobaPity;
    }

    // create list of gacha characters
    // remember to define the arraylist outside any functions
    List<String> gachaList = new ArrayList<>();
    List<String> SSR = new ArrayList<>();
    

    // loading function must have created file as an input
    public void loadGacha(String file, String file2) throws IOException, FileNotFoundException {

        // pass file into created file
        File charList = new File(file);
        File SSRList = new File(file2);

        // create reader to read from gacha file
        BufferedReader br = new BufferedReader(new FileReader(charList));

        // while loop to read through and load file content into list
        String loadList = ""; // read lines must assign to string
        while ((loadList = br.readLine()) != null) {

            gachaList.add(loadList);
        }

        br.close();

        BufferedReader br2 = new BufferedReader(new FileReader(SSRList));

        // while loop to read through and load file content into list
        // read lines must assign to string
        while ((loadList = br2.readLine()) != null) {

            SSR.add(loadList);
        }

        br.close();

        System.out.println("Gacha list loaded. May the odds be ever in your favour");

    }

    // function to generate one character from list
    // function must return a string bcs the output is a string
    public String gachaRoll() {

        Random roll = new Random();
        String result = "";

        result = gachaList.get(roll.nextInt(gachaList.size()-0)).toString();
        ssrPity = ssrPity + 1;
        bobaPity = bobaPity + 1;
        System.out.println(result);
        return result;

        }

    public String ssrRoll () {

        Random roll = new Random();
        String resultSSR = SSR.get(roll.nextInt((SSR.size() - 1))).toString();
        ssrPity = 0;
        bobaPity = bobaPity + 1;
        System.out.println(resultSSR);
        return resultSSR;
    }

    public String bobaRoll () {

        String result = "Boba the rabbit the bestest of luck and the smelliest of babies";
        bobaPity = 0;
        ssrPity = ssrPity + 1;
        System.out.println(result);
        return result;
    }

}
