package com.warehouse.webview;

import com.warehouse.webview.ICallbackFromMainToWeb;

interface IWebToMain {
      void handleWebAction(String actionName, String jsonParams, in ICallbackFromMainToWeb callback);
}
