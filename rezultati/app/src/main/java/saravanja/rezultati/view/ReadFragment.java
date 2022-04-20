package saravanja.rezultati.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import saravanja.rezultati.R;
import saravanja.rezultati.model.Utakmica;
import saravanja.rezultati.view.adapter.UtakmicaAdapter;
import saravanja.rezultati.view.adapter.UtakmicaClickListener;
import saravanja.rezultati.viewModel.UtakmicaViewModel;


public class ReadFragment extends Fragment {

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lista)
    ListView listView;

    private UtakmicaViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);
        ButterKnife.bind(this,view);

        model = ((MainActivity)getActivity()).getModel();

        defineList ();

        refreshData ();

        return view;
    }

    public void refreshData(){
        model.dohvatiUtakmicu().observe(getViewLifecycleOwner ( ), new Observer<List<Utakmica>>( ) {
            @Override
            public void onChanged(List<Utakmica> utakmice) {
                swipeRefreshLayout.setRefreshing(false);
                ((UtakmicaAdapter)listView.getAdapter()).setItemList(utakmice);
                ((UtakmicaAdapter) listView.getAdapter()).refreshItem();
            }
        } );
    }


    private void defineSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData ();
            }
        });

    }

    private void defineList() {

        listView.setAdapter(new UtakmicaAdapter(getActivity(), R.layout.red_liste, new UtakmicaClickListener() {
            @Override
            public void onItemClick(Utakmica utakmica) {
                model.setUtakmica(utakmica);
                ((MainActivity)getActivity()).cud();
            }
        } ));
    }

    @OnClick(R.id.fab)
    public void novaUtakmica(){
        model.setUtakmica(new Utakmica());
        ((MainActivity)getActivity()).cud();
    }
}
