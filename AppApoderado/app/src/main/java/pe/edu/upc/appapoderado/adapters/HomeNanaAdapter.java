package pe.edu.upc.appapoderado.adapters;


import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import pe.edu.upc.appapoderado.R;
import java.util.List;

import pe.edu.upc.appapoderado.models.Nana;

public class HomeNanaAdapter extends RecyclerView.Adapter<HomeNanaAdapter.ViewHolder> {
    private List<Nana> services;
    public void setService(List<Nana> service) { this.services = service; }

    @Override
    public HomeNanaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.homenana_source,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeNanaAdapter.ViewHolder holder, final int position) {
        holder.nameTextView.setText(services.get(position).getNombre()+ " " + services.get(position).getApellido());
        holder.edadTextView.setText(String.valueOf( services.get(position).getEdad()) + " años");
        holder.puntajeTextView.setText(String.valueOf( services.get(position).getPuntaje()) );

        holder.pictureANImageView.setErrorImageResId(R.drawable.avatar);
        holder.pictureANImageView.setDefaultImageResId(R.drawable.avatar);
        holder.pictureANImageView.setImageUrl(services.get(position).getAvatar());

        //holder.logoImageView.setImageResource(services.get(position).getImage());
        holder.serviceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Log.d("SOENAPP",String.valueOf(position));

                //Intent serviceIntent = new Intent(view.getContext(), ServiceViewActivity.class);
                //serviceIntent.putExtras(bundle);
                //view.getContext().startActivity(serviceIntent);

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
        public ViewHolder(View itemView) {
            super(itemView);
            serviceCardView = (CardView) itemView.findViewById(R.id.serviceCardView);
            pictureANImageView = (ANImageView) itemView.findViewById(R.id.pictureANImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            edadTextView = (TextView) itemView.findViewById(R.id.edadTextView);
            puntajeTextView = (TextView) itemView.findViewById(R.id.puntajeTextView);
        }
    }
}

