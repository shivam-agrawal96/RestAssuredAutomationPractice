package pojopractice;

import java.util.List;

public class Courses {
	
	private List<WebAutomation> webAutomation;
	private List<ApiPojo> api;
	private List<MobileAutomation> mobile;
	
	public List<WebAutomation> getWebAutomation() {
		return webAutomation;
	}
	public void setWebautomation(List<WebAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<ApiPojo> getApi() {
		return api;
	}
	public void setApi(List<ApiPojo> api) {
		this.api = api;
	}
	public List<MobileAutomation> getMobile() {
		return mobile;
	}
	public void setMobile(List<MobileAutomation> mobile) {
		this.mobile = mobile;
	}

}
