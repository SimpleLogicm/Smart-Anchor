package com.anchor.activities;

public class Local_Data {
	String cust_mob;
    String curr_date;
    String LEGACY_CUSTOMER_CODE;
    String sync_time ;
    String total_sync;
    String editable;
    String mandatory;
    String allow;
    String DISTRIBUTER_ID;
    String var_label;
    String new_label;
    String Signature_image;
	String CUSTOMER_NAME;
	String CUSTOMER_SHOPNAME;
	String ADDRESS;
	String STREET;
	String LANDMARK;
	String PIN_CODE;
	String LANDLINE_NO;
	String MOBILE_NO;
	String EMAIL_ADDRESS;
	String STATUS;
	String STATE_ID;
	String CITY_ID;
	String BEAT_ID;
	String vatin;
	String email;

    String calender_id;
    String calender_type;
    String from_date;
    String to_date;
    String calender_details;
    String latlon;


	
	String prod_name;
	String reason_name;
	String reason_code;
	String c_name;
	String rrp;
	String item_code;
	String cust_code;
	String product_name;
	String productid;
	String user_email;
	String PURPOSE_ADDRESS;
	String order_number;
	String ordamount;
	String orderstatusid;
	String orderid;
	String qtyy;
	String amount;
	String price;
	String rp;
	int _id;
    String username;
    String password;
    String imei_no;
    String project_id;
    String user_id,beat_name,beat_id;
    String client_id;
    String cur_date;
    String cur_time;
    String prod_stat;
    String regid;
    String state_name,city_name,city_id,category,product,variant;
    String description;
    String claims;
    String claims_amount;
    String complaints;
    String media_type;
    String cust_adr;
    String media_url;
    String media_disc;
    String Target_Year;
    String Target_Month;
    String Target_Text;
    String Target_Achieved;
    String Target_Quarter;
    
    String travel_from;
    String travel_to;
    String travel_date;
    String travel_mode;
    String travel_cost;
    String travel_text;
    String Retailer_id;
    
    String category_id;
    String category_code;
    String category_desc;
    String category_status;
    String category_ids;
    
    String product_code;
    String product_desc;
    String product_status;
    String product_ids;

    String variants_code;
    String variants_desc;
    String variants_MRP;
    String variants_retail_price;
    String variants_product_unit;
    String variants_status;
    String variants_ids;

    String stocks_product_name;
    String stocks_product_quantity;
    String stocks_product_text;
    
    String PackSizes_variant_id;
    String PackSizes_desc;
    String PackSizes_product_unit;
    String PackSizes_product_pack_size;
    String PackSizes_status;
    String PackSizes_ids;
    
    String RR,MRP,Dstr;
    
    String shedule_order_id,shedule_depatch_date,shedule_delivery_date,shedule_order_amount,shedule_payment_mode,shedule_outstanding_amount;
    String delivery_product_id,delivery_product_order_quantity,delivery_product_delivered_quality,delivery_product_transporter_details;
    
    String credit_limit;
    String Address;

    String Ammount_overdue;

    String reporting_to;
    String first_name;
    String last_name;
    String latitude;
    String longitude;
    String datetime1;
    String date1;
    String time1;

    String survey_code;
    String question_code;
    String active_from;
    String active_to;
    String question;
    String option_1;
    String option_2;
    String option_3;
    String option_4;
    String option_5;
    String answer_date;
    String customer_choice;
    String reason_type;
    String misc_date;
    String misc_cost;
    String misc_text;
    String calender_delete_flag;
    String media_id;
    String Title;
    String Heading;
    String Sub_Heading;
    String L1_Address;
    String L1_phone1;
    String L1_phone2;
    String L1_email_id1;
    String L1_email_id2;
    String L1_website;
    String code;
    String sche_type;
    String sche_name;
    String sche_disname;
    String sche_discription;
    String ware_name;
    String sub_categ;
    String img_ordersign;
    String order_type_name;
    String order_detail1;
    String order_detail2;

    public String getCur_date() {
        return curr_date;
    }

    public void setCur_date(String curdate) {
        this.curr_date = curdate;
    }

    public String getCust_mob(){
        return cust_mob;
    }

    public void setCust_mob(String cust_mb){
        this.cust_mob=cust_mb;
    }

    public String getEditable(){
        return editable;
    }

    public void setEditable(String edt){
        this.editable=edt;
    }

    public String getMandatory(){
        return mandatory;
    }

    public void setMandatory(String mandate){
        this.mandatory=mandate;
    }

    public String getAllow(){
        return allow;
    }

    public void setAllow(String allw){
        this.allow=allw;
    }

    public String getOrder_category_type() {
        return order_category_type;
    }

    public void setOrder_category_type(String order_category_type) {
        this.order_category_type = order_category_type;
    }

    public String getOrder_detail3() {

        return order_detail3;
    }

    public void setOrder_detail3(String order_detail3) {
        this.order_detail3 = order_detail3;
    }

    public String getOrder_detail2() {

        return order_detail2;
    }

    public void setOrder_detail2(String order_detail2) {
        this.order_detail2 = order_detail2;
    }

    public String getOrder_detail1() {

        return order_detail1;
    }

    public void setOrder_detail1(String order_detail1) {
        this.order_detail1 = order_detail1;
    }

    String order_detail3;
    String order_category_type;

    public String getOrder_type_code() {
        return order_type_code;
    }

