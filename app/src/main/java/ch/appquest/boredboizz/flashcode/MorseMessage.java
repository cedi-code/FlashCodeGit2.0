package ch.appquest.boredboizz.flashcode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.widget.Toast;

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

    // TODO threads.sleeps halten das ganze programm an :(
    // TODO abstäng würd ig momentan mau so lah ;)
    // main.turnOnFlashlight laht flash liecht a...
    public void transmitMorse(String[] morse) {
        Thread light = new Thread();
        try{
            main.turnOnFlashLight();
            try {
                light.sleep(2000); // 2000
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            main.turnOffFlashLight();
            try {
                light.sleep(1000); // 1000
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        for (String zeichen : morse) {
            try {
                for (char code : zeichen.toCharArray()) {
                    if (code == '.') {
                        main.turnOnFlashLight();
                        try {
                            light.sleep(70); // 70
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        main.turnOffFlashLight();
                        try {
                            light.sleep(140); // 140
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (code == '-') {
                        main.turnOnFlashLight();
                        try {
                            light.sleep(400); // 400
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        main.turnOffFlashLight();
                        try {
                            light.sleep(140); // 140
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                light.sleep(500); //Wartezeit zwischen einzelnen Buchstaben // 500
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
