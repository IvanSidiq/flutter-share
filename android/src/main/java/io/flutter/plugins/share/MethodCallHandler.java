// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.share;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.*;
import java.util.List;
import java.util.Map;

/** Handles the method calls for the plugin. */
class MethodCallHandler implements MethodChannel.MethodCallHandler {

  private Share share;

  MethodCallHandler(Share share) {
    this.share = share;
  }

  @Override
  public void onMethodCall(MethodCall call, MethodChannel.Result result) {
    switch (call.method) {
      case "share":
        expectMapArguments(call);
        // Android does not support showing the share sheet at a particular point on screen.
        String text = call.argument("text");
        String subject = call.argument("subject");
        share.share(text, subject);
        result.success(null);
        break;
      case "shareFiles":
        expectMapArguments(call);

        List<String> paths = call.argument("paths");
        List<String> mimeTypes = call.argument("mimeTypes");
        text = call.argument("text");
        subject = call.argument("subject");
        // Android does not support showing the share sheet at a particular point on screen.
        try {
          share.shareFiles(paths, mimeTypes, text, subject);
          result.success(null);
        } catch (IOException e) {
          result.error(e.getMessage(), null, null);
        }
        break;
      case "shareWa":
        expectMapArguments(call);
        // Android does not support showing the share sheet at a particular point on screen.
        text = call.argument("text");
        subject = call.argument("subject");
        String waNumber = call.argument("waNumber");
        share.shareWa(text, subject, waNumber);
        result.success(null);
        break;
      case "shareFilesWa":
        expectMapArguments(call);

        paths = call.argument("paths");
        mimeTypes = call.argument("mimeTypes");
        text = call.argument("text");
        subject = call.argument("subject");
        waNumber = call.argument("waNumber");
        // Android does not support showing the share sheet at a particular point on screen.
        try {
          share.shareFilesWa(paths, mimeTypes, text, subject, waNumber);
          result.success(null);
        } catch (IOException e) {
          result.error(e.getMessage(), null, null);
        }
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  private void expectMapArguments(MethodCall call) throws IllegalArgumentException {
    if (!(call.arguments instanceof Map)) {
      throw new IllegalArgumentException("Map argument expected");
    }
  }
}
