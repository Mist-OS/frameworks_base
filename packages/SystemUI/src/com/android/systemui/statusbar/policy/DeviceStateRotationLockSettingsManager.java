/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.statusbar.policy;

import static android.provider.Settings.Secure.DEVICE_STATE_ROTATION_LOCK_IGNORED;
import static android.provider.Settings.Secure.DEVICE_STATE_ROTATION_LOCK_LOCKED;
import static android.provider.Settings.Secure.DEVICE_STATE_ROTATION_LOCK_UNLOCKED;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;

import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages device-state based rotation lock settings. Handles reading, writing, and listening for
 * changes.
 */
public final class DeviceStateRotationLockSettingsManager {

    private static final String TAG = "DSRotLockSettingsMngr";
    private static final String SEPARATOR_REGEX = ":";

    private static DeviceStateRotationLockSettingsManager sSingleton;

    private final ContentResolver mContentResolver;
    private final String[] mDeviceStateRotationLockDefaults;
    private final Handler mMainHandler = Handler.getMain();
    private final Set<DeviceStateRotationLockSettingsListener> mListeners = new HashSet<>();
    private SparseIntArray mDeviceStateRotationLockSettings;

    private DeviceStateRotationLockSettingsManager(Context context) {
        mContentResolver = context.getContentResolver();
        mDeviceStateRotationLockDefaults =
                context.getResources()
                        .getStringArray(R.array.config_perDeviceStateRotationLockDefaults);
        initializeInMemoryMap();
        listenForSettingsChange(context);
    }

    /** Returns a singleton instance of this class */
    public static synchronized DeviceStateRotationLockSettingsManager getInstance(Context context) {
        if (sSingleton == null) {
            sSingleton =
                    new DeviceStateRotationLockSettingsManager(context.getApplicationContext());
        }
        return sSingleton;
    }

    /** Returns true if device-state based rotation lock settings are enabled. */
    public static boolean isDeviceStateRotationLockEnabled(Context context) {
        return context.getResources()
                        .getStringArray(R.array.config_perDeviceStateRotationLockDefaults)
                        .length
                > 0;
    }

