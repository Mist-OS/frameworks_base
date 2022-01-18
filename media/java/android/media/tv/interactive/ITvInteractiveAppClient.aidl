/*
 * Copyright (C) 2021 The Android Open Source Project
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

package android.media.tv.interactive;

import android.graphics.Rect;
import android.media.tv.AdRequest;
import android.media.tv.BroadcastInfoRequest;
import android.net.Uri;
import android.os.Bundle;
import android.view.InputChannel;

/**
 * Interface a client of the ITvInteractiveAppManager implements, to identify itself and receive
 * information about changes to the state of each TV interactive application service.
 * @hide
 */
oneway interface ITvInteractiveAppClient {
    void onSessionCreated(in String iAppServiceId, IBinder token, in InputChannel channel, int seq);
    void onSessionReleased(int seq);
    void onLayoutSurface(int left, int top, int right, int bottom, int seq);
    void onBroadcastInfoRequest(in BroadcastInfoRequest request, int seq);
    void onRemoveBroadcastInfo(int id, int seq);
    void onSessionStateChanged(int state, int seq);
    void onBiInteractiveAppCreated(in Uri biIAppUri, in String biIAppId, int seq);
    void onTeletextAppStateChanged(int state, int seq);
    void onCommandRequest(in String cmdType, in Bundle parameters, int seq);
    void onSetVideoBounds(in Rect rect, int seq);
    void onRequestCurrentChannelUri(int seq);
    void onRequestCurrentChannelLcn(int seq);
    void onRequestStreamVolume(int seq);
    void onRequestTrackInfoList(int seq);
    void onRequestCurrentTvInputId(int seq);
    void onAdRequest(in AdRequest request, int Seq);
}
