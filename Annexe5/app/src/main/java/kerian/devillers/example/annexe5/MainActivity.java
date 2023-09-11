package kerian.devillers.example.annexe5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    SimpleAdapter adapteurListe;
    ListView listeChansons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listeChansons = findViewById(R.id.listeChansons);
        try {
            adapteurListe = new SimpleAdapter((Context) this,
                    lireInfoChansons(),
                    R.layout.layout_chanson,
                    new String[]{"date", "image", "position", "nom"},
                    new int[]{R.id.dateChanson, R.id.imageChanson, R.id.idChanson, R.id.titreChanson}
            );
            listeChansons.setAdapter(adapteurListe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    protected Vector<HashMap<String, Object>> lireInfoChansons() throws IOException, ClassNotFoundException {
        Vector<HashMap<String, Object>> listeInfoChansons = new Vector<>();
        boolean loop = true;
        ObjectInputStream ois = new ObjectInputStream(getResources().openRawResource(R.raw.palmares));

        while(loop == true){
            try {
                listeInfoChansons.add((HashMap<String, Object>) ois.readObject());
            }catch (EOFException e){
                loop = false;
            }
        }

        ois.close();
        return listeInfoChansons;
    }



    //Ex créer fichier
    /*
        try(FileOutputStream is = openFileOutput("palmares.ser", Context.MODE_PRIVATE);
        ObjectOutputStream ois = new ObjectOutputStream(is)) {
            HashMap<String, Object> un = new HashMap();
            un.put("position", "3");
            un.put("nom", "Touch Me");
            un.put("date", "22/03/86");
            un.put("image", R.drawable.touchme);
            ois.writeObject(un);

            HashMap<String, Object> deux = new HashMap();
            deux.put("position", "8");
            deux.put("nom", "Nothing's gonna stop me now");
            deux.put("date", "30/05/86");
            deux.put("image", R.drawable.nothing);
            ois.writeObject(deux);

            HashMap<String, Object> trois = new HashMap();
            trois.put("position", "31");
           trois.put("nom", "Santa Maria");
            trois.put("date", "28/03/1998");
            trois.put("image", R.drawable.santamaria);
            ois.writeObject(trois);

            HashMap<String, Object> quatre = new HashMap();
            quatre.put("position", "108");
            quatre.put("nom", "Hot boy");
            quatre.put("date", "10/04/2018");
            quatre.put("image", R.drawable.hotboy);
            ois.writeObject(quatre);
        }
        catch ( Exception e)
        {
            e.printStackTrace();
        }
*/
}