package fr.oversimple.bluetoothdemo;

import java.util.Iterator;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bluetoothdemo.R;

import fr.oversimple.bluetooth.BluetoothDeviceDetectedEventListener;
import fr.oversimple.bluetooth.BluetoothHandler;

public class MainActivity extends Activity implements BluetoothDeviceDetectedEventListener{

	private BluetoothHandler bluetoothHandler;
	private TextView deviceListTextview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bluetoothHandler = new BluetoothHandler(this);
		bluetoothHandler.startScan();
		deviceListTextview = (TextView) findViewById(R.id.devicesTextView);
		bluetoothHandler.setBluetoothDeviceDetectedEventListener(this);
	}
	
	@Override
	protected void onDestroy() {
		bluetoothHandler.stopScan();
		super.onDestroy();
	}

	@Override
	public void deviceDetected() {
		Iterator<BluetoothDevice> it = bluetoothHandler.getDiscoveredDevices().iterator();
		
		while(it.hasNext()) {
			BluetoothDevice c_device = it.next();
			deviceListTextview.append(c_device.getName() + " " + c_device.getAddress());
		}
	}
	
}
