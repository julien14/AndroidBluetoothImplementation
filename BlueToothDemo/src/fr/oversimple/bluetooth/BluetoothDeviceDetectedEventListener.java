package fr.oversimple.bluetooth;

import android.bluetooth.BluetoothDevice;

public interface BluetoothDeviceDetectedEventListener {
	
	public void deviceDetected(BluetoothDevice device);
	
}
