package com.turhanoz.android.reactivedirectorychooser.utils;


import android.util.Log;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBusException;

public class BusUtils {
    public static void register(EventBus bus, Object listener) {
        try {
            bus.register(listener);
        } catch (EventBusException e) {
            Log.w("RDC", e.toString());
        }
    }

    public static void unregister(EventBus bus, Object listener) {
        try {
            bus.unregister(listener);
        } catch (EventBusException e) {
            Log.w("RDC", e.toString());
        }
    }
}
