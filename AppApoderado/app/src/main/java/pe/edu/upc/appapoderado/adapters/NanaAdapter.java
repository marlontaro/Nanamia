package pe.edu.upc.appapoderado.adapters;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.widget.ANImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pe.edu.upc.appapoderado.AppApoderado;
import pe.edu.upc.appapoderado.R;
import pe.edu.upc.appapoderado.activities.PrincipalActivity;
import pe.edu.upc.appapoderado.activities.SeleccionarnanaActivity;
import pe.edu.upc.appapoderado.models.Nana;
import pe.edu.upc.appapoderado.models.Usuario;
import pe.edu.upc.appapoderado.network.NanaApi;
import pe.edu.upc.appapoderado.util.Preferencias;

/**
 * Created by Marlon Cordova on 3/05/2017.
 */

public class NanaAdapter extends RecyclerView.Adapter<NanaAdapter.ViewHolder> {
    private List<Nana> services;
    public void setService(List<Nana> service) { this.services = service; }
    private String TAG="SOEN";
    @Override
    public NanaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.nana_source,parent, false);
        NanaAdapter.ViewHolder viewHolder = new NanaAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NanaAdapter.ViewHolder holder, final int position) {

        holder.nameTextView.setText(services.get(position).getNombre()+ " " + services.get(position).getApellido());
        holder.edadTextView.setText(String.valueOf( services.get(position).getEdad()) + " años");
        holder.puntajeTextView.setText(String.valueOf( services.get(position).getPuntaje()) );

        holder.pictureANImageView.setErrorImageResId(R.drawable.avatar);
        holder.pictureANImageView.setDefaultImageResId(R.drawable.avatar);
        holder.pictureANImageView.setImageUrl(services.get(position).getAvatar());

        //holder.logoImageView.setImageResource(services.get(position).getImage());
       holder.serviceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //  Log.d("SOENAPP",String.valueOf(position));





                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("tipo", "1");
                    jsonObject.put("nombre", "");
                    jsonObject.put("apellido", "");
                    jsonObject.put("celular", "");
                    jsonObject.put("dni", "");
                    jsonObject.put("correo", "");
                    jsonObject.put("password", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking.post(NanaApi.SOLICITUD_URL)
                        .addJSONObjectBody(jsonObject)
                        .setPriority(Priority.LOW)
                        .setTag(TAG)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("estado").equalsIgnoreCase("error")) {
                                        Log.d(TAG, response.getString("mensaje"));

                                        Toast.makeText(view.getContext(), response.getString("mensaje"),
                                                Toast.LENGTH_SHORT).show();

                                        return;
                                    }else{

                                        //crear objeto usuario

                                       
                                        Intent serviceIntent = new Intent(view.getContext(), PrincipalActivity.class);

                                        view.getContext().startActivity(serviceIntent);
                                        Toast.makeText(view.getContext(),"Se registro solicitud",Toast.LENGTH_SHORT);

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d(TAG, anError.getLocalizedMessage());
                            }
                        });

            }
        });

        holder.imbOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(v.getContext(),"ew",Toast.LENGTH_SHORT);

            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public List<Nana> getServices() {
        return services;
    }

    public void setServices(List<Nana> services) {
        this.services = services;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView serviceCardView;
        ANImageView pictureANImageView;
        TextView nameTextView;
        TextView edadTextView;
        TextView puntajeTextView;
        ImageView imbOk;
        public ViewHolder(View itemView) {
            super(itemView);
            serviceCardView = (CardView) itemView.findViewById(R.id.serviceCardView);
            pictureANImageView = (ANImageView) itemView.findViewById(R.id.pictureANImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            edadTextView = (TextView) itemView.findViewById(R.id.edadTextView);
            puntajeTextView = (TextView) itemView.findViewById(R.id.puntajeTextView);
            imbOk = (ImageView)itemView.findViewById(R.id.imbOk);

        }
    }
}