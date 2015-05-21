package com.ustc.deliverybox.register;

import com.ustc.deliverybox.command.RegisterCommand;
import com.ustc.deliverybox.command.RegisterCommand.RegisterCommandSink;
import com.ustc.deliverybox.util.Logger;

public class RegisterManager implements IRegisterManager, RegisterCommandSink{
	
	private static final String TAG = RegisterCommandSink.class.getSimpleName();
	
	private Listener mListener = null;
	
	private RegisterCommand registerAPICommand = null;
	
	public RegisterManager()
	{
		registerAPICommand = new RegisterCommand();
		registerAPICommand.setListener(this);
	}

	//IRegisterManager
	@Override
	public void setListener(Listener listener) {
		mListener = listener;
	}

	@Override
	public void register(RegisterParams param) {
		
		registerAPICommand.doCommand(param);
		
	}

	
	//RegisterCommandSink
	@Override
	public void onRegisterSuccess() {
		
		if (mListener == null){
			Logger.error(TAG, "onRegisterSuccess error, mListener is null");
			return;
		}
		
		mListener.onRegisterSuccess();
	}

	@Override
	public void onRegisterFailure(String errorMessage) {
		
		if (mListener == null){
			Logger.error(TAG, "onRegisterFailure error, mListener is null");
			return;
		}
		
		mListener.onRegisterFailure(errorMessage);
	}

}
