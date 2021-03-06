package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerGlobal implements Parcelable, Comparable<PlayerGlobal>{
    public String nickname;
    public int puntuacio;
    public int partides;

    public PlayerGlobal(String nickname, int puntuacio, int partides) {
        this.nickname = nickname;
        this.puntuacio = puntuacio;
        this.partides = partides;
    }

    protected PlayerGlobal(Parcel in){
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<PlayerGlobal> CREATOR = new Parcelable.Creator<PlayerGlobal>() {
        @Override
        public PlayerGlobal createFromParcel(Parcel parcel) {
            return new PlayerGlobal(parcel);
        }

        @Override
        public PlayerGlobal[] newArray(int i) {
            return new PlayerGlobal[i];
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
        parcel.writeInt(partides);
    }
    private void readFromParcel(Parcel in){
        nickname = in.readString();
        puntuacio =  in.readInt();
        partides = in.readInt();
    }

    // Overriding compareTo() method
    @Override
    public int compareTo(PlayerGlobal p) {
        return Integer.compare(p.getPuntuacio(), this.getPuntuacio());
    }
}
