package fr.oversimple.bluetoothdemo;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
	public void deviceDetected(BluetoothDevice device) {
		deviceListTextview.setText(bluetoothHandler.getDiscoveredDevices().toString());
	}
	
}
