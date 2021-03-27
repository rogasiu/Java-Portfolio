package com.example.puzzle;

public class Wyniki {
    int _id;
    String _nickName;
    CharSequence _time;
    public Wyniki(){}
    public Wyniki(int id, String nick, CharSequence time){
        this._id = id;
        this._nickName = nick;
        this._time = time;
    }
    public Wyniki(String nick, CharSequence time) {
        this._nickName = nick;
        this._time = time;
    }
    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getNickName() {
        return _nickName;
    }

    public void setNickName(String _nickName) {
        this._nickName = _nickName;
    }

    public CharSequence getTime() {
        return _time;
    }

    public void setTime(CharSequence _time) {
        this._time = _time;
    }


}