    public void setOrder_type_code(String order_type_code) {
        this.order_type_code = order_type_code;
    }

    String order_type_code;

    public String getOrder_type_desc() {
        return order_type_desc;
    }

    public void setOrder_type_desc(String order_type_desc) {
        this.order_type_desc = order_type_desc;
    }

    public String getOrder_type_name() {

        return order_type_name;
    }

    public void setOrder_type_name(String order_type_name) {
        this.order_type_name = order_type_name;
    }

    String order_type_desc;

    public String getimg_ordersign() {
        return img_ordersign;
    }

    public void setimg_ordersign(String img_ordersign) {
        this.img_ordersign = img_ordersign;
    }

    public String getSubcateg() {
        return sub_categ;
    }

    public void setSubcateg(String sub_categ) {
        this.sub_categ = sub_categ;
    }


    public String getWare_code() {
        return ware_code;
    }

    public void setWare_code(String ware_code) {
        this.ware_code = ware_code;
    }

    public String getWare_name() {

        return ware_name;
    }

    public void setWare_name(String ware_name) {
        this.ware_name = ware_name;
    }

    String ware_code;

    public String getSche_code() {
        return sche_code;
    }

    public void setSche_code(String sche_code) {
        this.sche_code = sche_code;
    }

    String sche_code;

    public String getSche_type() {
        return sche_type;
    }

    public void setSche_type(String sche_type) {
        this.sche_type = sche_type;
    }

    public String getSche_discription() {
        return sche_discription;
    }

    public void setSche_discription(String sche_discription) {
        this.sche_discription = sche_discription;
    }

    public String getSche_disname() {

        return sche_disname;
    }

    public void setSche_disname(String sche_disname) {
        this.sche_disname = sche_disname;
    }

    public String getSche_name() {

        return sche_name;
    }

