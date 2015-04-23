package datebase;

public class InfoDB {
    private String USER;
	private	String PASSWORD;///senha pessoal
	private String	URL ;
	private String	DRIVER ;
	public String 	NAME_DATABASE ;
	public InfoDB(String database){
		if(database.equals("MySQL")){
			setUSER("root");
			setPASSWORD("root");
			setURL("jdbc:mysql://localhost/");
			setDRIVER("com.mysql.jdbc.Driver");
			setNAME_DATABASE("bd2");
		}
		else{
			setUSER("postgres");
			setPASSWORD("root");
			setURL("jdbc:postgresql://localhost:5432/");
			setDRIVER("org.postgresql.Driver");
			setNAME_DATABASE("bd2");
		}
	}
	public String getNAME_DATABASE() {
		return NAME_DATABASE;
	}
	public String getDRIVER() {
		return DRIVER;
	}
	public String setNAME_DATABASE(String database){
		return NAME_DATABASE= "bd2";
	}
	public void setDRIVER(String dRIVER) {
		DRIVER = dRIVER;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getUSER() {
		return USER;
	}
	public void setUSER(String uSER) {
		USER = uSER;
	}
    
}
