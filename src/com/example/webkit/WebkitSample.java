package com.example.webkit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class WebkitSample extends Activity {
	WebView webView;
	Button button01;
	Button button02;
	Button button03;
	EditText editText01;
	String url;

	public void onCreate(Bundle icicle) {
		url = "http://www.android.com";
		super.onCreate(icicle);
		setContentView(R.layout.activity_webkit_sample);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// WebViewの取得と表示
		webView = (WebView) findViewById(R.id.webview);

		button01 = (Button) findViewById(R.id.Button01);
		button01.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				webView.goBack();
			}
		});

		button02 = (Button) findViewById(R.id.Button02);
		button02.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				webView.goForward();
			}
		});

		editText01 = (EditText) findViewById(R.id.EditText01);
		editText01.setText(url);

		button03 = (Button) findViewById(R.id.Button03);
		button03.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
                InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

				url = editText01.getText().toString();
				webView.loadUrl(url);
			}
		});

		CustomWebViewClient myClient = new CustomWebViewClient();
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(myClient);

		webView.loadUrl(url);
	}
}

class CustomWebViewClient extends WebViewClient {

	ProgressDialog mDialog = null;

	// ページが読み込みおわった際に呼ばれる
	public void onPageFinished(WebView view, String url) {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
			mDialog = null;
		}

	}

	// ページが読み込み始められた際に呼ばれる
	public void onPageStarted(WebView view, String url, Bitmap favicon) {

		if (mDialog == null) {
			mDialog = new ProgressDialog(view.getContext());
			mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mDialog.setMessage("Loading...");
			mDialog.show();
		}

	}

	// 呼び出されるURLの処理等を記述する　
	public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
		view.loadUrl(url);
		return true;
	}

	// エラーが発生した際に呼ばれる
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {

	}
}