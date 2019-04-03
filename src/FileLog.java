import java.io.*;

public class FileLog {
    BufferedReader br = null;
    FileReader fr = null;
    BufferedWriter bw = null;
    FileWriter fw = null;

    public FileLog(String INPUT, String OUTPUT){
        try {
            this.fr = new FileReader(INPUT);
            this.br = new BufferedReader(fr);
            this.fw = new FileWriter(OUTPUT);
            this.bw = new BufferedWriter(fw);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection (){
        try {
            if (br != null)
                br.close();
            if (fr != null)
                fr.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
