package saravanja.rezultati.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import saravanja.rezultati.R;
import saravanja.rezultati.model.Utakmica;
import saravanja.rezultati.viewModel.UtakmicaViewModel;

public class CUDFragment extends Fragment {

    static final int flag = 1;


    @BindView(R.id.imeDomaci)
    EditText imeDomaci;
    @BindView(R.id.imeGosti)
    EditText imeGosti;
    @BindView(R.id.rezultat)
    EditText rezultat;
    @BindView(R.id.datum)
    DatePicker datum;
    @BindView(R.id.slika)
    ImageView slika;
    @BindView(R.id.novaUtakmica)
    Button novaUtakmica;
    @BindView(R.id.uslikaj)
    Button uslikaj;
    @BindView(R.id.promijeni)
    Button promijeni;
    @BindView(R.id.obrisi)
    Button obrisi;

    UtakmicaViewModel model;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cud,
                container, false);
        ButterKnife.bind(this, view);
        model = ((MainActivity) getActivity()).getModel();
        Picasso picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);

        picasso.load(R.drawable.stadion).fit().centerCrop().into(slika);


        if (model.getUtakmica().getId() == 0) {
            defineNewFish();
            return view;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getUtakmica().getDatum());
        imeDomaci.setText(model.getUtakmica().getImeDomaci());
        imeGosti.setText(model.getUtakmica().getImeGosti());
        rezultat.setText(model.getUtakmica().getRezultat());
        datum.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        defineChangeDeleteFish ();

        return view;
    }


    private void defineNewFish() {
        promijeni.setVisibility(View.GONE);
        obrisi.setVisibility(View.GONE);
        uslikaj.setVisibility(View.GONE);
        novaUtakmica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novaUtakmica();
            }
        });
    }

    private void novaUtakmica() {
        model.getUtakmica().setImeDomaci(imeDomaci.getText().toString());
        model.getUtakmica().setImeGosti(imeGosti.getText().toString());
        model.getUtakmica().setRezultat(rezultat.getText().toString());
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,datum.getDayOfMonth());
        c.set(Calendar.MONTH, datum.getMonth());
        c.set(Calendar.YEAR, datum.getYear());
        model.getUtakmica().setDatum(c.getTime());
        model.addNewUtakmica();
        natrag();
    }

    private void defineChangeDeleteFish() {
        Utakmica utakm = model.getUtakmica();
        novaUtakmica.setVisibility(View.GONE);
        imeDomaci.setText(utakm.getImeDomaci());
        imeGosti.setText(utakm.getImeGosti());
        rezultat.setText(utakm.getRezultat());
        Log.d("Putanja slika", "->" + utakm.getUrlSlike());
        if (utakm.getUrlSlike() != null) {
            Picasso.get().load(utakm.getUrlSlike()).fit().centerCrop().into(slika);
        }

        promijeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promijeniUtakmicu();
            }
        });

        obrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obrisiUtakmicu();
            }
        });

        uslikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                napraviFoto();
            }
        });
    }

    private void napraviFoto() {
        Intent uslikajIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(uslikajIntent.resolveActivity(getActivity().getPackageManager())==null){
            //poruke korisniku
            return;
        }

        File slika= null;
        try{
            slika = createImageFile();
        }catch (IOException e){

            return;
        }

        if(slika == null){

            return;
        }

        Uri slikaUri = FileProvider.getUriForFile(getActivity(),
                "com.saravanja.provider",
                slika);
        uslikajIntent.putExtra(MediaStore.EXTRA_OUTPUT, slikaUri);
        startActivityForResult(uslikajIntent, flag);

    }

    private File createImageFile() throws IOException {
        String naziv = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_osoba";
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File datoteka = File.createTempFile(naziv,".jpg",dir);
        model.getUtakmica().setUrlSlike("file:" + datoteka.getAbsolutePath());
        return datoteka;
    }


    private void promijeniUtakmicu(){
        model.getUtakmica().setImeDomaci(imeDomaci.getText().toString());
        model.getUtakmica().setImeGosti(imeGosti.getText().toString());
        model.getUtakmica().setRezultat(rezultat.getText().toString());
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, datum.getDayOfMonth());
        c.set(Calendar.MONTH, datum.getMonth());
        c.set(Calendar.YEAR, datum.getYear());
        model.getUtakmica().setDatum(c.getTime());
        model.changeUtakmica();
        natrag();
    }

    private void obrisiUtakmicu(){
        model.deleteUtakmica();
        natrag();
    }



    @OnClick(R.id.natrag)
    public void natrag(){
        ((MainActivity)getActivity()).read();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == flag && resultCode == Activity.RESULT_OK){
            model.changeUtakmica();
            Picasso.get().load(model.getUtakmica().getUrlSlike()).fit().centerCrop().into(slika);
        }
    }
}
