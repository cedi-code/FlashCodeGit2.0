package ch.appquest.boredboizz.flashcode;

/**
 * Created by florian.leuenberger on 23.04.2018.
 */

public class MorseTranslater {

    private static String[] morseTable = new String[26];

    private void initMorseTable() {
        morseTable[0] = ".-";
        morseTable[1] = "-...";
        morseTable[2] = "-.-.";
        morseTable[3] = "-..";
        morseTable[4] = ".";
        morseTable[5] = "..-.";
        morseTable[6] = "--.";
        morseTable[7] = "....";
        morseTable[8] = "..";
        morseTable[9] = ".---";
        morseTable[10] = "-.-";
        morseTable[11] = ".-..";
        morseTable[12] = "--";
        morseTable[13] = "-.";
        morseTable[14] = "---";
        morseTable[15] = ".--.";
        morseTable[16] = "--.-";
        morseTable[17] = ".-.";
        morseTable[18] = "...";
        morseTable[19] = "-";
        morseTable[20] = "..-";
        morseTable[21] = "...-";
        morseTable[22] = ".--";
        morseTable[23] = "-..-";
        morseTable[24] = "-.--";
        morseTable[25] = "--..";
    }

    public MorseTranslater() {

        initMorseTable();
    }

    public String addSpace(int count) {
        String zeichen = "";
        if (count > 1 & count <= 4) {
            zeichen = ",";
        }
        else if (count > 4) {
            zeichen = "?";
        }

        return zeichen;
    }

    public String addChar(int count) {
        String zeichen = "";
        if (count > 1 & count < 4) { //Fals nur 1 -4 einsen hat dann ein -> .
            zeichen += ".";
            count = 0; //stueck zurücksetzen
        }
        else if (count > 4) { //Fals mehr als 4 dann ein -> -
            zeichen += "-";
            count = 0; //stueck zurücksetzen
        }

        return zeichen;
    }

    public String morseToText(int[] morse) {
        int count0 = 0;
        int count1 = 0;
        String satz = "";

        for(int i=0; i<morse.length; i++)
        {
            if (morse[i] == 1) { //Einsen zählen
                count1++;
                if (morse[i] + 1 == 0) {
                    satz = addChar(count1);
                    count1 = 0; //count1 zurücksetzen
                }
            }
            else if (morse[i] == 0) { // Nullen zählen
                count0++;
                if (morse[i] + 1 == 1) {
                    satz = addSpace(count0);
                    count0 = 0; //count0 zurücksetzen
                }
            }
        }

        return "Dr Rückgabewärt";
    }

    public String[] textToMorse(String text) {
        String[] morse = new String[40];

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            try{
                morse[i] = morseTable[c - 97];
            }catch(Exception e){}
        }
        return morse;
    }
}
