package com.incognysis.cheque;

import android.widget.Spinner;

/**
 * Created by Charul on 23-12-2017.
 */

public class cheque {
    int id;
    String _type;
    String _bank;
    String _givenTo;
    String _entry_date;
    String _issue_date;
    String _amount;
    String _chequeNo;
    String _status;
    String _notes;
    String _reminder;

//    public cheque(int id, String _type, String _bank, String _givenTo, String _date, String _amount, String _chequeNo, String _status, String _notes, String _reminder) {
//        this.id = id;
//        this._type = _type;
//        this._bank = _bank;
//        this._givenTo = _givenTo;
//        this._date = _date;
//        this._amount = _amount;
//        this._chequeNo = _chequeNo;
//        this._status = _status;
//        this._notes = _notes;
//        this._reminder = _reminder;
//   }

//    public cheque(String _type, String _bank, String _givenTo, String _date, String _amount, String _chequeNo, String _status, String _notes, String _reminder) {
//        this._type = _type;
//        this._bank = _bank;
//        this._givenTo = _givenTo;
//        this._date = _date;
//        this._amount = _amount;
//        this._chequeNo = _chequeNo;
//        this._status = _status;
//        this._notes = _notes;
////        this._reminder = _reminder;
//    }

    public cheque() {
    }



    public cheque(int id, String _type, String _bank, String _givenTo, String _entry_date, String _issue_date, String _amount, String _chequeNo, String _status, String _notes, String _reminder) {
        this.id = id;
        this._type = _type;
        this._bank = _bank;
        this._givenTo = _givenTo;
        this._entry_date = _entry_date;
        this._issue_date = _issue_date;
        this._amount = _amount;
        this._chequeNo = _chequeNo;
        this._status = _status;
        this._notes = _notes;
        this._reminder = _reminder;
    }

    public cheque(String _type, String _bank, String _givenTo, String _entry_date, String _issue_date, String _amount, String _chequeNo, String _status, String _notes, String _reminder) {
        this._type = _type;
        this._bank = _bank;
        this._givenTo = _givenTo;
        this._entry_date = _entry_date;
        this._issue_date = _issue_date;
        this._amount = _amount;
        this._chequeNo = _chequeNo;
        this._status = _status;
        this._notes = _notes;
        this._reminder = _reminder;
    }

//    public cheque(int id, String _type, String _bank, String _givenTo, String _date, String _amount, String _chequeNo, String _status, String _notes) {
//        this.id = id;
//        this._type = _type;
//        this._bank = _bank;
//        this._givenTo = _givenTo;
//        this._date = _date;
//        this._amount = _amount;
//        this._chequeNo = _chequeNo;
//        this._status = _status;
//        this._notes = _notes;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_bank() {
        return _bank;
    }

    public void set_bank(String _bank) {
        this._bank = _bank;
    }

    public String get_givenTo() {
        return _givenTo;
    }

    public void set_givenTo(String _givenTo) {
        this._givenTo = _givenTo;
    }

    public String get_amount() {
        return _amount;
    }

    public void set_amount(String _amount) {
        this._amount = _amount;
    }

    public String get_chequeNo() {
        return _chequeNo;
    }

    public void set_chequeNo(String _chequeNo) {
        this._chequeNo = _chequeNo;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_notes() {
        return _notes;
    }

    public void set_notes(String _notes) {
        this._notes = _notes;
    }

    public String get_reminder() {
        return _reminder;
    }

    public void set_reminder(String _reminder) {
        this._reminder = _reminder;
    }

    public String get_entry_date() {
        return _entry_date;
    }

    public void set_entry_date(String _entry_date) {
        this._entry_date = _entry_date;
    }

    public String get_issue_date() {
        return _issue_date;
    }

    public void set_issue_date(String _issue_date) {
        this._issue_date = _issue_date;
    }
}
