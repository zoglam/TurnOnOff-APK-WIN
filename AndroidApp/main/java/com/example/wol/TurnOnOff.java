package com.example.wol;

import android.annotation.SuppressLint;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class TurnOnOff implements Runnable {

    private int PORT;
    private String ipAddress;
    private String macAddress;
    public boolean flag;

    public TurnOnOff(int PORT, String ipAddress, String macAddress) {
        this.PORT = PORT;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
    }

    public TurnOnOff() {
        this(0, "", "");
    }

    @Override
    public void run() {
        String ipStr = ipAddress;
        String macStr = "4C:72:B9:85:11:75";
        try {
            DatagramSocket socket;
            byte[] macBytes = getMacBytes(macStr);
            byte[] bytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }
            InetAddress address = InetAddress.getByName(ipStr);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
            socket = new DatagramSocket(null);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(PORT));
            socket.send(packet);
            socket.close();
            Log.d("myLogs", "Good");
        } catch (Exception e) {
            Log.d("myLogs", e.toString());
        }
    }

    private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");

        }
        return bytes;
    }

}