    public void setSche_name(String sche_name) {
        this.sche_name = sche_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public String getL1_website() {
        return L1_website;
    }

    public void setL1_website(String l1_website) {
        L1_website = l1_website;
    }

    public String getL1_email_id2() {

        return L1_email_id2;
    }

    public void setL1_email_id2(String l1_email_id2) {
        L1_email_id2 = l1_email_id2;
    }

    public String getL1_email_id1() {

        return L1_email_id1;
    }

    public void setL1_email_id1(String l1_email_id1) {
        L1_email_id1 = l1_email_id1;
    }

    public String getL1_phone2() {

        return L1_phone2;
    }

    public void setL1_phone2(String l1_phone2) {
        L1_phone2 = l1_phone2;
    }

    public String getL1_phone1() {

        return L1_phone1;
    }

    public void setL1_phone1(String l1_phone1) {
        L1_phone1 = l1_phone1;
    }

    public String getL1_Address() {

        return L1_Address;
    }

    public void setL1_Address(String l1_Address) {
        L1_Address = l1_Address;
    }

    public String getSub_Heading() {

        return Sub_Heading;
    }

    public void setSub_Heading(String sub_Heading) {
        Sub_Heading = sub_Heading;
    }

    public String getHeading() {

        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getTitle() {

        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    // Empty constructor
    public Local_Data(){
         
    }
    
 // constructor
    public Local_Data(String username, String password, String imei_no, String project_id, String user_id, String client_id, String cur_date,
    		String cur_time,String dstr,String RR, String MRP,String claims,String claims_amoun, String complaints , String media_type,String media_url,String media_disc,String Target_Year,String Target_Month){
    	
    	 this.username = username;
         this.password = password;
         this.imei_no = imei_no;
         this.project_id = project_id;
         this.user_id = user_id;
         this.client_id = client_id;
         this.cur_date = cur_time;
         this.cur_time = cur_time;
         this.state_name = state_name;
         this.city_name = city_name;
         this.Dstr = dstr;
         this.claims = claims;
         this.claims_amount = claims_amount;
         this.complaints = complaints;
         this.media_type = media_type;
         this.media_url = media_url;
         this.media_disc = media_disc;
         this.Target_Year = Target_Year;
         this.Target_Month = Target_Month;
         this.Target_Text = Target_Text;
         this.Target_Achieved = Target_Achieved;
         this.Target_Quarter = Target_Quarter;
        
         this.travel_from = travel_from;
         this.travel_to =  travel_to;
         this.travel_date = travel_date;
         this.travel_mode = travel_mode;
         this.travel_cost = travel_cost;
         this.travel_text = travel_text;
         this.Retailer_id = Retailer_id;
        
         this.category_id = category_id;
         this.category_code =  category_code;
         this.category_desc =  category_desc;
         this.category_status = category_status;
         this.category_ids =  category_ids;
        
         this.product_code = product_code;
         this.product_desc = product_desc;
         this.product_status =  product_status;
         this.product_ids =  product_ids;
        
         this.variants_code = variants_code;
         this.variants_desc = variants_desc;
         this.variants_MRP =  variants_MRP;
         this.variants_retail_price = variants_retail_price;
         this.variants_product_unit = variants_product_unit;
         this.variants_status = variants_status;
         this.variants_ids =  variants_ids;
        
        
         this.stocks_product_name = stocks_product_name;
         this.stocks_product_quantity = stocks_product_quantity;
         this.stocks_product_text = stocks_product_text;
        
         this.PackSizes_variant_id =  PackSizes_variant_id;
         this.PackSizes_desc =  PackSizes_desc;
         this.PackSizes_product_unit = PackSizes_product_unit;
         this.PackSizes_product_pack_size = PackSizes_product_pack_size;
         this.PackSizes_status =  PackSizes_status;
         this.PackSizes_ids =  PackSizes_ids;
         this.RR = RR;
         this.MRP = MRP;
    }


    // getting media_id
    public String getmedia_id(){
        return this.media_id;
    }

    // setting media_id
    public void setmedia_id(String media_id){
        this.media_id = media_id;
    }

    // getting calender_delete_flag
    public String getcalender_delete_flag(){
        return this.calender_delete_flag;
    }

    // setting calender_delete_flag
    public void setcalender_delete_flag(String calender_delete_flag){
        this.calender_delete_flag = calender_delete_flag;
    }
    // getting misc_date
    public String getmisc_date(){
        return this.misc_date;
    }

    // setting misc_date
    public void setmisc_date(String misc_date){
        this.misc_date = misc_date;
    }

    // getting misc_cost
    public String getmisc_cost(){
        return this.misc_cost;
    }

    // setting misc_cost
    public void setmisc_cost(String misc_cost){
        this.misc_cost = misc_cost;
    }

    // getting misc_text
    public String getmisc_text(){
        return this.misc_text;
    }

    // setting misc_text
    public void setmisc_text(String misc_text){
        this.misc_text = misc_text;
    }

    // getting reason_type
    public String getreason_type(){
        return this.reason_type;
    }

    // setting reason_type
    public void setreason_type(String reason_type){
        this.reason_type = reason_type;
    }
    // getting survey_code
    public String getsurvey_code(){
        return this.survey_code;
    }

    // setting survey_code
    public void setsurvey_code(String survey_code){
        this.survey_code = survey_code;
    }

    // getting question_code
    public String getquestion_code(){
        return this.question_code;
    }

    // setting question_code
    public void setquestion_code(String question_code){
        this.question_code = question_code;
    }


    // getting active_from
    public String getactive_from(){
        return this.active_from;
    }

    // setting active_from
    public void setactive_from(String active_from){
        this.active_from = active_from;
    }

    // getting active_to
    public String getactive_to(){
        return this.active_to;
    }

    // setting active_to
    public void setactive_to(String active_to){
        this.active_to = active_to;
    }

    // getting question
    public String getquestion(){
        return this.question;
    }

    // setting question
    public void setquestion(String question){
        this.question = question;
    }

    // getting option_2
    public String getoption_2(){
        return this.option_2;
    }

    // setting option_2
    public void setoption_2(String option_2){
        this.option_2 = option_2;
    }

    // getting option_3
    public String getoption_3(){
        return this.option_3;
    }

    // setting option_3
    public void setoption_3(String option_3){
        this.option_3 = option_3;
    }


    // getting option_4
    public String getoption_4(){
        return this.option_4;
    }

    // setting option_4
    public void setoption_4(String option_4){
        this.option_4 = option_4;
    }

    // getting option_5
    public String getoption_5(){
        return this.option_5;
    }

    // setting option_5
    public void setoption_5(String option_5){
        this.option_5 = option_5;
    }

    // getting answer_date
    public String getanswer_date(){
        return this.answer_date;
    }

    // setting answer_date
    public void setanswer_date(String answer_date){
        this.answer_date = answer_date;
    }

    // getting customer_choice
    public String getcustomer_choice(){
        return this.customer_choice;
    }

    // setting customer_choice
    public void setcustomer_choice(String customer_choice){
        this.customer_choice = customer_choice;
    }


    // getting option_1
    public String getoption_1(){
        return this.option_1;
    }

    // setting option_1
    public void setoption_1(String option_1){
        this.option_1 = option_1;
    }


   // getting order DISTRIBUTER_ID
    public String getDISTRIBUTER_ID(){
        return this.DISTRIBUTER_ID;
    }
     
    // setting DISTRIBUTER_ID
    public void setDISTRIBUTER_ID(String DISTRIBUTER_ID){
        this.DISTRIBUTER_ID = DISTRIBUTER_ID;
    }


    // getting datetime1
    public String getdatetime1(){
        return this.datetime1;
    }

    // setting datetime1
    public void setdatetime1(String datetime1){
        this.datetime1 = datetime1;
    }

    // getting date1
    public String getdate1(){
        return this.date1;
    }

    // setting date1
    public void setdate1(String date1){
        this.date1 = date1;
    }

    // getting time1
    public String gettime1(){
        return this.time1;
    }

    // setting time1
    public void settime1(String time1){
        this.time1 = time1;
    }

    // getting order latitude
    public String getlatitude(){
        return this.latitude;
    }

    // setting latitude
    public void setlatitude(String latitude){
        this.latitude = latitude;
    }

    // getting order longitude
    public String getlongitude(){
        return this.longitude;
    }

    // setting longitude
    public void setlongitude(String longitude){
        this.longitude = longitude;
    }

    // getting order first_name
    public String getfirst_name(){
        return this.first_name;
    }

    // setting first_name
    public void setfirst_name(String first_name){
        this.first_name = first_name;
    }

    // getting order last_name
    public String getlast_name(){
        return this.last_name;
    }

    // setting last_name
    public void setlast_name(String last_name){
        this.last_name = last_name;
    }

    // getting order reporting_to
    public String getreporting_to(){
        return this.reporting_to;
    }

    // setting reporting_to
    public void setreporting_to(String reporting_to){
        this.reporting_to = reporting_to;
    }

    // getting order Ammount_overdue
    public String getAmmount_overdue(){
        return this.Ammount_overdue;
    }

    // setting Ammount_overdue
    public void setAmmount_overdue(String Ammount_overdue){
        this.Ammount_overdue = Ammount_overdue;
    }

    // getting order Address
    public String getAddress(){
        return this.Address;
    }

    // setting Address
    public void setAddress(String Address){
        this.Address = Address  ;
    }

    // getting order sync_time
    public String getsync_time(){
        return this.sync_time;
    }

    // setting sync_time
    public void setsync_time(String sync_time){
        this.sync_time = sync_time;
    }

    // getting order total_sync
    public String gettotal_sync(){
        return this.total_sync;
    }

    // setting total_sync
    public void settotal_sync(String total_sync){
        this.total_sync = total_sync;
    }

    // getting order Signature_image
    public String getSignature_image(){
        return this.Signature_image;
    }

    // setting Signature_image
    public void setSignature_image(String Signature_image){
        this.Signature_image = Signature_image;
    }


    // getting order calender_id
    public String getcalender_id(){
        return this.calender_id;
    }

    // setting calender_id
    public void setcalender_id(String calender_id){
        this.calender_id = calender_id;
    }


    // getting order calender_type
    public String getcalender_type(){
        return this.calender_type;
    }

    // setting calender_type
    public void setcalender_type(String calender_type){
        this.calender_type = calender_type;
    }


    // getting order from_date
    public String getfrom_date(){
        return this.from_date;
    }

    // setting from_date
    public void setfrom_date(String from_date){
        this.from_date = from_date;
    }


    // getting order to_date
    public String getto_date(){
        return this.to_date;
    }

    // setting to_date
    public void setto_date(String to_date){
        this.to_date = to_date;
    }


    // getting order calender_details
    public String getcalender_details(){
        return this.calender_details;
    }

    // setting calender_details
    public void setcalender_details(String calender_details){
        this.calender_details = calender_details;
    }

    // getting order latlon
    public String getlatlon(){
        return this.latlon;
    }

    // setting latlon
    public void setlatlon(String latlon){
        this.latlon = latlon;
    }






    // getting order user_email
    public String getuser_email(){
        return this.user_email;
    }

    // setting user_email
    public void setuser_email(String user_email){
        this.user_email = user_email;
    }
    
 // getting order PURPOSE_ADDRESS
    public String getPURPOSE_ADDRESS(){
        return this.PURPOSE_ADDRESS;
    }
     
    // setting PURPOSE_ADDRESS
    public void setPURPOSE_ADDRESS(String PURPOSE_ADDRESS){
        this.PURPOSE_ADDRESS = PURPOSE_ADDRESS;
    }  
    
  // getting order status
    public String getOrderAmount(){
        return this.ordamount;
    }
     
    // setting order status
    public void setOrderAmount(String ordamnt){
        this.ordamount = ordamnt;
    }  
    
 // getting order_number
    public String getorder_number(){
        return this.order_number;
    }
     
    // setting order_number
    public void setorder_number(String order_number){
        this.order_number = order_number;
    }  
 // getting order_id
    public String getOrderId(){
        return this.orderid;
    }
     
    // setting order_id
    public void setOrderId(String ordrid){
        this.orderid = ordrid;
    }
    
    
 // getting product_id
    public String getProductId(){
        return this.productid;
    }
     
    // setting product_id
    public void setProductId(String prdctid){
        this.productid = prdctid;
    }
    
    // getting qty
    public String getQty(){
        return this.qtyy;
    }
     
    // setting qty
    public void setQty(String qty){
        this.qtyy = qty;
    }  
    
    // getting price
    public String getPrice(){
        return this.price;
    }
     
    // setting price
    public void setPrice(String prce){
        this.price = prce;
    }  
    
 // getting amount
    public String getAmount(){
        return this.amount;
    }
     
    // setting amount
    public void setAmount(String amnt){
        this.amount = amnt;
    } 
  //getting customer_address
    public String get_custadr(){
        return this.cust_adr;
    }
     
    // setting customer_address
    public void set_custadr(String custadr ){
        this.cust_adr = custadr;
    }
    // getting id
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
    
    // getting rr
    public String getRR(){
        return this.RR;
    }
     
    // setting rr
    public void setRR(String rr){
        this.RR = rr;
    }
    
    // getting mrp
    public String getMRP(){
        return this.MRP;
    }
     
    // setting mrp
    public void setMRP(String mrp){
        this.MRP = mrp;
    }
    
    // getting distributor
    public String getDstr(){
        return this.Dstr;
    }
     
    // setting distributor 
    public void setDistr(String dstr){
        this.Dstr = dstr;
    }
    
 // getting username
    public String getUser(){
        return this.username;
    }
     
    // setting username
    public void setUser(String user){
        this.username = user;
    }
    
 // getting current date
    public String getC_Date(){
        return this.cur_date;
    }
     
    // setting current date
    public void setC_Date(String c_date){
        this.cur_date = c_date;
    }
    
 // getting current Description
    public String get_Description(){
        return this.description;
    }
     
    // setting current Description
    public void set_Description(String description ){
        this.description = description;
    }
    
 // getting current Description
    public String get_Claims(){
        return this.claims;
    }
     
    // setting current Description
    public void set_Claims(String claims ){
        this.claims = claims;
    }
    
    // getting current Description
    public String get_Claims_amount(){
        return this.claims_amount;
    }
     
    // setting current Description
    public void set_Claims_amount(String claims_amount ){
        this.claims_amount = claims_amount;
    }
    
  // getting current Description
    public String get_complaints(){
        return this.complaints;
    }
     
    // setting current Description
    public void set_complaints(String complaints ){
        this.complaints = complaints;
    }
    
    //getting current Media type
    public String get_mediaType(){
        return this.media_type;
    }
     
    // setting current Media type
    public void set_mediaType(String media_type ){
        this.media_type = media_type;
    }
    
  //getting current Media url
    public String get_mediaUrl(){
        return this.media_url;
    }
     
    // setting current Media url
    public void set_mediaUrl(String media_url ){
        this.media_url = media_url;
    }
    
  //getting current Media disc
    public String get_mediaDisc(){
        return this.media_disc;
    }
     
    // setting current Media url
    public void set_mediaDisc(String media_disc ){
        this.media_disc = media_disc;
    }
    
    //getting current Target Year
    public String get_Target_Year(){
        return this.Target_Year;
    }
     
    // setting current Target Year
    public void set_Target_Year(String Target_Year ){
        this.Target_Year = Target_Year;
    }
    
  //getting current Target Month
    public String get_Target_Month(){
        return this.Target_Month;
    }
     
    // setting current Target Month
    public void set_Target_Month(String Target_Month ){
        this.Target_Month = Target_Month;
    }
    
  //getting current Target Text
    public String get_Target_Text(){
        return this.Target_Text;
    }
     
    // setting current Target Text
    public void set_Target_Text(String Target_Text ){
        this.Target_Text = Target_Text;
    }
    
  //getting current Target Achieved
    public String get_Target_Achieved(){
        return this.Target_Achieved;
    }
     
    // setting current Target Achieved
    public void set_Target_Achieved(String Target_Achieved ){
        this.Target_Achieved = Target_Achieved;
    }
    
  //getting current Target Quarter
    public String get_Target_Quarter(){
        return this.Target_Quarter;
    }
     
    // setting current Target Quarter
    public void set_Target_Quarter(String Target_Quarter ){
        this.Target_Quarter = Target_Quarter;
    }
    
    //getting current travel from
    public String get_travel_from(){
        return this.travel_from;
    }
     
    // setting current travel from
    public void set_travel_from(String travel_from ){
        this.travel_from = travel_from;
    }
    
   //getting current travel to
    public String get_travel_to(){
        return this.travel_to;
    }
     
    // setting current travel to
    public void set_travel_to(String travel_to ){
        this.travel_to = travel_to;
    }
    
    //getting current travel date
    public String get_travel_date(){
        return this.travel_date;
    }
     
    // setting current travel date
    public void set_travel_date(String travel_date ){
        this.travel_date = travel_date;
    }
    
   //getting current travel mode
    public String get_travel_mode(){
        return this.travel_mode;
    }
     
    // setting current travel mode
    public void set_travel_mode(String travel_mode ){
        this.travel_mode = travel_mode;
    }
    
   //getting current travel cost
    public String get_travel_cost(){
        return this.travel_cost;
    }
     
    // setting current travel cost
    public void set_travel_cost(String travel_cost ){
        this.travel_cost = travel_cost;
    }
    
  //getting current Retailer id
    public String get_Retailer_id(){
        return this.Retailer_id;
    }
     
    // setting current Retailer id
    public void set_Retailer_id(String Retailer_id ){
        this.Retailer_id = Retailer_id;
    }
    
    //getting current travel text
    public String get_travel_text(){
        return this.travel_text;
    }
     
    // setting current travel text
    public void set_travel_text(String travel_text ){
        this.travel_text = travel_text;
    }
    
    
   //getting current category_id
    public String get_category_id(){
        return this.category_id;
    }
     
    // setting current category_id
    public void set_category_id(String category_id ){
        this.category_id = category_id;
    }
    
   //getting current category_code
    public String get_category_code(){
        return this.category_code;
    }
     
    // setting current category_code
    public void set_category_code(String category_code ){
        this.category_code = category_code;
    }
    
   //getting current category_desc
    public String get_category_desc(){
        return this.category_desc;
    }
     
    // setting current category_desc
    public void set_ccategory_desc(String category_desc ){
        this.category_desc = category_desc;
    }
    
   //getting current category_status
    public String get_category_status(){
        return this.category_desc;
    }
     
    // setting current category_status
    public void set_category_status(String category_status ){
        this.category_status = category_status;
    }
    
   //getting current category_ids
    public String get_category_ids(){
        return this.category_ids;
    }
     
    // setting current category_ids
    public void set_category_ids(String category_ids ){
        this.category_ids = category_ids;
    }
    
   //getting current product_code
    public String get_product_code(){
        return this.product_code;
    }
     
    // setting current product_code
    public void set_product_code(String product_desc ){
        this.product_code = product_code;
    }
    
    
    //getting current product_desc
    public String get_product_desc(){
        return this.product_desc;
    }
     
    // setting current product_desc
    public void set_product_desc(String product_desc ){
        this.product_desc = product_desc;
    }
    
    //getting current product_status
    public String get_product_status(){
        return this.product_status;
    }
     
    // setting current product_desc
    public void set_product_status(String product_status ){
        this.product_status = product_status;
    }
    
    
   //getting current product_status
    public String get_product_ids(){
        return this.product_ids;
    }
     
    // setting current product_desc
    public void set_product_ids(String product_ids ){
        this.product_ids = product_ids;
    }
    
   //getting current variants_code
    public String get_variants_code(){
        return this.variants_code;
    }
     
    // setting current variants_code
    public void set_variants_code(String variants_code ){
        this.variants_code = variants_code;
    }
    
    //getting current variants_desc
    public String get_variants_desc(){
        return this.variants_desc;
    }
     
    // setting current variants_desc
    public void set_variants_desc(String variants_desc ){
        this.variants_desc = variants_desc;
    }
    
    
   //getting current variants_MRP
    public String get_variants_MRP(){
        return this.variants_MRP;
    }
     
    // setting current variants_MRP
    public void set_variants_MRP(String variants_MRP ){
        this.variants_MRP = variants_MRP;
    }
    
    //getting current variants_retail_price
    public String get_variants_retail_price(){
        return this.variants_retail_price;
    }
     
    // setting current variants_retail_price
    public void set_variants_retail_price(String variants_retail_price ){
        this.variants_retail_price = variants_retail_price;
    }
    
    
   //getting current variants_product_unit
    public String get_variants_product_unit(){
        return this.variants_product_unit;
    }
     
    // setting current variants_product_unit
    public void set_variants_product_unit(String variants_product_unit ){
        this.variants_product_unit = variants_product_unit;
    }
    
    //getting current variants_status
    public String get_variants_status(){
        return this.variants_status;
    }
     
    // setting current variants_status
    public void set_variants_status(String variants_status ){
        this.variants_status = variants_status;
    }
    
    
   //getting current variants_ids
    public String get_variants_ids(){
        return this.variants_ids;
    }
     
    // setting current variants_ids
    public void set_variants_ids(String variants_ids ){
        this.variants_ids = variants_ids;
    }
    
    
   //getting current stocks_product_name
    public String get_stocks_product_name(){
        return this.stocks_product_name;
    }
     
    // setting current stocks_product_name
    public void set_stocks_product_name(String stocks_product_name ){
        this.stocks_product_name = stocks_product_name;
    }
    
    
   //getting current stocks_product_quantity
    public String get_stocks_product_quantity(){
        return this.stocks_product_quantity;
    }
     
    // setting current stocks_product_quantity
    public void set_stocks_product_quantity(String stocks_product_quantity ){
        this.stocks_product_quantity = stocks_product_quantity;
    }
    
    
   //getting current stocks_product_text
    public String get_stocks_product_text(){
        return this.stocks_product_text;
    }
     
    // setting current stocks_product_text
    public void set_stocks_product_text(String stocks_product_text ){
        this.stocks_product_text = stocks_product_text;
    }
    
   //getting current PackSizes_variant_id
    public String get_PackSizes_variant_id(){
        return this.PackSizes_variant_id;
    }
     
    // setting current PackSizes_variant_id
    public void set_PackSizes_variant_id(String PackSizes_variant_id ){
        this.PackSizes_variant_id = PackSizes_variant_id;
    }
    
   //getting current PackSizes_desc
    public String get_PackSizes_desc(){
        return this.PackSizes_desc;
    }
     
    // setting current PackSizes_desc
    public void set_PackSizes_desc(String PackSizes_desc ){
        this.PackSizes_desc = PackSizes_desc;
    }
    
    //getting current PackSizes_product_unit
    public String get_PackSizes_product_unit(){
        return this.PackSizes_product_unit;
    }
     
    // setting current PackSizes_product_unit
    public void set_PackSizes_product_unit(String PackSizes_product_unit ){
        this.PackSizes_product_unit = PackSizes_product_unit;
    }
    
   //getting current PackSizes_product_pack_size
    public String get_PackSizes_product_pack_size(){
        return this.PackSizes_product_pack_size;
    }
     
    // setting current PackSizes_product_pack_size
    public void set_PackSizes_product_pack_size(String PackSizes_product_pack_size ){
        this.PackSizes_product_pack_size = PackSizes_product_pack_size;
    }
    
   //getting current PackSizes_status
    public String get_PackSizes_status(){
        return this.PackSizes_status;
    }
     
    // setting current PackSizes_status
    public void set_PackSizes_status(String PackSizes_status ){
        this.PackSizes_status = PackSizes_status;
    }
    
    //getting current PackSizes_ids
    public String get_PackSizes_ids(){
        return this.PackSizes_ids;
    }
     
    // setting current PackSizes_ids
    public void set_PackSizes_ids(String PackSizes_ids ){
        this.PackSizes_ids = PackSizes_ids;
    }
    
   //getting current shedule_order_id
    public String get_shedule_order_id(){
        return this.shedule_order_id;
    }
     
    // setting current shedule_order_id
    public void set_shedule_order_id(String shedule_order_id ){
        this.shedule_order_id = shedule_order_id;
    }
    
   //getting current shedule_depatch_date
    public String get_shedule_depatch_date(){
        return this.shedule_depatch_date;
    }
     
    // setting current shedule_depatch_date
    public void set_shedule_depatch_date(String shedule_depatch_date ){
        this.shedule_depatch_date = shedule_depatch_date;
    }
    
    
   //getting current shedule_delivery_date
    public String get_shedule_delivery_date(){
        return this.shedule_delivery_date;
    }
     
    // setting current shedule_delivery_date
    public void set_shedule_delivery_date(String shedule_delivery_date ){
        this.shedule_delivery_date = shedule_delivery_date;
    }
    
    
   //getting current shedule_order_amount
    public String get_shedule_order_amount(){
        return this.shedule_order_amount;
    }
     
    // setting current shedule_order_amount
    public void set_shedule_order_amount(String shedule_order_amount ){
        this.shedule_order_amount = shedule_order_amount;
    }
    
    
   //getting current shedule_payment_mode
    public String get_shedule_payment_mode(){
        return this.shedule_payment_mode;
    }
     
    // setting current shedule_payment_mode
    public void set_shedule_payment_mode(String shedule_payment_mode ){
        this.shedule_payment_mode = shedule_payment_mode;
    }
    
    
    //getting current shedule_outstanding_amount
    public String get_shedule_outstanding_amount(){
        return this.shedule_outstanding_amount;
    }
     
    // setting current shedule_outstanding_amount
    public void set_shedule_outstanding_amount(String shedule_outstanding_amount ){
        this.shedule_outstanding_amount = shedule_outstanding_amount;
    }
    
    //getting current delivery_product_id
    public String get_delivery_product_id(){
        return this.delivery_product_id;
    }
     
    // setting current delivery_product_id
    public void set_delivery_product_id(String delivery_product_id ){
        this.delivery_product_id = delivery_product_id;
    }
    
    
   //getting current delivery_product_order_quantity
    public String get_delivery_product_order_quantity(){
        return this.delivery_product_order_quantity;
    }
     
    // setting current delivery_product_order_quantity
    public void set_delivery_product_order_quantity(String delivery_product_order_quantity ){
        this.delivery_product_order_quantity = delivery_product_order_quantity;
    }
    
    
   //getting current delivery_product_delivered_quality
    public String get_delivery_product_delivered_quality(){
        return this.delivery_product_delivered_quality;
    }
     
    // setting current delivery_product_delivered_quality
    public void set_delivery_product_delivered_quality(String delivery_product_delivered_quality ){
        this.delivery_product_delivered_quality = delivery_product_delivered_quality;
    }
    
    
    //getting current delivery_product_transporter_details
    public String get_delivery_product_transporter_details(){
        return this.delivery_product_transporter_details;
    }
     
    // setting current delivery_product_transporter_details
    public void set_delivery_product_transporter_details(String delivery_product_transporter_details ){
        this.delivery_product_transporter_details = delivery_product_transporter_details;
    }

  //getting current credit_limit
    public String get_credit_limit(){
        return this.credit_limit;
    }
     
    // setting current credit_limit
    public void set_credit_limit(String credit_limit ){
        this.credit_limit = credit_limit;
    }
    
    
    
    // getting password
    public String getPwd(){
        return this.password;
    }
    
    // setting password
    public void setPwd(String pwd){
        this.password = pwd;
    }
    
    // getting imei
    public String getImei(){
        return this.imei_no;
    }
     
    // setting imei
    public void setImei(String imei){
        this.imei_no = imei;
    }
    
    // getting product status
    public String getProductStatus(){
        return this.prod_stat;
    }
     
    // setting product status
    public void setProductStatus(String prod_stat){
        this.prod_stat = prod_stat;
    }
    
 // getting regid
    public String getRegid(){
        return this.regid;
    }
     
    // setting regid
    public void setRegid(String regid){
        this.regid = regid;
    }
    
 // getting regid
    public String getStateName(){
        return this.state_name;
    }
     
    // setting regid
    public void setStateName(String state_nm){
        this.state_name = state_nm;
    }
    
  // getting regid
    public String getCityName(){
        return this.city_name;
    }
     
    // setting city_id
    public void setcity_id(String city_id){
        this.city_id = city_id;
    }
    
 // getting city_id
    public String getcity_id(){
        return this.city_id;
    }
     
    // setting regid
    public void setCityName(String city_nm){
        this.city_name = city_nm;
    }
    
 // getting city_id
    public String getbeat_id(){
        return this.beat_id;
    }
     
    // setting regid
    public void setBeatName(String beat_nm){
        this.beat_name = beat_nm;
    }    
    
    // getting category
    public String getCategory(){
        return this.category;
    }
     
    // setting category
    public void setCategory(String categ){
        this.category = categ;
    }
    
 // getting category
    public String getProduct(){
        return this.product;
    }
     
    // setting category
    public void setProduct(String product){
        this.product = product;
    }
    
    // getting category
    public String getVariant(){
        return this.variant;
    }
     
    // setting category
    public void setVariant(String variant){
        this.variant = variant;
    }
    
    public String getOrderStatus(){
        return this.orderstatusid;
    }
     
    // setting order status
    public void setOrderStatus(String ordstat){
        this.orderstatusid = ordstat;
    }  
    
    public String getProduct_nm(){
        return this.product_name;
    }
     
    // setting order status
    public void setProduct_nm(String pname){
        this.product_name = pname;
    }  
    
    
    public String getRP(){
        return this.rp;
    }
     
    // setting order status
    public void setRP(String rr){
        this.rp = rr;
    }  
    
    public String getCust_Code(){
        return this.cust_code;
    }
     
    // setting order status
    public void setCust_Code(String code){
        this.cust_code = code;
    }  
    
    public String getItem_Code(){
        return this.item_code;
    }
     
    // setting order status
    public void setItem_Code(String icode){
        this.item_code = icode;
    } 
    
    public String getRRP(){
        return this.rrp;
    }
     
    // setting order status
    public void setRRP(String rp){
        this.rrp = rp;
    } 
    
    public String getProdname(){
        return this.prod_name;
    }
     
    // setting order status
    public void setProdname(String prdnm){
        this.prod_name = prdnm;
    }
    
    public String getc_name(){
        return this.c_name;
    }
     
    // setting order status
    public void setc_name(String c_name){
        this.c_name = c_name;
    }
    
    public String getreason_name(){
        return this.reason_name;
    }
     
    // setting reason_name
    public void setreason_name(String reason_name){
        this.reason_name = reason_name;
    }
    
    public String getreason_code(){
        return this.reason_code;
    }
     
    // setting reason_code
    public void setreason_code(String reason_code){
        this.reason_code = reason_code;
    }
    
    //LEGACY_CUSTOMER_CODE GET
    public String getLEGACY_CUSTOMER_CODE(){
        return this.LEGACY_CUSTOMER_CODE;
    }
     
    // setting LEGACY_CUSTOMER_CODE SET
    public void setLEGACY_CUSTOMER_CODE(String LEGACY_CUSTOMER_CODE){
        this.LEGACY_CUSTOMER_CODE = LEGACY_CUSTOMER_CODE;
    }
    
  //CUSTOMER_NAME GET
    public String getCUSTOMER_NAME(){
        return this.CUSTOMER_NAME;
    }
     
    // setting CUSTOMER_NAME SET
    public void setCUSTOMER_NAME(String CUSTOMER_NAME){
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }
    
  //CUSTOMER_SHOPNAME GET
    public String getCUSTOMER_SHOPNAME(){
        return this.CUSTOMER_SHOPNAME;
    }
     
    // setting CUSTOMER_SHOPNAME SET
    public void setCUSTOMER_SHOPNAME(String CUSTOMER_SHOPNAME){
        this.CUSTOMER_SHOPNAME = CUSTOMER_SHOPNAME;
    }
    
    
  //ADDRESS GET
    public String getADDRESS(){
        return this.ADDRESS;
    }
     
    // setting ADDRESS SET
    public void setADDRESS(String ADDRESS){
        this.ADDRESS = ADDRESS;
    }
    
    //STREET GET
    public String getSTREET(){
        return this.STREET;
    }
     
    // setting STREET SET
    public void setSTREET(String STREET){
        this.STREET = STREET;
    }
    
    //LANDMARK GET
    public String getLANDMARK(){
        return this.LANDMARK;
    }
     
    // setting LANDMARK SET
    public void setLANDMARK(String LANDMARK){
        this.LANDMARK = LANDMARK;
    }
    
   //PIN_CODE GET
    public String getPIN_CODE(){
        return this.PIN_CODE;
    }
     
    // setting PIN_CODE SET
    public void setPIN_CODE(String PIN_CODE){
        this.PIN_CODE = PIN_CODE;
    }
    
    //MOBILE_NO GET
    public String getMOBILE_NO(){
        return this.MOBILE_NO;
    }
     
    // setting MOBILE_NO SET
    public void setMOBILE_NO(String MOBILE_NO){
        this.MOBILE_NO   = MOBILE_NO;
    }
    
  //EMAIL_ADDRESS GET
    public String getEMAIL_ADDRESS(){
        return this.EMAIL_ADDRESS;
    }
     
    // setting EMAIL_ADDRESS SET
    public void setEMAIL_ADDRESS(String EMAIL_ADDRESS){
        this.EMAIL_ADDRESS   = EMAIL_ADDRESS;
    }
    
    
   //STATUS GET
    public String getSTATUS(){
        return this.STATUS;
    }
     
    // setting STATUS SET
    public void setSTATUS(String STATUS){
        this.STATUS   = STATUS;
    }
    
    //STATE_ID GET
    public String getSTATE_ID(){
        return this.STATE_ID;
    }
     
    // setting STATE_ID SET
    public void setSTATE_ID(String STATE_ID){
        this.STATE_ID   = STATE_ID;
    }
    
  //CITY_ID GET
    public String getCITY_ID(){
        return this.CITY_ID;
    }
     
    // setting CITY_ID SET
    public void setCITY_ID(String CITY_ID){
        this.CITY_ID   = CITY_ID;
    }
    
   //BEAT_ID GET
    public String getBEAT_ID(){
        return this.BEAT_ID;
    }
     
    // setting BEAT_ID SET
    public void setBEAT_ID(String BEAT_ID){
        this.BEAT_ID   = BEAT_ID;
    }
    
  //vatin GET
    public String getvatin(){
        return this.vatin;
    }
     
    // setting vatin SET
    public void setvatin(String vatin){
        this.vatin   = vatin;
    }
    
  //email GET
    public String getemail(){
        return this.email;
    }
     
    // setting email SET
    public void setemail(String email){
        this.email   = email;
    }

    // getting new label
    public String getNewLabel_account() {
        return this.new_label;
    }

    // setting new label
    public void setNewLabel_account(String newlab_acc) {
        this.new_label = newlab_acc;
    }

    // getting variable label
    public String getVarLabel_account() {
        return this.var_label;
    }

    // setting variable label
    public void setVarLabel_account(String varlab_acc) {
        this.var_label = varlab_acc;
    }


}
