package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Player implements Parcelable{
    @NonNull
    @PrimaryKey
    public String uid;

    @ColumnInfo(name = "nickname")
    public String nickname;

    @ColumnInfo(name = "puntuacio")
    public int puntuacio;

    @ColumnInfo(name = "partides")
    public int partides;

    public Player(String nickname) {
        this.nickname = nickname;
        this.partides = 0;
        this.puntuacio = 0;
        this.uid = UUID.randomUUID().toString();
    }

    protected Player(Parcel in){
        super();
        readFromParcel(in);
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel parcel) {
            return new Player(parcel);
        }

        @Override
        public Player[] newArray(int i) {
            return new Player[i];
        }
    };

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }

    public int getPartides() {
        return partides;
    }

    public void setPartides(int partides) {
        this.partides = partides;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nickname);
        parcel.writeInt(puntuacio);
    }
    private void readFromParcel(Parcel in){
        nickname = in.readString();
        puntuacio =  in.readInt();
    }
}
