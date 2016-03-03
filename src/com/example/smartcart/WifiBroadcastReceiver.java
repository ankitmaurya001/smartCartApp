package com.example.smartcart;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartcart.fragments.NotificationFragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/*
 * Receives Wifi scan result whenever WifiManager has them,
 * updates `wifiListString` and `lastWifiScanTime`,
 * logs location (and accuracy) and Wifis (SSID, BSSID, strength) to disk
 */
public class WifiBroadcastReceiver extends BroadcastReceiver {

	/*
	private String wifi1 = "Thinqubator";
    private String wifi1Mac1 = "24:b6:57:5a:ce:00";
    private String wifi1Mac2 = "24:b6:57:5a:ce:0f";
    private int wifi1Strength = -35;
    private String message1title = "You have an offer from Levi's";
    private String message1body = "30% off on Tshirts";
*/
	//for video shoot 21st Jan 2016
	private String wifi1 = "Thinqubator";
    private String wifi1Mac1 = "24:b6:57:5a:ce:00";
    private String wifi1Mac2 = "24:b6:57:5a:ce:0f";
    private int wifi1Strength = -34;
    private String message1title = "You have an offer from Puma";
    private String message1body = "10% off on Caps";
    
   /* 
	//for jaipur demo
	private String wifi1 = "ankitP3";
    private String wifi1Mac1 = "90:68:c3:47:65:d5";
    private String wifi1Mac2 = "90:68:c3:47:65:d5";
    private int wifi1Strength = -50;
    private String message1title = "You have an offer from Puma";
    private String message1body = "10% off on Caps";

	*/
/*	
    private String wifi2 = "Thinqubator";
    private String wifi2Mac1 = "24:b6:57:42:d3:c0";
    private String wifi2Mac2 = "24:b6:57:42:d3:cf";
    private int wifi2Strength = -41;
    private String message2title = "You have an offer from Nike";
    private String message2body = "Get 30% off on Tshirts";
*/
    
  //for jaipur demo
    /*
    private String wifi2 = "ankitpt2";
    private String wifi2Mac1 = "5c:2e:59:33:b8:ff";
    private String wifi2Mac2 = "5c:2e:59:33:b8:ff";
    private int wifi2Strength = -45;
    private String message2title = "You have an offer from Nike";
    private String message2body = "Get 30% off on Tshirts";
  	*/
    
    //for video shoot on 21st jan , 2016
    private String wifi2 = "Thinqubator";
    private String wifi2Mac1 = "24:b6:57:42:d3:c0";
    private String wifi2Mac2 = "24:b6:57:42:d3:cf";
    private int wifi2Strength = -41;
    private String message2title = "You have an offer from Nike";
    private String message2body = "Get 30% off on Tshirts";
    

    public static boolean SNotificationSentPuma = false;

    public static boolean SNotificationSentNike = false;


    private final MainActivity m;

