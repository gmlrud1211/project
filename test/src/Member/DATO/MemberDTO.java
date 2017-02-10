package Member.DATO;

public class MemberDTO {
	String name; 	// 이름
	String usr_id; 		// 아이디
	String usr_pwd; 	// 비밀번호

	public MemberDTO(String name, String usr_id, String usr_pwd) {
		this.name = name;
		this.usr_id = usr_id;
		this.usr_pwd = usr_pwd;
	}

	public MemberDTO(String usr_id, String usr_pwd) {
		this.usr_id 	= 	usr_id;
		this.usr_pwd 	=	usr_pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsr_id() {
		return usr_id;
	}

	public void setUsr_id(String usr_id) {
		this.usr_id = usr_id;
	}
	
	public String getUsr_pwd() {
		return usr_pwd;
	}

	public void setUsr_pwd(String usr_pwd) {
		this.usr_pwd = usr_pwd;
	}


	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("이름 : "+name);
		sb.append(" 아이디 : "+usr_id);
		sb.append(" 비밀번호 : "+usr_pwd);
		return sb.toString();
	}

}
