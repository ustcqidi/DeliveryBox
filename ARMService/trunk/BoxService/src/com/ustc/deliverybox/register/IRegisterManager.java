package com.ustc.deliverybox.register;

public interface IRegisterManager {
	
	public interface Listener {
		
		void onRegisterSuccess();
		
		void onRegisterFailure(String errorMessage);
	}
	
	
	void setListener(Listener listener);
	
	void register(RegisterParams param);

}
