package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfessionalListResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("response_message")
    private String response_message;

    @SerializedName("Data")
    @Expose
    private ProfessionalListDataResponse data;

    public String getResponse_message() {
        return response_message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProfessionalListDataResponse getData() {
        return data;
    }

    public void setData(ProfessionalListDataResponse data) {
        this.data = data;
    }

}
