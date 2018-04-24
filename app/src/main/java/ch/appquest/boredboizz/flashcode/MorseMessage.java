package ch.appquest.boredboizz.flashcode;
import android.os.Handler;

/**
 * Created by florian.leuenberger on 23.04.2018.
 */

public class MorseMessage {

    private MainActivity main;
    public String textMessage; //Nachricht in klartext
    public String[] intervalMessage;

    //Konstruktor
    public MorseMessage(MainActivity main) {
        this.main = main;
    }

    public void receiveMorse() {

    }

    public void transmitMorse(String[] morse) {
        try{
            main.turnOnFlashLight();
            try {
                Thread.sleep(2000); // 2000
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            main.turnOffFlashLight();
            try {
                Thread.sleep(1000); // 1000
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        for (String zeichen : morse) {
            try {
                for (char code : zeichen.toCharArray()) {
                    if (code == '.') {
                        main.turnOnFlashLight();
                        try {
                            Thread.sleep(70); // 70
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        main.turnOffFlashLight();
                        try {
                            Thread.sleep(140); // 140
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (code == '-') {
                        main.turnOnFlashLight();
                        try {
                            Thread.sleep(400); // 400
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        main.turnOffFlashLight();
                        try {
                            Thread.sleep(140); // 140
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
        }catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                Thread.sleep(500); //Wartezeit zwischen einzelnen Buchstaben
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
        catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
}}
