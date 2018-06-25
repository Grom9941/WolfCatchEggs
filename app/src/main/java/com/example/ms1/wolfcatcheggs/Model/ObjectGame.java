package com.example.ms1.wolfcatcheggs.Model;

public class ObjectGame {
    public Byte getLife() {
        return life;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    private Byte life = 3;
    private Integer account = 0;

    public void setLife(Byte neww) {
        life=neww;
    }

    public void setAccount(){
        account++;
    }
}
