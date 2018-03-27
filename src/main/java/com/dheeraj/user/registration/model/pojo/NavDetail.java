package com.dheeraj.user.registration.model.pojo;

import lombok.Data;

@Data
public class NavDetail {
    /*
    Scheme Code;ISIN Div Payout/ ISIN Growth;ISIN Div Reinvestment;Scheme Name;Net Asset Value;Repurchase Price;Sale Price;Date
     */

    String schemeCode;
    String isin;
    String isinDivReinvestment;
    String schemeName;
    String nav;
    String repurchasePrice;
    String salePrice;
    String date;

    public NavDetail(String[] vals) {

        if (vals.length < 8) {
            return;
        }
        this.schemeCode = vals[0];
        this.isin = vals[1];
        this.isinDivReinvestment = vals[2];
        this.schemeName = vals[3];
        this.nav = vals[4];
        this.repurchasePrice = vals[5];
        this.salePrice = vals[6];
        this.date = vals[7];

    }
}
