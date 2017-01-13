package com.adaptivehandyapps.ahapmdash;

import java.util.List;
import java.util.concurrent.Callable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.adaptivehandyapps.ahaclouddal.AhaCloudDal;
import com.adaptivehandyapps.ahaclouddal.AhaDalShare;
import com.adaptivehandyapps.ahaclouddal.AhaSession;
import com.adaptivehandyapps.ahaclouddal.AhaStaff;
import com.adaptivehandyapps.ahaclouddal.AhaStaffWrapper;
import com.adaptivehandyapps.ahaclouddal.DiagTools;
import com.adaptivehandyapps.ahaopsactivity.SiteListActivity;
import com.adaptivehandyapps.common.SitesWrapper;
import com.adaptivehandyapps.dal.PreviousLoginsDAO;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class AhaAccessActivity extends Activity {

	private final String TAG = "AhaAccessActivity";
	
	public final static String EXTRA_ORG_ID = "orgId";
	public final static String EXTRA_STAFF_ID = "staffId";
	// session object
	private AhaSession mAhaSession;
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
		"ahasuper8@gmail.com", 
		"officeadmin@gmail.com", 
		"hksupernorth@gmail.com", "hknorth1@gmail.com", "hknorth2@gmail.com", "ornorth@gmail.com", 
		"bad@nowhere.com",
		"mat@gmail.com", "nat@gmail.com" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	// Keep track of the login task to ensure we can cancel it if requested.
	private AuthTask mAuthTask = null;
	private Context context;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	private List<String> previousNames;

	//////////////////////////////////////////////////////////////////////////////////////////
	// constructor
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;
		DiagTools.setContext(this);
		
		setContentView(R.layout.activity_login_main);
		
		// Get previously used names
        PreviousLoginsDAO dao = new PreviousLoginsDAO(this);

        dao.open();

        previousNames = dao.GetAllPreviousUserNames();

        dao.close();

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
		mEmailView.setText(mEmail);
		mEmailView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, previousNames) );

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		
		if(getResources().getBoolean(R.bool.portrait_only)){
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    }
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			
			mLoginFormView.setBackgroundResource(R.drawable.background_large);
			mLoginStatusView.setBackgroundResource(R.drawable.background_large);
		}

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	// Attempts to sign in or register the account specified by the login form.
	// If there are form errors (invalid email, missing fields, etc.), the
	// errors are presented and no actual login attempt is made.
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		/*if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}*/

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new AuthTask();
			mAuthTask.execute((Void) null);
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	// Shows the progress UI and hides the login form.
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
    protected void onPause() {
    	// perform light weight cleanup, release resources, save draft data
        super.onPause();
		// say hello
		Log.v(TAG, "onPause");     	
    }
    protected void onStop() {
    	// perform heavy duty cleanup, DB writes, persist auto-save data
        super.onStop();  // ...always call super class first
		// say hello
		Log.v(TAG, "onStop");     
    }
    protected void onDestroy() {
        super.onDestroy();
		// say hello
		Log.v(TAG, "onDestroy");     	
    }
    /////////////////////////////////////////////////////////////////////////////////////////
	// Represents an asynchronous login/registration task used to authenticate the user.
	public class AuthTask extends AsyncTask<Void, String, Boolean> implements Callable<Void> {
		boolean success = false;
		boolean loadSitesReturn = false;
		String result = "nada";
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// attempt authentication against a network service.

			publishProgress(context.getResources().getString(R.string.login_progress_signing_in));

			// TODO: remove dummy password
			if (mPassword.equals("")) { mPassword = "nada"; }
			
			result = AhaCloudDal.grantAhaAccess(mEmail, mPassword);
    		Log.v(TAG, "AuthTask result: " + result);
			
			int start = 0;
			if (result.startsWith(AhaDalShare.STATUS_CODE_MARKER, start )) {
	    		Log.e(TAG, "AuthTask doInBackground: AhaCloudDal.grantAhaAccess result: " + result);
//				Toast.makeText(context, "HTTP Failure " + result, Toast.LENGTH_LONG).show();	
			}
			else {
				mAhaSession = AhaCloudDal.decodeSession(result);
				if (mAhaSession != null) {
					getIntent().putExtra(EXTRA_ORG_ID, mAhaSession.getOrgId());
					getIntent().putExtra(EXTRA_STAFF_ID, mAhaSession.getStaffId());
		    		Log.v(TAG, "putExtra: " + "orgId(" + mAhaSession.getOrgId() +"), staffId(" + mAhaSession.getStaffId() + ")");
		    		// test org/staff id
		    		result = AhaCloudDal.getStaffList( mAhaSession.getOrgId(),mAhaSession.getOrgId());
		    		if (!result.startsWith(AhaDalShare.STATUS_CODE_MARKER)) {
			    		AhaStaffWrapper ahaStaffWrapper = AhaCloudDal.decodeStaffList(result);		    		
						if (ahaStaffWrapper != null) {
				    		Log.v(TAG, "AuthTask ahaStaffWrapper size: " + ahaStaffWrapper.staff.size());
							for (AhaStaff ahaStaff : ahaStaffWrapper.staff) {
					    		Log.v(TAG, "Staff: " + ahaStaff.toString());
							}
						}
						
						publishProgress(context.getResources().getString(R.string.dialogue_loading_sites_message));
						
						loadSitesReturn = SitesWrapper.loadSites(mAhaSession.getOrgId());
						
						if( !loadSitesReturn )
							return false;
						
						publishProgress(context.getResources().getString(R.string.dialogue_init_list));

						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						return true;
		    		}
		    		else {
			    		Log.e(TAG, "AuthTask doInBackground: AhaCloudDal.getStaffList SC " + result);
//						Toast.makeText(context, result, Toast.LENGTH_LONG).show();		
		    		}
				}
				else {
					// session decode failure
		    		Log.e(TAG, "AuthTask doInBackground: SignIn rejected: Session decode fails:" + result);
//					Toast.makeText(context, "SignIn rejected: Session decode fails.", Toast.LENGTH_LONG).show();	
				}
			}
			
			return success;
		}

		protected void onProgressUpdate(String... ui)
		{
			mLoginStatusMessageView.setText(ui[0]);
		}
		
		@Override
		protected void onPreExecute(){
			InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		    View view = getCurrentFocus();
		    if (view != null) {
		        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		    }
		}
		
		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				// success!

				// set result code & result data
				Log.v(TAG, "getParent " + getParent());
				if (getParent() == null) {
					setResult(RESULT_OK, getIntent());
				} else {
				    getParent().setResult(RESULT_OK, getIntent());
				}
				
				if( !previousNames.contains(mEmail))
				{
                    PreviousLoginsDAO dao = new PreviousLoginsDAO(context);

                    dao.open();

                    dao.AddUserName(mEmail);

                    dao.close();

					previousNames.add(mEmail);
					
					mEmailView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, previousNames) );
				}
				
				Intent mainIntent = new Intent(AhaAccessActivity.this, SiteListActivity.class);
				startActivity( mainIntent );
				
			} else {
				// TODO: invalid email, incorrect password
				mPasswordView.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}

		@Override
		public Void call() throws Exception {
			return null;
		}
	}
}
