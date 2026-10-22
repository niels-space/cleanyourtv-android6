package com.gathin.tvhome;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.tv.TvInputInfo;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.UserManager;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.util.Locale;

/** Calls newer than API 23 behind version checks, so the launcher also runs on Android 6. */
final class Compat {
    private Compat() {}

    static void noOverlap(View v) {
        if (Build.VERSION.SDK_INT >= 24) v.forceHasOverlappingRendering(false);
    }

    /** Before Android 7 there is no direct boot: storage is always unlocked. */
    static boolean userUnlocked(Context c) {
        if (Build.VERSION.SDK_INT < 24) return true;
        UserManager um = (UserManager) c.getSystemService(Context.USER_SERVICE);
        return um == null || um.isUserUnlocked();
    }

    @SuppressWarnings("deprecation")
    static Locale primaryLocale(Configuration cfg) {
        return Build.VERSION.SDK_INT >= 24 ? cfg.getLocales().get(0) : cfg.locale;
    }

    static boolean isHidden(TvInputInfo info, Context c) {
        return Build.VERSION.SDK_INT >= 24 && info.isHidden(c);
    }

    /** null on Android 6, which has no custom label for inputs. */
    static CharSequence customLabel(TvInputInfo info, Context c) {
        return Build.VERSION.SDK_INT >= 24 ? info.loadCustomLabel(c) : null;
    }

    static Typeface font(Context c, int resId) {
        try {
            Typeface t = ResourcesCompat.getFont(c, resId);
            return t != null ? t : Typeface.DEFAULT;
        } catch (Exception e) {
            return Typeface.DEFAULT;
        }
    }

    /** Android 6 has no "default network" callback; a general internet request is the closest match. */
    static void registerDefaultNetworkCallback(ConnectivityManager cm, ConnectivityManager.NetworkCallback cb) {
        if (Build.VERSION.SDK_INT >= 24) {
            cm.registerDefaultNetworkCallback(cb);
        } else {
            cm.registerNetworkCallback(new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(), cb);
        }
    }
}
