package fr.oversimple.bluetooth;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 
 * This class has been made to handle efficiently the Bluetooth manager
 * 
 * Add the following permissions to the AndroidManifest.xml file :
 * <uses-permission android:name="android.permission.BLUETOOTH"/>
 * <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
 * 
 * @author Julien Hatin
 * @version 1.0
 * 
 */
public class BluetoothHandler extends BroadcastReceiver {

	/* private variable */
	private Context context;
	/* The list of device discivered by this BluetoothHandler */
	private List<BluetoothDevice> devices;

	private BluetoothAdapter bluetoothAdapter;
	private BluetoothDeviceDetectedEventListener bluetoothDeviceDetectedEventListener;

	public BluetoothHandler(Context context) {
		this.context = context;
		devices = new ArrayList<BluetoothDevice>();
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	/**
	 * Start the Bluetooth scan add a
	 * {@link BluetoothDeviceDetectedEventListener} with the
	 * {@link setBluetoothDeviceDetectedEventListener} to be informed of device
	 * detection
	 */
	public void startScan() {
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		context.registerReceiver(this, filter);
		bluetoothAdapter.startDiscovery();
	}

	/**
	 * Stop the Bluetooth scan. Don't forget to call in the onDestroy method of
	 * your activity for example
	 */
	public void stopScan() {
		bluetoothAdapter.cancelDiscovery();
		context.unregisterReceiver(this);
	}

	/**
	 * 
	 * Make your device visible to other devices for the specified amount of
	 * time
	 * 
	 * @param time
	 *            the time your device will be visible
	 */
	public void beDiscoverable(int time) {
		Intent discoverableIntent = new Intent(
				BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(
				BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, time);
		context.startActivity(discoverableIntent);
	}

	/**
	 * 
	 * @return true if the Bluetooth is present on the device
	 */
	public boolean isBluetoothPresentOnDevice() {
		if (null == bluetoothAdapter) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @return true is the Bluetooth is activatd on the device
	 */
	public boolean isBluetoothEnabled() {
		if (isBluetoothPresentOnDevice()) {
			return bluetoothAdapter.isEnabled();
		} else {
			return false;
		}
	}

	/**
	 * Return the list of the devices already paired your device
	 * 
	 * @return the list of the devices already paired your device
	 */
	public Set<BluetoothDevice> getAllreadyBoundedDevices() {
		return bluetoothAdapter.getBondedDevices();
	}

	/**
	 * Return the devices discovered since the {@link startScan()} method have
	 * been called
	 * 
	 * @return all the device discovered
	 */
	public List<BluetoothDevice> getDiscoveredDevices() {
		return devices;
	}

	/**
	 * Set a {@link BluetoothDeviceDetectedEventListener}. This listener is
	 * unique and will be called every time a new device is discovered;
	 * 
	 * @param eventListener
	 */
	public void setBluetoothDeviceDetectedEventListener(
			BluetoothDeviceDetectedEventListener eventListener) {
		bluetoothDeviceDetectedEventListener = eventListener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (BluetoothDevice.ACTION_FOUND.equals(action)) {
			BluetoothDevice device = (BluetoothDevice) intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			
			if(!devices.contains(device)) {
				devices.add(device);
			}

			if (null != bluetoothDeviceDetectedEventListener) {
				bluetoothDeviceDetectedEventListener.deviceDetected(device);
			}

		}
	}

}
