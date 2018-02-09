package compartir.unitec.org.compartir;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActividadSeleccionarContacto extends AppCompatActivity {


    public static final String ACTION_SELECT_CONTACT
            = "compartir.org.unitec.compartir.intent.action.SELECT_CONTACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccionar_contacto);

        Intent intent = getIntent();
        if (!ACTION_SELECT_CONTACT.equals(intent.getAction())) {
            finish();
            return;
        }
        // Set up the list of contacts
        ListView list = (ListView) findViewById(R.id.lista);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(mOnItemClickListener);
    }


    private final ListAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return Contacto.CONTACTOS.length;
        }

        @Override
        public Object getItem(int i) {
            return Contacto.porId(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public View getView(int i, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seleccionar_contacto, parent,
                        false);
            }
            TextView textView = (TextView) view;
            Contacto contacto = (Contacto) getItem(i);
            EnlazarContacto.bind(contacto, textView);
            return textView;
        }
    };

    private final AdapterView.OnItemClickListener mOnItemClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent data = new Intent();
            data.putExtra(Contacto.ID, i);
            setResult(RESULT_OK, data);
            finish();
        }
    };
}