package compartir.unitec.org.compartir;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.service.chooser.ChooserTarget;
import android.service.chooser.ChooserTargetService;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by campitos on 2/7/18.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class ServicioSeleccionarContacto extends ChooserTargetService {

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
