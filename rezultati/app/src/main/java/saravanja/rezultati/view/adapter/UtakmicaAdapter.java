package saravanja.rezultati.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import saravanja.rezultati.R;
import saravanja.rezultati.model.Utakmica;

public class UtakmicaAdapter extends ArrayAdapter<Utakmica> {

    private List<Utakmica> itemList;
    private UtakmicaClickListener utakmicaClickListener;
    private int resource;
    private Context context;


    public UtakmicaAdapter(@NonNull Context context, int resource, saravanja.rezultati.view.adapter.UtakmicaClickListener utakmicaClickListener) {
        super(context, resource);
        this.resource = resource;
        this.context = context;
        this.utakmicaClickListener = utakmicaClickListener;
    }


    private static class ViewHolder {

        private TextView id;
        private TextView imeDomaci;
        private TextView imeGosti;
        private TextView rezultat;
        private ImageView imagePath;
        private TextView datum;
    }


    @androidx.annotation.NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {

        View view = convertView;
        Utakmica utakmica;
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(this.resource, null);

                viewHolder.id = view.findViewById(R.id.id);
                viewHolder.imeDomaci = view.findViewById(R.id.imeDomaci);
                viewHolder.imeGosti = view.findViewById(R.id.imeGosti);
                viewHolder.rezultat = view.findViewById(R.id.rezultat);
                viewHolder.imagePath = view.findViewById(R.id.urlSlika);
                viewHolder.datum = view.findViewById ( R.id.datum);

            } else {
                viewHolder = (ViewHolder) view.getTag();

            }

            utakmica = getItem(position);

            if (utakmica != null) {

                viewHolder.id.setText(String.valueOf(utakmica.getId()));
                viewHolder.imeDomaci.setText(String.valueOf(utakmica.getImeDomaci()));
                viewHolder.imeGosti.setText(String.valueOf(utakmica.getImeGosti()));
                viewHolder.rezultat.setText(String.valueOf (utakmica.getRezultat()));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd. MM. yyyy.");
                try {
                    viewHolder.datum.setText(simpleDateFormat.format(utakmica.getDatum()));
                }catch (Exception e){
                    viewHolder.datum.setText("");
                }

                if (utakmica.getUrlSlike() == null) {
                    Picasso.get().load(R.drawable.stadion).fit().centerCrop().into(viewHolder.imagePath);
                } else {
                    Picasso.get().load(utakmica.getUrlSlike()).fit().centerCrop().into(viewHolder.imagePath);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { utakmicaClickListener.onItemClick(utakmica); }
            });
        }
        return view;



    }

    @Override
    public int getCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Nullable
    @Override
    public Utakmica getItem(int position) {
        return itemList.get(position);
    }

    public void setItemList(List<Utakmica> itemList) {
        this.itemList = itemList;
    }

    public void refreshItem() {
        notifyDataSetChanged();
    }






}
