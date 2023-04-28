package poop;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//learning points:
//keep the called functions simple
//keep the randomiser in the server instead of the class, somehow more reliable
//code might just fuck up and cry
//for rarity, make each type of roll its own function
//pity count is tied to each gacha file - possible next step is to create diffferent pities for different accounts
//pull history even? like the shopping cart login thing but added here

public class App {
    public static void main(String[] args) throws NumberFormatException, IOException {
        String port = args[0];
        String file = args[1];
        String file2 = args[2];

        File gacha = new File(file);
        if (!gacha.exists()) {

            gacha.createNewFile();
            System.out.println("File created.");

        } else {

            System.out.println("File located, ready to roll");
        }

        File gachaSSR = new File(file2);
        if (!gachaSSR.exists()) {

            gachaSSR.createNewFile();
            System.out.println("File created.");

        } else {

            System.out.println("File located, ready to roll");
        }

        // can manually decide the port in ths command or choose one in the execution

        ServerSocket server = new ServerSocket(Integer.parseInt(port));
        Socket socket = server.accept();

        List<String> tenPull = new ArrayList<>();
        // int pity = 0;
        // int bobaPity = 0;
        gacha wish = new gacha();

        try (InputStream is = socket.getInputStream()) {

            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            String msg = "";

            try (OutputStream os = socket.getOutputStream()) {

                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);

                while (!msg.equals("done")) {

                    msg = dis.readUTF();

                    System.out.println("Dispensing gacha...");

                    Random random = new Random();

                    if (msg.equals("ten")) {

                        
                        wish.loadGacha(file, file2);
                        String wishResults = "";

                        tenPull.removeAll(tenPull);

                        for (int i = 0; i < 10; i++) {

                            if (wish.getSsrPity() >= 79) {

                                wishResults = wish.ssrRoll();
                                tenPull.add(wishResults);

                            }

                            else if (wish.getBobaPity() >= 29) {

                                wishResults = wish.bobaRoll();
                                tenPull.add(wishResults);

                            }

                            else {

                                int rarity = random.nextInt(1000 - 1);

                                if (rarity == 999) {

                                    wishResults = wish.bobaRoll();
                                    tenPull.add(wishResults);
                                }

                                else if (rarity <= 6 && rarity >= 0) {

                                    wishResults = wish.ssrRoll();
                                    tenPull.add(wishResults);

                                }

                                else {

                                    wishResults = wish.gachaRoll();
                                    tenPull.add(wishResults);
                                }

                            }
                        }

                        dos.writeUTF("You got: \n" + tenPull.toString());
                        dos.flush();

                    }

                    else if (msg.equals("one")) {

                        wish.loadGacha(file, file2);
                        String wishResults = "";

                        if (wish.getSsrPity() >= 79) {
 
                            wishResults = wish.ssrRoll();

                        }

                        if (wish.getBobaPity() >= 29) {

                            wishResults = wish.bobaRoll();

                        }

                        else {

                            int rarity = random.nextInt(1000 - 1);

                            if (rarity == 999) {

                                wishResults = wish.bobaRoll();

                            }

                            else if (rarity <= 6 && rarity >= 0) {

                                wishResults = wish.ssrRoll();

                            }

                            else {

                                wishResults = wish.gachaRoll();
                            }
                            
                        }

                        dos.writeUTF("You got: " + wishResults);
                        dos.flush();

                    }

                    else if (msg.equals("pity")) {

                        //method from class can only be called on object of class
                        dos.writeUTF("Your current pity counter is: " + Integer.toString(wish.getSsrPity()) + "\nYour current Boba pity counter is: " + Integer.toString(wish.getBobaPity()));

                        System.out.println("Your current pity counter is: " + wish.getBobaPity());
                        dos.flush();
                    }

                    else if (msg.equals("done")) {

                        dos.writeUTF("Bye bye");
                        dos.flush();
                    }

                    else {

                        dos.writeUTF("You can't do that ");
                        dos.flush();
                    }

                }

                dos.close();
                bos.close();
                os.close();

            } catch (EOFException ex) {

            }

            dis.close();
            bis.close();
            is.close();

        } catch (EOFException ex) {

            ex.printStackTrace();
            System.exit(0);

        }

    }
}
