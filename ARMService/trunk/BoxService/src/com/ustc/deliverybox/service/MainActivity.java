package com.ustc.deliverybox.service;



import com.ustc.deliverybox.service.R;
import com.ustc.deliverybox.gpio.DeliveryBoxGPIO;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	public EditText myTextField;
	public Button myButton;
	final String TAG = "DeliveryBoxService";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myTextField = (EditText) findViewById(R.id.myTextField);
		myButton = (Button) findViewById(R.id.myButton);
		myButton.setOnClickListener(myHandler);
		
	}

	View.OnClickListener myHandler = new View.OnClickListener() {
		public void onClick(View v) {
			
			String boxid = myTextField.getText().toString();
			
			int box;
			
 			try {
 				
 				box = Integer.parseInt(boxid);
 			} catch (NumberFormatException e) {
 				Toast.makeText(getApplicationContext(), R.string.open_box_gpio_fail, 2000).show();
 				return;
 			}
			
			int ret = DeliveryBoxGPIO.openBox(box);
			
			if (ret == -1)
				Toast.makeText(getApplicationContext(), R.string.open_box_gpio_fail, 2000).show();
			else 
				updateBoxId(box);
		}
	};
	
	private void updateBoxId(int boxId)
	{
		myTextField.setText(/*(boxId+1)+*/"");
	}
}
