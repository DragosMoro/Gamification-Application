package com.example.accesa_application.domain;

public class Reward<ID> extends Entity<ID>{
    private ID idUser;
    private ID idItem;

    public Reward(ID idUser, ID idItem) {
        this.idUser = idUser;
        this.idItem = idItem;
    }
    public ID getIdUser() {
        return idUser;
    }

    public void setIdUser(ID idUser) {
        this.idUser = idUser;
    }

    public ID getIdItem() {
        return idItem;
    }

    public void setIdItem(ID idItem) {
        this.idItem = idItem;
    }
}