    private final Comparator<ScanResult> RSSI_ORDER =
            new Comparator<ScanResult>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                public int compare(ScanResult e1, ScanResult e2) {
                    return Integer.compare(e2.level, e1.level);
                }
            };

    private static final String WIFI_SCAN_TIMER = "wifi-scan-timer";
    private static Timer wifiScanTimer;

    private static final int NOT_SPECIAL = 0;
    private static final int SPECIAL_NO_VISIBLE_WIFI = 1;
    public static ScanResult mMinWifi = null;
    NotificationManager manager;
    Notification myNotication;

    public WifiBroadcastReceiver(MainActivity m) {
        this.m = m;
        wifiScanTimer = new Timer(WIFI_SCAN_TIMER);
        manager = (NotificationManager) m.getSystemService(m.NOTIFICATION_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        List<ScanResult> scanResultList = MainActivity.wifiManager.getScanResults();
        m.lastWifiScanTime = new Date();

        Collections.sort(scanResultList, RSSI_ORDER);

        String combined = "";
        Pattern filter = makeFilter();

        Boolean atLeastOneLogged = false;
        double mini = 200000;
        if(scanResultList.size() > 0)
        {
            //m.mTextView.setText("You are near to wifi : " + scanResultList.get(0).SSID);

            Intent resultIntent = new Intent(m, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(m, 1, resultIntent, 0);

            Notification.Builder builder = new Notification.Builder(m);


//            SNotificationSentLevis = false;
//            SNotificationSentArrow = false;

            if(NotificationFragment.mAdapter != null && NotificationFragment.mKeys != null)
            {
                if(!NotificationFragment.mKeys.containsKey(scanResultList.get(0).BSSID)) {

                    String wifiName = scanResultList.get(0).SSID;
                    String wifiMac = scanResultList.get(0).BSSID;
                    int wifiLevel = scanResultList.get(0).level;
                    if(wifiName.contains(wifi1) &&
                            (wifiMac.contains(wifi1Mac1) || wifiMac.contains(wifi1Mac2)) &&
                         wifiLevel >= wifi1Strength   )
                    {
                        builder.setAutoCancel(false);
                        builder.setTicker("this is ticker text");
                        builder.setContentTitle("Smart Cart");
                        builder.setContentText(message1title);
                        builder.setSmallIcon(R.drawable.ic_launcher);
                        builder.setContentIntent(pendingIntent);
                        builder.setOngoing(true);
                        builder.setSubText(message1body);   //API level 16
                        builder.setNumber(100);
                        builder.build();
                        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        builder.setSound(uri);

                        myNotication = builder.getNotification();
                        myNotication.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
                        manager.notify(11, myNotication);


                        NotificationFragment.mValues.add(message1body + "\n" + message1title);
                        NotificationFragment.mAdapter.notifyDataSetChanged();
                        NotificationFragment.mKeys.put(wifiMac, true);
                        SNotificationSentPuma = true;
                        Toast.makeText(m, "You have an offer from Puma", Toast.LENGTH_SHORT)
                        .show();
                        
                    }
                    if(wifiName.contains(wifi2) &&
                            (wifiMac.contains(wifi2Mac1) || wifiMac.contains(wifi2Mac2)) &&
                            wifiLevel >= wifi2Strength   )
                    {
                        builder.setAutoCancel(false);
                        builder.setTicker("this is ticker text");
                        builder.setContentTitle("Smart Cart");
                        builder.setContentText(message2title);
                        builder.setSmallIcon(R.drawable.ic_launcher);
                        builder.setContentIntent(pendingIntent);
                        builder.setOngoing(true);
                        builder.setSubText(message2body);   //API level 16
                        builder.setNumber(100);
                        builder.build();
                        Uri uri1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        builder.setSound(uri1);

                        myNotication = builder.getNotification();
                        myNotication.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
                        manager.notify(11, myNotication);


                        NotificationFragment.mValues.add(message2body + "\n" + message2title);
                        NotificationFragment.mAdapter.notifyDataSetChanged();
                        NotificationFragment.mKeys.put(wifiMac, true);
                        SNotificationSentNike = true;
                        Toast.makeText(m, "You have an offer from Nike", Toast.LENGTH_SHORT)
                        .show();
                    }

                }
                else
                {

                }
            }
        }
        for (ScanResult wifi : scanResultList) {
//            if (!filter.matcher(wifi.SSID).matches()) {
//                //continue;
//            }
            atLeastOneLogged = true;

            combined += convertFrequencyToChannel(wifi.frequency) + " " + wifi.SSID + " [" + wifi.BSSID + "]" + ": " + wifi.level + "\n";
            double x = calculateDistance(wifi.level, wifi.frequency);
            double y = x;
            double z = y;
            //log(wifi, NOT_SPECIAL);
        }
        // if no log entry was triggered (i.e., no wifis that matched the filter),
        // write special entry signifying that no wifi was in range
        if (!atLeastOneLogged) {
           log(null, SPECIAL_NO_VISIBLE_WIFI);
        }

//        m.wifiListString = combined;
//        m.updateUI();

        // schedule next scan after short delay
        wifiScanTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    MainActivity.wifiManager.startScan();
                }
            }, loginPage.WIFI_SCAN_DELAY_MILLIS);
    }

    public double calculateDistance(double signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }

    private void log(ScanResult wifi, int specialCode) {
//        if (m.loggingEnabled) {
//            String csvLine =     MainActivity.LOG_FORMAT_VERSION
//                         + "," + Build.MODEL
//                         + "," + MainActivity.sessionId
//                         + "," + m.currentLocation.getLatitude()
//                         + "," + m.currentLocation.getLongitude()
//                         + "," + m.currentLocation.getAltitude()
//                         + "," + m.currentLocation.getAccuracy()
//                         + "," + m.currentLocation.getSpeed()
//                         + "," + specialCode
//                         + "," + (m.lastLocationUpdateTime.getTime() - m.lastWifiScanTime.getTime());
//
//            if (specialCode == NOT_SPECIAL) {
//                csvLine += "," + wifi.SSID // FIXME: escape commas
//                         + "," + wifi.BSSID
//                         + "," + wifi.level
//                         + "," + convertFrequencyToChannel(wifi.frequency);
//            } else if (specialCode == SPECIAL_NO_VISIBLE_WIFI) {
//                csvLine += ",,,,";
//            } else {
//                throw new IllegalArgumentException(Integer.toString(specialCode));
//            }
//
//            // FIXME last in case it might contain messed up characters
//            // this really needs to be escaped properly, same for SSID above
//            // (which can apparently contain arbitrary characters - dear god...)
//            csvLine +=     "," + "'" + m.wifiFilterET.getText().toString() + "'";
//
//            m.diskLog.info(csvLine);
//        }
    }

    private Pattern makeFilter() {
        // if not a valid regular expression or empty, don't filter at all
//        String regexp = m.wifiFilterET.getText().toString();
//        if (regexp.equals("")) {
//            regexp = ".*";
//        }
//        try {
//            return Pattern.compile(regexp);
//        } catch (PatternSyntaxException ex) {
//            return Pattern.compile(".*");
//        }
        return null;
    }

    private static int convertFrequencyToChannel(int freq) {
        if (freq >= 2412 && freq <= 2484) {
            return (freq - 2412) / 5 + 1;
        } else if (freq >= 5170 && freq <= 5825) {
            return (freq - 5170) / 5 + 34;
        } else {
            throw new IllegalArgumentException(Integer.toString(freq));
        }
    }

}