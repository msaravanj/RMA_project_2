package saravanja.rezultati.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import saravanja.rezultati.model.Utakmica;
import saravanja.rezultati.model.dao.UtakmicaBaza;
import saravanja.rezultati.model.dao.UtakmicaDAO;


public class UtakmicaViewModel extends AndroidViewModel {

    private Utakmica utakmica;
    UtakmicaDAO utakmicaDAO;
    private LiveData<List<Utakmica>> utakmice;

    public Utakmica getUtakmica() {
        return utakmica;
    }

    public void setUtakmica(Utakmica utakmica) {
        this.utakmica = utakmica;
    }

    public UtakmicaDAO getUtakmicaDAO() {
        return utakmicaDAO;
    }

    public void setUtakmicaDAO(UtakmicaDAO utakmicaDAO) {
        this.utakmicaDAO = utakmicaDAO;
    }

    public LiveData<List<Utakmica>> getUtakmice() {
        return utakmice;
    }

    public void setUtakmice(LiveData<List<Utakmica>> utakmice) {
        this.utakmice = utakmice;
    }

    public UtakmicaViewModel(@NonNull Application application) {
        super(application);
        utakmicaDAO = UtakmicaBaza.getInstance(application.getApplicationContext()).utakmicaDAO();
    }


    public LiveData<List<Utakmica>> dohvatiUtakmicu(){
        utakmice = utakmicaDAO.getUtakmica();
        return utakmice;
    }

    public void addNewUtakmica(){ utakmicaDAO.addNewUtakmica(utakmica); }

    public void changeUtakmica(){ utakmicaDAO.changeUtakmica(utakmica); }

    public void deleteUtakmica(){
        utakmicaDAO.deleteUtakmica(utakmica);
    }

}
