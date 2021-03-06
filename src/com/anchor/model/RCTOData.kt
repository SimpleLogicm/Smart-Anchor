package com.anchor.model

data class RCTOData(var id: String,var code: String,val user_name:String, var shop_name: String, var address: String,
                    val state_code:String,val city_code:String,val pincode :String,val landline_no:String,var mobile: String,var email: String,val status:String,
                    val proprietor_name:String,val gst_no:String,val aadhar_no:String,val pan_no:String,val latitude:String,val longitude:String,val power_dealer:String,val lighting_dealer:String,val iaq_dealer:String,val source_of_data:String,
                    val comments:String,val is_duplicate:String,val tsi_code:String, var card_color_code: String,
                    var distance: String,var address_line2: String,var landmark: String,var full_address : String,var dist_code:String,var dist_name:String,var is_approved:String,var bucket_name:String,var mobile_verified:String)