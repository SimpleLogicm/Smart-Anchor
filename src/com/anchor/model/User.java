package com.anchor.model;


public class User {

	int userID, rolID, stateID, cityID;
	String username, password, status, firstName, lastName, simID, beatID;
	float target, current_target;

	String Email,DateOfJoin,DeviceId,ImeiNo,MobileNo;
	
	public User(String beatId,int cityId,String dateOfJoin,String deviceId,String email,
			String firstname,String imeiNo,String LastName,String mobileNo,int RoleId,int SateID,
			String Satus,int UserId,String UserName,String UserPassword){
		
		 beatID = beatId;
		 cityID =cityId ;
		 DateOfJoin= dateOfJoin ;
		 DeviceId = deviceId;
		 Email = email;
		 firstName = firstname;
		 ImeiNo =imeiNo;
		 lastName =LastName;
		 MobileNo = mobileNo;
		 rolID =RoleId;
		 stateID = SateID;
		 status = Satus;
		 userID = UserId;
		 username = UserName;
		 password = UserPassword;
	}
	
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int userID, int rolID, int stateID, int cityID, String beatID,
			String username, String password, String status, String firstName,
			String lastName, String simID, float target, float current_target) {
		super();
		this.userID = userID;
		this.rolID = rolID;
		this.stateID = stateID;
		this.cityID = cityID;
		this.beatID = beatID;
		this.username = username;
		this.password = password;
		this.status = status;
		this.firstName = firstName;
		this.lastName = lastName;
		this.simID = simID;
		this.target = target;
		this.current_target = current_target;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", rolID=" + rolID + ", stateID="
				+ stateID + ", cityID=" + cityID + ", beatID=" + beatID
				+ ", username=" + username + ", password=" + password
				+ ", status=" + status + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", simID=" + simID + ", target="
				+ target + ", current_target=" + current_target + "]";
	}

	public String getEmail() {
		return Email;
	}



	public void setEmail(String email) {
		Email = email;
	}



	public String getDateOfJoin() {
		return DateOfJoin;
	}



	public void setDateOfJoin(String dateOfJoin) {
		DateOfJoin = dateOfJoin;
	}



	public String getDeviceId() {
		return DeviceId;
	}



	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}



	public String getImeiNo() {
		return ImeiNo;
	}



	public void setImeiNo(String imeiNo) {
		ImeiNo = imeiNo;
	}



	public String getMobileNo() {
		return MobileNo;
	}



	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}



	public String getSimID() {
		return simID;
	}

	public void setSimID(String simID) {
		this.simID = simID;
	}

	public float getTarget() {
		return target;
	}

	public void setTarget(float target) {
		this.target = target;
	}

	public float getCurrent_target() {
		return current_target;
	}

	public void setCurrent_target(float current_target) {
		this.current_target = current_target;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getRolID() {
		return rolID;
	}

	public void setRolID(int rolID) {
		this.rolID = rolID;
	}

	public int getStateID() {
		return stateID;
	}

	public void setStateID(int stateID) {
		this.stateID = stateID;
	}

	public int getCityID() {
		return cityID;
	}

	public void setCityID(int cityID) {
		this.cityID = cityID;
	}

	public String getBeatID() {
		return beatID;
	}

	public void setBeatID(String beatID) {
		this.beatID = beatID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
