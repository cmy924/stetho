/*
 * Copyright (c) 2014-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.stetho.inspector.elements.android;

import android.app.Activity;
import android.view.View;
import android.view.Window;

import com.facebook.stetho.common.Accumulator;
import com.facebook.stetho.common.StringUtil;
import com.facebook.stetho.inspector.elements.ChainedDescriptor;

final class ActivityDescriptor
    extends ChainedDescriptor<Activity> implements HighlightableDescriptor {
  @Override
  protected String onGetNodeName(Activity element) {
    String className = element.getClass().getName();
    return StringUtil.removePrefix(className, "android.app.");
  }

  @Override
  protected void onGetChildren(Activity element, Accumulator<Object> children) {
    Window window = element.getWindow();
    if (window != null) {
      children.store(window);
    }
  }

  @Override
  public View getViewForHighlighting(Object element) {
    if (getHost() instanceof AndroidDescriptorHost) {
      final AndroidDescriptorHost host = (AndroidDescriptorHost)getHost();
      Activity activity = (Activity)element;
      Window window = activity.getWindow();
      return host.getHighlightingView(window);
    }

    return null;
  }
}
