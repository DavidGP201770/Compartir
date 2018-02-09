package compartir.unitec.org.compartir;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ActividadEnviarMensaje extends AppCompatActivity {

    /**
     * The text to share.
     */
    private String mCuerpo;

    /**
     * The ID of the contact to share the text with.
     */
    private int miId;

    // View references.
    private TextView textoNombreContacto;
    private TextView textoCuerpoMensaje;

    private static final int PETICION_ID_CONTACTO = 1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviar_mensaje);


        setTitle("Enviando mensaje");
        // View references.
        textoNombreContacto= (TextView) findViewById(R.id.nombre_contacto);
        textoCuerpoMensaje = (TextView) findViewById(R.id.cuerpo_mensaje);
        // Resolve the share Intent.
        boolean resolved = resolverIntent(getIntent());
        if (!resolved) {
            finish();
            return;
        }

        // Bind event handlers.
        findViewById(R.id.enviar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar();
            }
        });
        // Set up the UI.
        prepararUi();
        // The contact ID will not be passed on when the user clicks on the app icon rather than any
        // of the Direct Share icons. In this case, we show another dialog for selecting a contact.
        if (miId == Contacto.INVALID_ID) {
            seleccionarContacto();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PETICION_ID_CONTACTO:
                if (resultCode == RESULT_OK) {
                    miId = data.getIntExtra(Contacto.ID, Contacto.INVALID_ID);
                }
                // Give up sharing the send_message if the user didn't choose a contact.
                if (miId == Contacto.INVALID_ID) {
                    finish();
                    return;
                }
                prepararUi();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private boolean resolverIntent(Intent intent) {
        if (Intent.ACTION_SEND.equals(intent.getAction()) &&
                "text/plain".equals(intent.getType())) {
            mCuerpo = intent.getStringExtra(Intent.EXTRA_TEXT);
            miId = intent.getIntExtra(Contacto.ID, Contacto.INVALID_ID);
            return true;
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void prepararUi() {
        if (miId != Contacto.INVALID_ID) {
            Contacto contact = Contacto.porId(miId);
            EnlazarContacto.bind(contact, textoNombreContacto);
        }
        textoCuerpoMensaje.setText(mCuerpo);
    }


    private void seleccionarContacto() {
        Intent intent = new Intent(this, ActividadSeleccionarContacto.class);
        intent.setAction(ActividadSeleccionarContacto.ACTION_SELECT_CONTACT);
        startActivityForResult(intent, PETICION_ID_CONTACTO);
    }

    private void enviar() {
        Toast.makeText(this,
                "Mensaje enviado"+ mCuerpo+ " "+ Contacto.porId(miId).getNombre(),
                Toast.LENGTH_SHORT).show();
        finish();
    }
}