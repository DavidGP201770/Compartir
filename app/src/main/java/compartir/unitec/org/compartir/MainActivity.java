package compartir.unitec.org.compartir;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.chooser.ChooserTarget;
import android.service.chooser.ChooserTargetService;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText textoCuerpo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textoCuerpo = (EditText) findViewById(R.id.mensaje);
        findViewById(R.id.compartir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, textoCuerpo.getText().toString());
                startActivity(Intent.createChooser(sharingIntent, "Enviando Mensaje"));
            }
        });
    }

    /**
     * Created by campitos on 2/7/18.
     */

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static class ServicioSeleccionarContacto extends ChooserTargetService {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public List<ChooserTarget> onGetChooserTargets(ComponentName targetActivityName,
                                                       IntentFilter matchedFilter) {
            ComponentName nombreComponente = new ComponentName(getPackageName(),
                    ActividadEnviarMensaje.class.getCanonicalName());

            ArrayList<ChooserTarget> targets = new ArrayList<>();
            for (int i = 0; i < Contacto.CONTACTOS.length; ++i) {
                Contacto contact = Contacto.porId(i);
                Bundle extras = new Bundle();
                extras.putInt(Contacto.ID, i);
                targets.add(new ChooserTarget(contact.getNombre(), Icon.createWithResource(this, contact.getIcon()), 0.5f, nombreComponente, extras));
            }
            return targets;
        }
    }

    /**
     * Created by campitos on 2/7/18.
     */

    public static class EnlazarContacto {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public static void bind(Contacto contacto, TextView textView) {
            textView.setText(contacto.getNombre());
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(contacto.getIcon(), 0, 0, 0);
        }
    }

    public static class Contacto {

        /**
         * Lista de contactos ficticios
         */
        public static final Contacto[] CONTACTOS = {
                new Contacto("Juan"),
                new Contacto("Ana"),
                new Contacto("Pedro"),
                new Contacto("Maria"),

        };


        public static final String ID = "id_contacto";


        public static final int INVALID_ID = -1;


        private final String mNombre;


        public Contacto(String nombre) {
            mNombre = nombre;
        }



        public static Contacto porId(int id) {
            return CONTACTOS[id];
        }


        public String getNombre() {
            return mNombre;
        }


        public int getIcon() {
            return R.mipmap.avatar;
        }

    }
}
