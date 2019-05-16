  package com.anchor.helper;

import android.content.Context;
import android.content.SharedPreferences;

  /**
   * Created by Lincoln on 05/05/16.
   */
  public class PrefManager {
      SharedPreferences pref;
      SharedPreferences.Editor editor;
      Context _context;

      // shared pref mode
      int PRIVATE_MODE = 0;

      // Shared preferences file name
      private static final String PREF_NAME = "DealerP";

      private String User_Email  = "user_email";
      private String User_name  = "user_name";
      private String User_code  = "user_code";
      private String User_id  = "user_id";
      private String User_mobile  = "user_mobile";
      private String User_address  = "user_address";
      private String User_shop_name  = "user_shop_name";
      private String Customer_service_type  = "customer_service_type";
      private String User_IMEI  = "user_imei";
      private String DocType  = "doc_type";
      private String PrimaryCateg  = "primary_categ";
      private String SubCateg  = "sub_categ";
      private String BussinessUnit  = "bussiness_unit";
      private String BussinessCateg  = "bussiness_categ";
      private String Ftp_user_id  = "ftp_user_id";
      private String Ftp_user_password  = "ftp_user_password";
      private String Birthdate  = "Birthdate";
      private String Aniversarydate  = "Aniversarydate";
      private String Fcm_Token  = "fcmtoken";
      public PrefManager(Context context) {
          this._context = context;
          pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
          editor = pref.edit();
      }

      public String getFcm_Token() {
          return pref.getString(Fcm_Token, "");
      }

      public void setFcm_Token(String Fcm_Tokenn) {
          editor.putString(Fcm_Token, Fcm_Tokenn);
          editor.commit();
      }

      public String getAniversarydate() {
          return pref.getString(Aniversarydate, "");
      }

      public void setAniversarydate(String Aniversarydaten) {
          editor.putString(Aniversarydate, Aniversarydaten);
          editor.commit();
      }

      public String getBirthdate() {
          return pref.getString(Birthdate, "");
      }

      public void setBirthdate(String Birthdaten) {
          editor.putString(Birthdate, Birthdaten);
          editor.commit();
      }

      public String getFtp_user_id() {
          return pref.getString(Ftp_user_id, "");
      }

      public void setFtp_user_id(String Ftp_user_idn) {
          editor.putString(Ftp_user_id, Ftp_user_idn);
          editor.commit();
      }

      public String getFtp_user_password() {
          return pref.getString(Ftp_user_password, "");
      }

      public void setFtp_user_password(String Ftp_user_passwordn) {
          editor.putString(Ftp_user_password, Ftp_user_passwordn);
          editor.commit();
      }

      public String getUser_IMEI() {
          return pref.getString(User_IMEI, "");
      }

      public void setUser_IMEI(String User_IMEIn) {
          editor.putString(User_IMEI, User_IMEIn);
          editor.commit();
      }

      public String getUser_id() {
          return pref.getString(User_id, "");
      }

      public void setUser_id(String User_id1) {
          editor.putString(User_id, User_id1);
          editor.commit();
      }

      public String getUser_shop_name() {
          return pref.getString(User_shop_name, "");
      }

      public void setUser_shop_name(String User_shop_namen) {
          editor.putString(User_shop_name, User_shop_namen);
          editor.commit();
      }

      public String getUser_address() {
          return pref.getString(User_address, "");
      }

      public void setUser_address(String User_addressn) {
          editor.putString(User_address, User_addressn);
          editor.commit();
      }

      public String getUser_mobile() {
          return pref.getString(User_mobile, "");
      }

      public void setUser_mobile(String User_mobilen) {
          editor.putString(User_mobile, User_mobilen);
          editor.commit();
      }

      public String getUser_Email() {
          return pref.getString(User_Email, "");
      }

      public void setUser_Email(String User_Emailn) {
          editor.putString(User_Email, User_Emailn);
          editor.commit();
      }

      public String getUser_name() {
          return pref.getString(User_name, "");
      }

      public void setUser_name(String User_namen) {
          editor.putString(User_name, User_namen);
          editor.commit();
      }


      public String getUser_code() {
          return pref.getString(User_code, "");
      }

      public void setUser_code(String User_coden) {
          editor.putString(User_code, User_coden);
          editor.commit();
      }

      public String getCustomer_service_type() {
          return pref.getString(Customer_service_type, "");
      }

      public void setCustomer_service_type(String Customer_service_typen) {
          editor.putString(Customer_service_type, Customer_service_typen);
          editor.commit();
      }

      public String getDocType() {
          return pref.getString(DocType, "");
      }

      public void setDocType(String DocType) {
          editor.putString(DocType, DocType);
          editor.commit();
      }


      public String getPrimaryCateg() {
          return pref.getString(PrimaryCateg, "");
      }

      public void setPrimaryCateg(String PrimaryCateg) {
          editor.putString(PrimaryCateg, PrimaryCateg);
          editor.commit();
      }

      public String getSubCateg() {
          return pref.getString(SubCateg, "");
      }

      public void setSubCateg(String SubCateg) {
          editor.putString(SubCateg, SubCateg);
          editor.commit();
      }


      public String getBussinessUnit() {
          return pref.getString(BussinessUnit, "");
      }

      public void setBussinessUnit(String BussinessUnit) {
          editor.putString(BussinessUnit, BussinessUnit);
          editor.commit();
      }

      public String getBussinessCateg() {
          return pref.getString(BussinessCateg, "");
      }

      public void setBussinessCateg(String BussinessCateg) {
          editor.putString(BussinessCateg, BussinessCateg);
          editor.commit();
      }



  }
