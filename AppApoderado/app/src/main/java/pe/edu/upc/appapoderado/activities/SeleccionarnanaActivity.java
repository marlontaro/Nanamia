package pe.edu.upc.appapoderado.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.appapoderado.R;
import pe.edu.upc.appapoderado.adapters.HomeNanaAdapter;
import pe.edu.upc.appapoderado.adapters.NanaAdapter;
import pe.edu.upc.appapoderado.models.Nana;
import pe.edu.upc.appapoderado.network.NanaApi;

public class SeleccionarnanaActivity extends AppCompatActivity {

    RecyclerView servicesRecyclerView;
    RecyclerView.LayoutManager servicesLayoutManager;
    NanaAdapter serviceAdapter;
    List<Nana> services;
    String TAG="SOEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionarnana);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        services =  new ArrayList<>();
        servicesLayoutManager = new LinearLayoutManager(this);
        serviceAdapter = new NanaAdapter();
        serviceAdapter.setService(services);
        servicesRecyclerView = (RecyclerView)findViewById(R.id.servicesRecyclerView);
        servicesRecyclerView.setLayoutManager(servicesLayoutManager);
        servicesRecyclerView.setAdapter(serviceAdapter);

        Update();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void Update(){
        AndroidNetworking.get(NanaApi.NANA_URL)
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG,response.toString());
                        services =  new ArrayList<>();

                        if (response != null) {

                            int length = response.length();

                            for(int i = 0; i < length; i++) {
                                try {
                                    services.add(Nana.build(response.getJSONObject(i)));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            serviceAdapter.setService(services);
                            serviceAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                }) ;
    }
}