    private void listenForSettingsChange(Context context) {
        context.getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor(Settings.Secure.DEVICE_STATE_ROTATION_LOCK),
                        /* notifyForDescendents= */ false, //NOTYPO
                        new ContentObserver(mMainHandler) {
                            @Override
                            public void onChange(boolean selfChange) {
                                onPersistedSettingsChanged();
                            }
                        },
                        UserHandle.USER_CURRENT);
    }

    /**
     * Registers a {@link DeviceStateRotationLockSettingsListener} to be notified when the settings
     * change. Can be called multiple times with different listeners.
     */
    public void registerListener(DeviceStateRotationLockSettingsListener runnable) {
        mListeners.add(runnable);
    }

    /**
     * Unregisters a {@link DeviceStateRotationLockSettingsListener}. No-op if the given instance
     * was never registered.
     */
    public void unregisterListener(
            DeviceStateRotationLockSettingsListener deviceStateRotationLockSettingsListener) {
        if (!mListeners.remove(deviceStateRotationLockSettingsListener)) {
            Log.w(TAG, "Attempting to unregister a listener hadn't been registered");
        }
    }

    /** Updates the rotation lock setting for a specified device state. */
    public void updateSetting(int deviceState, boolean rotationLocked) {
        mDeviceStateRotationLockSettings.put(
                deviceState,
                rotationLocked
                        ? DEVICE_STATE_ROTATION_LOCK_LOCKED
                        : DEVICE_STATE_ROTATION_LOCK_UNLOCKED);
        persistSettings();
    }

    /**
     * Returns the {@link DeviceStateRotationLockSetting} for the given device state. If no setting
     * is specified for this device state, it will return {@link
     * DEVICE_STATE_ROTATION_LOCK_IGNORED}.
     */
    @Settings.Secure.DeviceStateRotationLockSetting
    public int getRotationLockSetting(int deviceState) {
        return mDeviceStateRotationLockSettings.get(
                deviceState, DEVICE_STATE_ROTATION_LOCK_IGNORED);
    }

    /** Returns true if the rotation is locked for the current device state */
    public boolean isRotationLocked(int deviceState) {
        return getRotationLockSetting(deviceState) == DEVICE_STATE_ROTATION_LOCK_LOCKED;
    }

    /**
     * Returns true if there is no device state for which the current setting is {@link
     * DEVICE_STATE_ROTATION_LOCK_UNLOCKED}.
     */
    public boolean isRotationLockedForAllStates() {
        for (int i = 0; i < mDeviceStateRotationLockSettings.size(); i++) {
            if (mDeviceStateRotationLockSettings.valueAt(i)
                    == DEVICE_STATE_ROTATION_LOCK_UNLOCKED) {
                return false;
            }
        }
        return true;
    }

    private void initializeInMemoryMap() {
        String serializedSetting =
                Settings.Secure.getStringForUser(
                        mContentResolver,
                        Settings.Secure.DEVICE_STATE_ROTATION_LOCK,
                        UserHandle.USER_CURRENT);
        if (TextUtils.isEmpty(serializedSetting)) {
            // No settings saved, we should load the defaults and persist them.
            fallbackOnDefaults();
            return;
        }
        String[] values = serializedSetting.split(SEPARATOR_REGEX);
        if (values.length % 2 != 0) {
            // Each entry should be a key/value pair, so this is corrupt.
            Log.wtf(TAG, "Can't deserialize saved settings, falling back on defaults");
            fallbackOnDefaults();
            return;
        }
        mDeviceStateRotationLockSettings = new SparseIntArray(values.length / 2);
        int key;
        int value;

        for (int i = 0; i < values.length - 1; ) {
            try {
                key = Integer.parseInt(values[i++]);
                value = Integer.parseInt(values[i++]);
                mDeviceStateRotationLockSettings.put(key, value);
            } catch (NumberFormatException e) {
                Log.wtf(TAG, "Error deserializing one of the saved settings", e);
                fallbackOnDefaults();
                return;
            }
        }
    }

    private void fallbackOnDefaults() {
        loadDefaults();
        persistSettings();
    }

    private void persistSettings() {
        if (mDeviceStateRotationLockSettings.size() == 0) {
            Settings.Secure.putStringForUser(
                    mContentResolver,
                    Settings.Secure.DEVICE_STATE_ROTATION_LOCK,
                    /* value= */ "",
                    UserHandle.USER_CURRENT);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(mDeviceStateRotationLockSettings.keyAt(0))
                .append(SEPARATOR_REGEX)
                .append(mDeviceStateRotationLockSettings.valueAt(0));

        for (int i = 1; i < mDeviceStateRotationLockSettings.size(); i++) {
            stringBuilder
                    .append(SEPARATOR_REGEX)
                    .append(mDeviceStateRotationLockSettings.keyAt(i))
                    .append(SEPARATOR_REGEX)
                    .append(mDeviceStateRotationLockSettings.valueAt(i));
        }
        Settings.Secure.putStringForUser(
                mContentResolver,
                Settings.Secure.DEVICE_STATE_ROTATION_LOCK,
                stringBuilder.toString(),
                UserHandle.USER_CURRENT);
    }

    private void loadDefaults() {
        if (mDeviceStateRotationLockDefaults.length == 0) {
            Log.w(TAG, "Empty default settings");
            mDeviceStateRotationLockSettings = new SparseIntArray(/* initialCapacity= */ 0);
            return;
        }
        mDeviceStateRotationLockSettings =
                new SparseIntArray(mDeviceStateRotationLockDefaults.length);
        for (String serializedDefault : mDeviceStateRotationLockDefaults) {
            String[] entry = serializedDefault.split(SEPARATOR_REGEX);
            try {
                int key = Integer.parseInt(entry[0]);
                int value = Integer.parseInt(entry[1]);
                mDeviceStateRotationLockSettings.put(key, value);
            } catch (NumberFormatException e) {
                Log.wtf(TAG, "Error deserializing default settings", e);
            }
        }
    }

    /**
     * Called when the persisted settings have changed, requiring a reinitialization of the
     * in-memory map.
     */
    @VisibleForTesting
    public void onPersistedSettingsChanged() {
        initializeInMemoryMap();
        notifyListeners();
    }

    private void notifyListeners() {
        for (DeviceStateRotationLockSettingsListener r : mListeners) {
            r.onSettingsChanged();
        }
    }

    /** Listener for changes in device-state based rotation lock settings */
    public interface DeviceStateRotationLockSettingsListener {
        /** Called whenever the settings have changed. */
        void onSettingsChanged();
    }
}
