package com.example.wol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    Button button_on;
    Button button_off;
    Button button_ip;
    Button button_mac;
    EditText input;

    public String macAddress = "";
    public String ipAddress = "";

    private static final int PORT_WOL = 9998;
    private static final int PORT_OFF = 9999;
    private final String FILE_IP = "ip.txt";
    private final String FILE_MAC = "mac.txt";
    private AlertDialog inputIP;
    private AlertDialog inputMAC;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_on = (Button) findViewById(R.id.button1);
        button_off = (Button) findViewById(R.id.button2);
        button_ip = (Button) findViewById(R.id.button3);
        button_mac = (Button) findViewById(R.id.button4);

        IsFileEmpty(FILE_IP);
        IsFileEmpty(FILE_MAC);

        readFile(FILE_IP, "IP");
        readFile(FILE_MAC, "MAC");

        button_ip.setText("IP - " + ipAddress);
        button_mac.setText("MAC - " + macAddress);

        button_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFile(FILE_IP, "IP");
                readFile(FILE_MAC, "MAC");
                new Thread(new TurnOnOff(PORT_WOL, ipAddress, macAddress)).start();
                Toast.makeText(getApplicationContext(), "Действие выполняется...", Toast.LENGTH_LONG).show();
            }
        });
        button_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFile(FILE_IP, "IP");
                readFile(FILE_MAC, "MAC");
                Thread thread = new Thread() {
                    public void run() {
                        try {
                            Log.d("myLogs", "Good1");
                            Socket request = new Socket(ipAddress, PORT_OFF);
                            request.close();
                            Log.d("myLogs", "Good");
                        } catch (IOException e) {
                            Log.d("myLogs", "BAD - " + e.toString());
                        }
                    }
                };
                thread.start();
            }
        });
        button_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputIP = createAlertForm("IP");
                inputIP.show();
            }
        });
        button_mac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputMAC = createAlertForm("MAC");
                inputMAC.show();
            }
        });
    }

    private AlertDialog createAlertForm(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        input = new EditText(this);
        if (title.equals("IP")) {
            input.getText().insert(input.getSelectionStart(), ipAddress);
        } else if (title.equals("MAC")) {
            input.getText().insert(input.getSelectionStart(), macAddress);
        }
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt = input.getText().toString();
                Toast.makeText(getApplicationContext(), "New value - " + txt, Toast.LENGTH_LONG).show();
                if (txt.contains(".")) {
                    ipAddress = txt;
                } else if (txt.contains(":")) {
                    macAddress = txt;
                }
                writeFile(ipAddress, "ip.txt");
                writeFile(macAddress, "mac.txt");
                button_ip.setText("IP - " + ipAddress);
                button_mac.setText("MAC - " + macAddress);
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    public void writeFile(String str, String FILENAME) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)));
            bw.write(str); // пишем данные
            bw.close(); // закрываем поток
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(String FILENAME, String type) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            while ((str = br.readLine()) != null) {
                if (type.equals("IP")) {
                    ipAddress = str;
                } else if (type.equals("MAC")) {
                    macAddress = str;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void IsFileEmpty(String FILENAME) {
        File f = new File(FILENAME);
        if (f.exists()) {
            if (FILENAME.equals("ip.txt")) {
                writeFile("0.0.0.0", FILENAME);
            } else if (FILENAME.equals("mac.txt")) {
                writeFile("FF:FF:FF:FF:FF:FF", FILENAME);
            }
        }
    }


}
