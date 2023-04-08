package com.example.accesa_application.domain;

public class Response<ID> extends Entity<ID>{
    private ID idQuest;

    private ID idUser;
    private String message;
    private String status;

    public Response(ID idUser,ID idQuest, String message, String status) {
        this.idUser = idUser;
        this.idQuest = idQuest;
        this.message = message;
        this.status = status;

    }

    public ID getIdUser() {
        return idUser;
    }

    public void setIdUser(ID idUser) {
        this.idUser = idUser;
    }
    public String getMessage() {
        return message;
    }



    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ID getIdQuest() {
        return idQuest;
    }
    public void setIdQuest(ID idQuest) {
        this.idQuest = idQuest;
    }
}
