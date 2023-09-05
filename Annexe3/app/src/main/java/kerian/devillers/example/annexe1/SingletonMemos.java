package kerian.devillers.example.annexe1;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SingletonMemos {
    private static SingletonMemos instance;
    private Context context;
    private ArrayList<String> listeMemos;
    private SingletonMemos(Context context) {
        this.context = context;
        listeMemos = new ArrayList<>();
    }

    public static SingletonMemos getInstance(Context context) {
        if(instance == null){
            instance = new SingletonMemos(context);
        }
        return instance;
    }

    public void ajouterMemo(String memo){
        listeMemos.add(memo);
    }

    public ArrayList<String> getListeMemos() throws IOException {
        ObjectInputStream ois = null;
        try{
            FileInputStream fis = context.openFileInput("fichier.ser");
            ois = new ObjectInputStream(fis);
            listeMemos = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            ois.close();
        }

        return listeMemos;
    }

    public void serialiserListe() throws Exception {
        ObjectOutputStream oos = null;
        try{
            FileOutputStream fos = context.openFileOutput("fichier.ser", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(listeMemos);
        }
        finally {
            oos.close();
        }
    }
}
