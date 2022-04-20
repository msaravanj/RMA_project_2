package saravanja.rezultati.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "utakmica")
public class Utakmica {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "ime_domaci")
    @NonNull
    private String imeDomaci;

    @ColumnInfo(name = "ime_gosti")
    @NonNull
    private String imeGosti;

    @ColumnInfo(name = "rezultat")
    private String rezultat;

    @ColumnInfo(name = "datum")
    @NonNull
    private Date datum;

    @ColumnInfo(name = "url_slike")
    private String urlSlike;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getImeDomaci() {
        return imeDomaci;
    }

    public void setImeDomaci(@NonNull String imeDomaci) {
        this.imeDomaci = imeDomaci;
    }

    @NonNull
    public String getImeGosti() {
        return imeGosti;
    }

    public void setImeGosti(@NonNull String imeGosti) {
        this.imeGosti = imeGosti;
    }

    public String getRezultat() {
        return rezultat;
    }

    public void setRezultat(String rezultat) {
        this.rezultat = rezultat;
    }

    @NonNull
    public Date getDatum() {
        return datum;
    }

    public void setDatum(@NonNull Date datum) {
        this.datum = datum;
    }

    public String getUrlSlike() {
        return urlSlike;
    }

    public void setUrlSlike(String urlSlike) {
        this.urlSlike = urlSlike;
    }
}
